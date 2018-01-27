/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.CreditPackage;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.NoSuchCreditPackageException;

/**
 *
 * @author josh
 */
@Stateless
@Local(CreditPackageControllerLocal.class)
@Remote(CreditPackageControllerRemote.class)
public class CreditPackageController implements CreditPackageControllerRemote, CreditPackageControllerLocal {

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @Override
    public CreditPackage createCreditPackage(CreditPackage creditPackage) {
        em.persist(creditPackage);
        em.flush();
        em.refresh(creditPackage);
        return creditPackage;
    }

    @Override
    public CreditPackage retrieveCreditPackageByID(Long creditPackageID) throws NoSuchCreditPackageException {
        CreditPackage creditPackage = em.find(CreditPackage.class, creditPackageID);
        if (creditPackage != null) {
            return creditPackage;
        } else {
            throw new NoSuchCreditPackageException("Credit Package ID: " + creditPackageID + " does not exist");
        }
    }

    @Override
    public List<CreditPackage> retrieveAllCreditPackages() {
        Query query = em.createQuery("SELECT c FROM CreditPackage c");
        List<CreditPackage> creditPackages = query.getResultList();
        return creditPackages;
    }

    @Override
    public void updateCreditPackage(CreditPackage creditPackage) {
        em.merge(creditPackage);
    }

    @Override
    public void deleteCreditPackage(Long creditPackageID) {
        CreditPackage creditPackage = em.find(CreditPackage.class, creditPackageID);
        em.remove(creditPackage);
        em.flush();
    }
    
    
}