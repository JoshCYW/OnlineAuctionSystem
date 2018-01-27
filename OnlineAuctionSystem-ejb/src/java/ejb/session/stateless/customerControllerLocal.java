/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.CreditTransaction;
import Entity.Customer;
import java.math.BigDecimal;
import util.exception.CustomerNotFoundException;
import util.exception.InsufficientCreditsException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author josh
 */
public interface customerControllerLocal {

    public Customer createCustomer(Customer customer);

    public Customer retrieveCustomerByID(Long customerID) throws CustomerNotFoundException;

    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException;

    public void updateCustomer(Customer customer);

    public void deleteCustomer(Long customerID) throws CustomerNotFoundException;

    public void spendCredits(Long customerID, BigDecimal amount) throws InsufficientCreditsException;

    public void refundCredits(Long customerId, BigDecimal amount);

    public void topUpCredits(Long customerID, BigDecimal amount, CreditTransaction creditTransaction) throws CustomerNotFoundException;

    public void deductCreditBalance(Long customerID, BigDecimal amount);

    public BigDecimal retrieveCreditBalance(Long customerID);

    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public Boolean isUniqueUsername(String username);

}
