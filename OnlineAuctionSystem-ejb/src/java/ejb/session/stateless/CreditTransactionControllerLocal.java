/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.CreditPackage;
import Entity.CreditTransaction;
import java.util.List;

/**
 *
 * @author josh
 */
public interface CreditTransactionControllerLocal {

    public CreditTransaction createCreditTransaction(CreditTransaction creditTransaction);

    public List<CreditTransaction> retrieveCreditTransactions(Long customerID);

    public void buyCreditPackage(CreditPackage creditPackage, int qty, Long customerID);

}
