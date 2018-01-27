/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.ws;

import Entity.Customer;
import ejb.session.stateless.customerControllerLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author josh
 */
@WebService(serviceName = "CustomerWebService")
@Stateless()
public class CustomerWebService {

    @EJB
    private customerControllerLocal customerController;

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @WebMethod(operationName = "regsterPremiumCustomer")
    public void regsterPremiumCustomer(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        try {
            Customer customer = customerController.customerLogin(username, password);
            if (!customer.getPremium()) {
                customer.setPremium(Boolean.TRUE);
            } else {
                System.out.println("Already registered as premium");
            }
        } catch (InvalidLoginCredentialException ex) {
             System.out.println("Invalid username/password!");
        }
    }

    @WebMethod(operationName = "customerLogin")
    public Customer Login(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws InvalidLoginCredentialException {
        try {
            Customer customer = customerController.customerLogin(username, password);

            if (customer.getPremium()) {
                em.detach(customer);
                customer.setBids(null);
                customer.setAddresses(null);
                customer.setCreditTransactions(null);
                return customer;
            } else {
                throw new InvalidLoginCredentialException("Please register to become a premium member");
            }
        } catch (InvalidLoginCredentialException ex) {
            throw ex;
        }
    }

}

