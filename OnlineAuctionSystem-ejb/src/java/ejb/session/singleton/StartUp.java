/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import Entity.Address;
import Entity.AuctionListing;
import Entity.CreditPackage;
import Entity.CreditTransaction;
import Entity.Customer;
import Entity.Employee;
import ejb.session.stateless.AddressControllerLocal;
import ejb.session.stateless.AuctionListingControllerLocal;
import ejb.session.stateless.CreditPackageControllerLocal;
import ejb.session.stateless.CreditTransactionControllerLocal;
import ejb.session.stateless.EmployeeControllerLocal;
import ejb.session.stateless.customerControllerLocal;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.CreditTransactionTypeEnum;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.EmployeeNotFoundException;

/**
 *
 * @author josh
 */
@Startup
@Singleton
@LocalBean
public class StartUp {

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @EJB
    private AddressControllerLocal addressController;
    @EJB
    private AuctionListingControllerLocal auctionListingController;
    @EJB
    private customerControllerLocal customerController;
    @EJB
    private CreditPackageControllerLocal creditPackageController;
    @EJB
    private CreditTransactionControllerLocal creditTransactionController;
    @EJB
    private EmployeeControllerLocal employeeController;

    public StartUp() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            employeeController.retrieveEmployeeByUsername("admin");
        } catch (EmployeeNotFoundException ex) {
            initializeData();
        }
    }

    private void initializeData() {
        //initialize Customers
        Customer customer = customerController.createCustomer(new Customer("Patient", "Zero", "123", "123", "customer", "password", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, Boolean.FALSE));
        Address address1 = addressController.createAddress(new Address("Strawberry", "1-1", "010101", Boolean.TRUE));
        Address address2 = addressController.createAddress(new Address("Raspberry", "2-2", "123456", Boolean.TRUE));
        customer.getAddresses().add(address1);
        customer.getAddresses().add(address2);
        //initialize Employees
        Employee admin = employeeController.createEmployee(new Employee("Admin", "Admin", "admin", "password", EmployeeAccessRightEnum.SYSADMIN));
        Employee finance = employeeController.createEmployee(new Employee("Finance", "Finance", "finance", "password", EmployeeAccessRightEnum.FINANCE));
        Employee sales = employeeController.createEmployee(new Employee("Sales", "Sales", "sales", "password", EmployeeAccessRightEnum.SALES));
        //initialize auction
        Calendar start = Calendar.getInstance();
        start.set(2017, 11, 11, 11, 11);
        Calendar end = Calendar.getInstance();
        end.set(2017, 12, 12, 12, 12);
        AuctionListing auctionListing = new AuctionListing("PS4", BigDecimal.valueOf(499), null, BigDecimal.ZERO, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, start, end);
        auctionListing.setEmployee(sales);
        auctionListingController.createAuctionListing(auctionListing);
        start.set(2017, 10, 10, 10, 10);
        end.set(2017, 20, 12, 5, 15);
        AuctionListing auctionListing2 = new AuctionListing("xbox", BigDecimal.valueOf(399), BigDecimal.valueOf(500), BigDecimal.valueOf(800), Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, start, end);
        auctionListing2.setEmployee(sales);
        auctionListingController.createAuctionListing(auctionListing2);
        //generate packages
        CreditPackage basicPackage = new CreditPackage("Basic Package", BigDecimal.valueOf(100), BigDecimal.valueOf(100), true);
        basicPackage.setEmployee(finance);
        CreditPackage premiumPackage = new CreditPackage("Premium Package", BigDecimal.valueOf(300), BigDecimal.valueOf(330), true);
        premiumPackage.setEmployee(finance);
        basicPackage = creditPackageController.createCreditPackage(basicPackage);
        premiumPackage = creditPackageController.createCreditPackage(premiumPackage);
        //basic package transaction
        CreditTransaction creditTransaction1 = new CreditTransaction(BigDecimal.valueOf(100), CreditTransactionTypeEnum.TOPUP);
        creditTransaction1.setCreditPackage(basicPackage);
        creditTransaction1.setCustomer(customer);
        creditTransactionController.createCreditTransaction(creditTransaction1);
        customer.setCreditBalance(customer.getCreditBalance().add(creditTransaction1.getTransactionAmt()));
        customer.setAvailableBalance(customer.getAvailableBalance().add(creditTransaction1.getTransactionAmt()));
        basicPackage.getCreditTransaction().add(creditTransaction1);
        //premium package transaction
        CreditTransaction creditTransaction2 = new CreditTransaction(BigDecimal.valueOf(330), CreditTransactionTypeEnum.TOPUP);
        creditTransaction2.setCreditPackage(premiumPackage);
        creditTransaction2.setCustomer(customer);
        creditTransactionController.createCreditTransaction(creditTransaction2);
        customer.setCreditBalance(customer.getCreditBalance().add(creditTransaction2.getTransactionAmt()));
        customer.setAvailableBalance(customer.getAvailableBalance().add(creditTransaction2.getTransactionAmt()));
        premiumPackage.getCreditTransaction().add(creditTransaction2);
    }
}
