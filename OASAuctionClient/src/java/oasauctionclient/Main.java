/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oasauctionclient;

import ejb.session.stateless.AddressControllerRemote;
import ejb.session.stateless.AuctionListingControllerRemote;
import ejb.session.stateless.BidControllerRemote;
import ejb.session.stateless.CreditPackageControllerRemote;
import ejb.session.stateless.CreditTransactionControllerRemote;
import ejb.session.stateless.customerControllerRemote;
import javax.ejb.EJB;

/**
 *
 * @author josh
 */
public class Main {

    @EJB
    private static CreditTransactionControllerRemote creditTransactionController;

    @EJB
    private static CreditPackageControllerRemote creditPackageController;

    @EJB
    private static customerControllerRemote customerController;

    @EJB
    private static BidControllerRemote bidController;

    @EJB
    private static AuctionListingControllerRemote auctionListingController;

    @EJB
    private static AddressControllerRemote addressController;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        MainApp mainApp = new MainApp(addressController, auctionListingController, bidController, customerController, creditPackageController, creditTransactionController);
        mainApp.runApp();
    }

}
