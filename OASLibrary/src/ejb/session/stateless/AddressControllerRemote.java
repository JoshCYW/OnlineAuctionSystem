/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Address;
import java.util.List;
import util.exception.AddressNotFoundException;

/**
 *
 * @author josh
 */
public interface AddressControllerRemote {

    public Address createAddress(Address address);

    public Address retrieveAddressByID(Long addressID) throws AddressNotFoundException;

    public List<Address> retrieveAllAddress(Long customerID);

    public void updateAddress(Address address);

    public void deleteAddress(Long addressID) throws AddressNotFoundException;
}
