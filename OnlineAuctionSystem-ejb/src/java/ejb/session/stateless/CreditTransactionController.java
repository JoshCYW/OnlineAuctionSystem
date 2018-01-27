/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.CreditPackage;
import Entity.CreditTransaction;
import Entity.Customer;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.CreditTransactionTypeEnum;
import util.exception.CustomerNotFoundException;

/**
 *
 * @author josh
 */
@Stateless
@Local(CreditTransactionControllerLocal.class)
@Remote(CreditTransactionControllerRemote.class)
public class CreditTransactionController implements CreditTransactionControllerRemote, CreditTransactionControllerLocal {

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @EJB
    private customerControllerLocal customerController;
    @EJB
    private BidControllerLocal bidController;

    @Resource
    private EJBContext eJBContext;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CreditTransaction createCreditTransaction(CreditTransaction creditTransaction) {
        em.persist(creditTransaction);
        Customer customer = creditTransaction.getCustomer();
        customer.getCreditTransactions().add(creditTransaction);
        em.flush();
        em.refresh(creditTransaction);
        return creditTransaction;
    }

    @Override
    public List<CreditTransaction> retrieveCreditTransactions(Long customerID) {
        Query query = em.createQuery("SELECT c FROM CreditTransactionEntity c WHERE c.customerEntity.customerID = :inCustomerID");
        query.setParameter("inCustomerId", customerID);
        return query.getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void buyCreditPackage(CreditPackage creditPackage, int qty, Long customerID) {
        creditPackage = em.merge(creditPackage);
        CreditTransaction creditTransaction = new CreditTransaction(creditPackage.getNumOfCredits().multiply(BigDecimal.valueOf(qty)), CreditTransactionTypeEnum.TOPUP);
        try {
            Customer customer = customerController.retrieveCustomerByID(customerID);
            creditTransaction.setCustomer(customer);
        } catch (CustomerNotFoundException ex) {
        }
        creditTransaction.setCreditPackage(creditPackage);
        try {
            creditTransaction = createCreditTransaction(creditTransaction);
            customerController.topUpCredits(customerID, creditPackage.getNumOfCredits().multiply(BigDecimal.valueOf(qty)), creditTransaction);
            creditPackage.getCreditTransaction().add(creditTransaction);
        } catch (CustomerNotFoundException ex) {
            eJBContext.setRollbackOnly();
        }
    }

}
