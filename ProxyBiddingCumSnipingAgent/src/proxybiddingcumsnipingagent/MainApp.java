/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxybiddingcumsnipingagent;

import java.util.Scanner;
import ws.client.Customer;
import ws.client.InvalidLoginCredentialException_Exception;

/**
 *
 * @author josh
 */
class MainApp {

    private Customer customer;

    public MainApp() {
    }

    public void runApp() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Proxy Bidding cum Sniping Agent ***\n");
            System.out.println("1: Register");
            System.out.println("2: Login");
            System.out.println("3: Exit\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = sc.nextInt();

                if (response == 1) {
                    register();
                } else if (response == 2) {
                    try {
                        if (login()) {
                            mainMenu();
                        }
                    } catch (InvalidLoginCredentialException_Exception ex) {
                        System.out.println("Invalid login credential!\n");
                    }
                } else if (response == 3) {
                    break;

                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 3) {
                break;
            }
        }
    }

    private void register() {
        Scanner sc = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** Register ***\n");
        while (true) {
            System.out.print("Enter username> ");
            username = sc.nextLine().trim();
            System.out.print("Enter password> ");
            password = sc.nextLine().trim();
            if (username.length() > 0 && password.length() > 0) {
                //regsterPremiumCustomer(username, password);
                System.out.println("Registration successful!\n");
                return;
            } else {
                System.out.println("Invalid username/password!\n");
            }
        }
    }

    private boolean login() throws InvalidLoginCredentialException_Exception {
        Scanner sc = new Scanner(System.in);
        String username = "";
        String password = "";

        while (true) {
            System.out.println("*** Login ***\n");
            System.out.print("Enter username> ");
            username = sc.nextLine().trim();
            System.out.print("Enter password> ");
            password = sc.nextLine().trim();
            if (username.length() > 0 && password.length() > 0) {
                //customer = customerLogin(username, password);
                System.out.println("Login successful!\n");
                return true;
            } else {
                System.out.println("Invalid username/password!\n");
            }
        }
    }

    private void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Proxy Bidding cum Sniping Agent ***\n");
            System.out.println("Welcome " + customer.getFirstname() + " " + customer.getLastName() + "\n");
            System.out.println("1: View Credit Balance");
            System.out.println("2: View Auction Listing Details");
            System.out.println("3: Browse All Auction Listings");
            System.out.println("4: View Won Auction Listings");
            System.out.println("5: Logout\n");
            response = 0;

            while (response < 1 || response > 5) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    viewCreditBalance();
                } else if (response == 2) {
                    viewAuctionListingDetails();
                } else if (response == 3) {
                    browseAllAuctionListings();
                } else if (response == 4) {
                    retrieveWonListings();
                } else if (response == 5) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 5) {
                break;
            }
        }
    }

    private void viewCreditBalance() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Credit balance: " + customer.getCreditBalance());
        System.out.println("Holding balance: " + customer.getHoldingBalance());
        System.out.println("Available balance: " + customer.getAvailableBalance() + "\n");

        System.out.print("Press enter to continue...");
        sc.nextLine();
    }

    private void viewAuctionListingDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void browseAllAuctionListings() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void retrieveWonListings() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
