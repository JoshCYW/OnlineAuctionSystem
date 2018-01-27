/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oasadminpanel;

import ejb.session.stateless.AuctionListingControllerRemote;
import ejb.session.stateless.BidControllerRemote;
import ejb.session.stateless.CreditPackageControllerRemote;
import ejb.session.stateless.EmployeeControllerRemote;
import javax.ejb.EJB;

/**
 *
 * @author josh
 */
public class Main {

    @EJB
    private static EmployeeControllerRemote employeeController;
    @EJB
    private static CreditPackageControllerRemote creditPackageController;
    @EJB
    private static BidControllerRemote bidController;
    @EJB
    private static AuctionListingControllerRemote auctionListingController;

    public static void main(String[] args) {
        MainApp mainApp = new MainApp(auctionListingController,bidController,creditPackageController,employeeController);
        mainApp.runApp();
    }

}
