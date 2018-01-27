/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.CreditTransaction;
import Entity.Customer;
import java.math.BigDecimal;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CustomerNotFoundException;
import util.exception.InsufficientCreditsException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author josh
 */
@Stateless
@Local(customerControllerLocal.class)
@Remote(customerControllerRemote.class)
public class customerController implements customerControllerRemote, customerControllerLocal {

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @Override
    public Customer createCustomer(Customer customer) {
        em.persist(customer);
        em.flush();
        em.refresh(customer);

        return customer;
    }

    @Override
    public Customer retrieveCustomerByID(Long customerID) throws CustomerNotFoundException {

        Customer customer = em.find(Customer.class, customerID);

        if (customer != null) {
            return customer;
        } else {
            throw new CustomerNotFoundException("Customer ID " + customerID + " does not exist!");
        }
    }

    @Override
    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Customer customer = retrieveCustomerByUsername(username);
            if (customer.getPassword().equals(password)) {
                return customer;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (CustomerNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        } 
    }

    @Override
    public void updateCustomer(Customer customer) {
        em.merge(customer);
    }

    @Override
    public void deleteCustomer(Long customerID) throws CustomerNotFoundException {
        try {
            Customer customer = retrieveCustomerByID(customerID);
            em.remove(customer);
        } catch (CustomerNotFoundException ex) {
            throw new CustomerNotFoundException("Customer ID " + customerID + " does not exist!");
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void spendCredits(Long customerID, BigDecimal amount) throws InsufficientCreditsException {
        Customer customer;
        try {
            customer = retrieveCustomerByID(customerID);
            BigDecimal holdingBalance = customer.getHoldingBalance();
            holdingBalance = holdingBalance.add(amount);
            BigDecimal availableBalance = customer.getAvailableBalance();
            availableBalance = availableBalance.subtract(amount);

            if (holdingBalance.compareTo(customer.getCreditBalance()) <= 0) {
                customer.setHoldingBalance(holdingBalance);
                customer.setAvailableBalance(availableBalance);
                em.merge(customer);
            } else {
                throw new InsufficientCreditsException("Insufficient credits!");
            }

        } catch (CustomerNotFoundException ex) {
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void refundCredits(Long customerID, BigDecimal amount) {
        Customer customer;
        try {
            customer = retrieveCustomerByID(customerID);
            customer.setHoldingBalance(customer.getHoldingBalance().subtract(amount));
            customer.setAvailableBalance(customer.getAvailableBalance().add(amount));
            em.merge(customer);
        } catch (CustomerNotFoundException ex) {
        }
    }

    @Override
    public void topUpCredits(Long customerID, BigDecimal amount, CreditTransaction creditTransaction) throws CustomerNotFoundException {
        try {
            Customer customer = retrieveCustomerByID(customerID);
            customer.setCreditBalance(customer.getCreditBalance().add(amount));
            customer.setAvailableBalance(customer.getAvailableBalance().add(amount));
        } catch (CustomerNotFoundException ex) {
            throw new CustomerNotFoundException("Customer ID " + customerID + " does not exist!");
        }
    }

    @Override
    public void deductCreditBalance(Long customerID, BigDecimal amount) {
        Customer customer;
        try {
            customer = retrieveCustomerByID(customerID);
            customer.setHoldingBalance(customer.getHoldingBalance().subtract(amount));
            customer.setCreditBalance(customer.getCreditBalance().subtract(amount));
            em.merge(customer);
        } catch (CustomerNotFoundException ex) {
        }
    }

    @Override
    public BigDecimal retrieveCreditBalance(Long customerID) {
        Customer customer;
        try {
            customer = retrieveCustomerByID(customerID);
            return customer.getCreditBalance();
        } catch (CustomerNotFoundException ex) {
        }
        return null;
    }

    @Override
    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.username = :inUsername");
        query.setParameter("inUsername", username);
        try {
            return (Customer) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CustomerNotFoundException("Customer Username " + username + " does not exist!");
        }
    }
    
    @Override
    public Boolean isUniqueUsername(String username) {
        Query query = em.createQuery("SELECT COUNT(c) FROM Customer c WHERE c.username = :inUsername");
        query.setParameter("inUsername", username);

        Long count = (Long) query.getSingleResult();

        return count > 0;
    }    
}
