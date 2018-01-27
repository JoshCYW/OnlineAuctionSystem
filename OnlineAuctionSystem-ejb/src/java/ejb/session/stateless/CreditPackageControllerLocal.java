/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.CreditPackage;
import java.util.List;
import util.exception.NoSuchCreditPackageException;

/**
 *
 * @author josh
 */
public interface CreditPackageControllerLocal {

    public CreditPackage createCreditPackage(CreditPackage creditPackage);

    public CreditPackage retrieveCreditPackageByID(Long creditPackageID) throws NoSuchCreditPackageException;

    public List<CreditPackage> retrieveAllCreditPackages();

    public void updateCreditPackage(CreditPackage creditPackage);

    public void deleteCreditPackage(Long creditPackageID);

}
