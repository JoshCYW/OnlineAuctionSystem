/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Address;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AddressNotFoundException;

/**
 *
 * @author josh
 */
@Local(AddressControllerLocal.class)
@Remote(AddressControllerRemote.class)
@Stateless
public class AddressController implements AddressControllerRemote, AddressControllerLocal {

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @Override
    public Address createAddress(Address address) {
        em.persist(address);
        em.flush();
        em.refresh(address);

        return address;
    }

    @Override
    public Address retrieveAddressByID(Long addressID) throws AddressNotFoundException {

        Address address = em.find(Address.class, addressID);

        if (address != null) {
            return address;
        } else {
            throw new AddressNotFoundException("Address ID " + addressID + " does not exist!");
        }
    }

    @Override
    public List<Address> retrieveAllAddress(Long customerID) {
        Query query = em.createQuery("SELECT s FROM CustomerEntity c JOIN c.addressEntities s WHERE c.customerId = :inCustomerId AND s.enabled = true");
        query.setParameter("inCustomerId", customerID);
        return query.getResultList();
    }

    @Override
    public void updateAddress(Address address) {
        em.merge(address);
    }

    @Override
    public void deleteAddress(Long addressID) throws AddressNotFoundException {

        Address address = retrieveAddressByID(addressID);
        em.remove(address);
    }

}
