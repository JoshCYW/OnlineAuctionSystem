/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oasauctionclient;

import Entity.Address;
import Entity.AuctionListing;
import Entity.Bid;
import Entity.CreditPackage;
import Entity.CreditTransaction;
import Entity.Customer;
import ejb.session.stateless.AddressControllerRemote;
import ejb.session.stateless.AuctionListingControllerRemote;
import ejb.session.stateless.BidControllerRemote;
import ejb.session.stateless.CreditPackageControllerRemote;
import ejb.session.stateless.CreditTransactionControllerRemote;
import ejb.session.stateless.customerControllerRemote;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import util.exception.AddressNotFoundException;
import util.exception.AuctionListingNotFoundException;
import util.exception.InsufficientCreditsException;
import util.exception.InvalidBidException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author josh
 */
class MainApp {

    private AddressControllerRemote addressController;

    private AuctionListingControllerRemote auctionListingController;

    private BidControllerRemote bidController;

    private customerControllerRemote customerController;

    private CreditPackageControllerRemote creditPackageController;

    private CreditTransactionControllerRemote creditTransactionController;

    private Customer customer;

    public MainApp() {
    }

    public MainApp(AddressControllerRemote addressController, AuctionListingControllerRemote auctionListingController, BidControllerRemote bidController, customerControllerRemote customerController, CreditPackageControllerRemote creditPackageController, CreditTransactionControllerRemote creditTransactionController) {
        this.addressController = addressController;
        this.auctionListingController = auctionListingController;
        this.bidController = bidController;
        this.customerController = customerController;
        this.creditPackageController = creditPackageController;
        this.creditTransactionController = creditTransactionController;
    }

    public void runApp() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to OAS Auction Client ***\n");
            System.out.println("1: Register");
            System.out.println("2: Login");
            System.out.println("3: Exit\n");
            response = 0;

            OUTER:
            while (response < 1 || response > 3) {
                System.out.print("> ");
                response = sc.nextInt();

                switch (response) {
                    case 1:
                        Register();
                        break;
                    case 2:
                        try {
                            if (Login()) {
                                mainMenu();
                            }
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println("Invalid login credential!\n");
                        }
                        break;
                    case 3:
                        break OUTER;
                    default:
                        System.out.println("Invalid option, please try again!\n");
                        break;
                }
            }

            if (response == 3) {
                break;
            }
        }
    }

    private void Register() {
        System.out.println("*** OAS Registration ***\n");
        Scanner sc = new Scanner(System.in);
        String firstName, lastName, IDNumber, phoneNumber, username, password;
        BigDecimal creditBalance = BigDecimal.ZERO, holdingBalance = BigDecimal.ZERO, availableBalance = BigDecimal.ZERO;
        Boolean isPremium = false;

        do {
            System.out.print("Enter first name> ");
            firstName = sc.nextLine().trim();
            if (firstName.isEmpty()) {
                System.out.println("First name cannot be empty!\n");
            }
        } while (firstName.isEmpty());

        do {
            System.out.print("Enter last name> ");
            lastName = sc.nextLine().trim();
            if (lastName.isEmpty()) {
                System.out.println("Last name cannot be empty!\n");
            }
        } while (lastName.isEmpty());

        do {
            System.out.print("Enter idendtification number> ");
            IDNumber = sc.nextLine().trim();
            if (IDNumber.isEmpty()) {
                System.out.println("Identification number cannot be empty!\n");
            }
        } while (IDNumber.isEmpty());

        do {
            System.out.print("Enter phone number> ");
            phoneNumber = sc.nextLine().trim();
            if (phoneNumber.isEmpty()) {
                System.out.println("Phone number cannot be empty!\n");
            }
        } while (phoneNumber.isEmpty());

        Boolean isUnique = true;
        do {
            System.out.print("Enter username> ");
            username = sc.nextLine().trim();
            isUnique = !customerController.isUniqueUsername(username);
            if (!isUnique) {
                System.out.println("Username is not available.\n");
            }
            if (username.isEmpty()) {
                System.out.println("Last name cannot be empty!\n");
                isUnique = true;
            }
        } while (username.isEmpty() || !isUnique);

        Boolean match = true;

        do {
            System.out.print("Enter password> ");
            password = sc.nextLine().trim();
            System.out.print("Confirm password> ");
            String confirmPassword = sc.nextLine().trim();
            match = password.equals(confirmPassword);
            if (!match) {
                System.out.println("Passwords do not match!\n");
            }
            if (password.isEmpty()) {
                System.out.println("Password must not be empty!\n");
            }
        } while (password.isEmpty() || !match);

        Customer customer = new Customer(firstName, lastName, IDNumber, phoneNumber, username, password, creditBalance, holdingBalance, availableBalance, false);
        customer = customerController.createCustomer(customer);

        System.out.println("Account ID:" + customer.getCustomerID() + " created!");
    }

    private boolean Login() throws InvalidLoginCredentialException {
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
                try {
                    customer = customerController.customerLogin(username, password);
                    System.out.println("Login successful!\n");
                    return true;
                } catch (InvalidLoginCredentialException ex) {
                    throw ex;
                }
            } else {
                System.out.println("Please enter valid username/password!\n");
            }
        }
    }

    private void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer input = 0;

        while (true) {
            System.out.println("*** OAS Administration Panel ***\n");
            System.out.println("Welcome " + customer.getFirstname() + " " + customer.getLastName() + "\n");
            System.out.println("1: View Profile");
            System.out.println("2: Update Profile");
            System.out.println("3: Create Address");
            System.out.println("4: View Address Details");
            System.out.println("5: View All Addresses");
            System.out.println("6: View Credit Balance");
            System.out.println("7: View Credit Transaction History");
            System.out.println("8: Purchase Credit Package");
            System.out.println("9: Browse All Auction Listing");
            System.out.println("10. View Auction Listing Details");
            System.out.println("11: Browse Won Auction Listing");
            System.out.println("12: Logout\n");
            input = 0;

            OUTER:
            while (input < 1 || input > 12) {
                System.out.print("> ");
                input = scanner.nextInt();
                switch (input) {
                    case 1:
                        viewProfile();
                        break;
                    case 2:
                        updateProfile();
                        break;
                    case 3:
                        createAddress();
                        break;
                    case 4:
                        viewAddressDetails();
                        break;
                    case 5:
                        viewAllAddresses();
                        break;
                    case 6:
                        viewCreditBalance();
                        break;
                    case 7:
                        viewCreditTransactionHistory();
                        break;
                    case 8:
                        purchaseCreditPacakge();
                        break;
                    case 9:
                        viewAllAuctionListings();
                        break;
                    case 10:
                        viewAuctionListingDetails();
                        break;
                    case 11:
                        browseWonAuctionListing();
                        break;
                    case 12:
                        break OUTER;
                    default:
                        System.out.println("Invalid option, please try again!\n");
                        break;
                }
            }

            if (input == 12) {
                break;
            }
        }
    }

    private void viewProfile() {
        Scanner sc = new Scanner(System.in);
        System.out.printf("%10s%20s%20s%25s%15s%20s%20s%10s\n", "Customer ID", "First Name", "Last Name", "Identification Number", "Phone Number", "Username", "Password", "Premium");
        System.out.printf("%10s%20s%20s%25s%15s%20s%20s%10s\n", customer.getCustomerID().toString(), customer.getFirstname(),
                customer.getLastName(), customer.getIdentificationNo(), customer.getPhoneNumber(), customer.getUsername(), customer.getPassword(),
                customer.getPremium().toString());
        System.out.print("Press enter to continue...");
        sc.nextLine();
    }

    private void updateProfile() {
        Scanner sc = new Scanner(System.in);
        String input;

        System.out.println("*** Update Profile ***\n");
        System.out.print("Enter First Name (Leave blank if no change)> ");
        input = sc.nextLine().trim();
        if (input.length() > 0) {
            customer.setFirstname(input);
        }

        System.out.print("Enter Last Name (Leave blank if no change)> ");
        input = sc.nextLine().trim();
        if (input.length() > 0) {
            customer.setLastName(input);
        }

        System.out.print("Enter Phone Number (Leave blank if no change)> ");
        input = sc.nextLine().trim();
        if (input.length() > 0) {
            customer.setPhoneNumber(input);
        }

        while (true) {
            System.out.print("Enter Password (blank if no change)> ");
            input = sc.nextLine().trim();
            if (input.length() > 0) {
                System.out.print("Confirm Password> ");
                String match = sc.nextLine().trim();

                if (input.equals(match)) {
                    customer.setPassword(input);
                }
            } else {
                break;
            }
        }

        customerController.updateCustomer(customer);
        System.out.println("Profile updated!");
    }

    private void createAddress() {
        System.out.println("*** Create Address ***\n");
        Scanner sc = new Scanner(System.in);
        String streetAddress, unitNumber, postalCode;
        Boolean enabled = true;

        do {
            System.out.print("Enter street address> ");
            streetAddress = sc.nextLine().trim();
            if (streetAddress.isEmpty()) {
                System.out.println("Street address cannot be empty!\n");
            }
        } while (streetAddress.isEmpty());

        do {
            System.out.print("Enter Unit number> ");
            unitNumber = sc.nextLine().trim();
            if (unitNumber.isEmpty()) {
                System.out.println("Unit number cannot be empty!\n");
            }
        } while (unitNumber.isEmpty());

        do {
            System.out.print("Enter postal code> ");
            postalCode = sc.nextLine().trim();
            if (postalCode.isEmpty()) {
                System.out.println("Postal code cannot be empty!\n");
            }
        } while (postalCode.isEmpty());

        System.out.println();

        Address address = new Address(streetAddress, unitNumber, postalCode, enabled);
        address = addressController.createAddress(address);
        customer.getAddresses().add(address);
        customerController.updateCustomer(customer);
        System.out.println("Address ID: " + address.getAddressID() + " created!");

    }

    private void viewAddressDetails() {
        System.out.println("*** View Address Details ***\n");
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Address ID> ");
        Long addressID = sc.nextLong();
        sc.nextLine();

        try {
            Address address = addressController.retrieveAddressByID(addressID);

            System.out.printf("%10s%40s%20s%20s%20s\n", "Address ID", "Street Address", "Unit Number", "Postal Code", "Enabled");
            System.out.printf("%10s%40s%20s%20s%20s\n", address.getAddressID().toString(), address.getStreetAddress(), address.getUnitNumber(),
                    address.getPostalCode(), address.getStatus());
            System.out.println("------------------------");
            System.out.println("1: Update Address");
            System.out.println("2: Delete Address");
            System.out.println("3: Back\n");

            Integer input = 0;
            OUTER:
            while (true) {
                try {
                    input = Integer.parseInt(sc.next());
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter numeric values.\n");
                    continue;
                }
                switch (input) {
                    case 1:
                        updateAddress(address);
                        break;
                    case 2:
                        deleteAddress(address);
                        break;
                    case 3:
                        break OUTER;
                    default:
                        System.out.println("Invalid option, please try again!\n");
                        break;
                }
            }
        } catch (AddressNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.out.print("Press enter to continue...");
            sc.nextLine();
        }
    }

    private void updateAddress(Address address) {
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.println("*** Update Address ***\n");
        System.out.print("Enter Street Address (Leave blank if no change)> ");
        input = sc.nextLine().trim();
        if (input.length() > 0) {
            address.setStreetAddress(input);
        }
        System.out.print("Enter Unit Number (Leave blank if no change)> ");
        input = sc.nextLine().trim();
        if (input.length() > 0) {
            address.setUnitNumber(input);
        }
        System.out.print("Enter Postal Code (Leave blank if no change)> ");
        input = sc.nextLine().trim();
        if (input.length() > 0) {
            address.setPostalCode(input);
        }
        addressController.updateAddress(address);
        System.out.println("Address updated successfully!\n");
    }

    private void deleteAddress(Address address) {
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.println("*** Delete Address ***\n");
        System.out.printf("Confirm Delete Address %s %s %s(Address ID: %d) (Enter 'Y' to Delete)> ", address.getStreetAddress(), address.getUnitNumber(), address.getPostalCode(), address.getAddressID());
        input = sc.nextLine().trim();
        if (input.equalsIgnoreCase("Y")) {
            try {
                addressController.deleteAddress(address.getAddressID());
                System.out.println("Address deleted successfully!\n");
            } catch (AddressNotFoundException ex) {
                System.out.println("Address NOT deleted!\n");
            }
        } else {
            System.out.println("Address NOT deleted!\n");
        }
    }

    private void viewAllAddresses() {
        Scanner sc = new Scanner(System.in);
        List<Address> addresses = addressController.retrieveAllAddress(customer.getCustomerID());
        if (!addresses.isEmpty()) {
            System.out.printf("%10s%40s%20s%20s%20s\n", "Address ID", "Street Address", "Unit Number", "Postal Code", "Enabled");
            for (Address address : addresses) {
                System.out.printf("%10s%40s%20s%20s%20s\n", address.getAddressID().toString(), address.getStreetAddress(), address.getUnitNumber(),
                        address.getPostalCode(), address.getStatus());
            }
        } else {
            System.out.println("No addresses found.");
        }
        System.out.print("Press enter to continue...");
        sc.nextLine();
    }

    private void viewCreditBalance() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Credit balance: " + customer.getCreditBalance());
        System.out.println("Holding balance: " + customer.getHoldingBalance());
        System.out.println("Available balance: " + customer.getAvailableBalance() + "\n");
        System.out.print("Press enter to continue...");
        sc.nextLine();
    }

    private void viewCreditTransactionHistory() {
        Scanner sc = new Scanner(System.in);
        List<CreditTransaction> creditTransactions = creditTransactionController.retrieveCreditTransactions(customer.getCustomerID());
        System.out.printf("%20s%10s%20s\n", "Transaction Id", "Credits", "Transaction Type");
        for (CreditTransaction creditTransaction : creditTransactions) {
            System.out.printf("%20s%10s%20s\n", creditTransaction.getCreditTransactionID(), creditTransaction.getTransactionAmt(), creditTransaction.getTransactionType());
        }
        System.out.println("Press enter to continue...");
        sc.nextLine();
    }

    private void purchaseCreditPacakge() {
        Scanner sc = new Scanner(System.in);
        List<CreditPackage> creditPackages = creditPackageController.retrieveAllCreditPackages();
        HashMap<Long, CreditPackage> packages = new HashMap();
        CreditPackage creditPackage;
        int qty;
        System.out.printf("%20s%25s%20s%20s\n", "Credit Package Id", "Credit Package Name", "Number of Credits", "Price");
        for (CreditPackage inList : creditPackages) {
            if (inList.getEnabled() == true) {
                System.out.printf("%20s%25s%20s%20s\n", inList.getCreditPackageID(), inList.getCreditPackageName(), inList.getNumOfCredits(), inList.getPrice());
                packages.put(inList.getCreditPackageID(), inList);
            }
        }

        while (true) {
            System.out.print("Enter Credit Package ID for Purchase> ");
            Long response = 0L;
            try {
                response = Long.parseLong(sc.next());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter numeric values.\n");
                continue;
            }
            for (int i = 0; i < packages.size(); i++) {
                if (packages.containsKey(response)) {
                    break;
                }
                if (i == packages.size() - 1) {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            creditPackage = packages.get(response);
            System.out.print("Enter quantity to purchase " + creditPackage.getCreditPackageName() + "> ");
            qty = sc.nextInt();
            if (qty > 0) {
                sc.nextLine();
                creditTransactionController.buyCreditPackage(creditPackage, qty, customer.getCustomerID());
                System.out.println(qty + "unit of " + creditPackage.getCreditPackageName() + " purchased successfully!:\n");
                System.out.print("Press enter to continue...");
                sc.nextLine();
                return;
            } else {
                System.out.println("Invalid quantity!\n");
            }
        }

    }

    private void viewAllAuctionListings() {
        Scanner sc = new Scanner(System.in);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal;
        try {
            List<AuctionListing> auctionListings = auctionListingController.retrieveActiveAuctionListings();
            System.out.printf("%20s%15s%20s%25s\n", "Auction Listing Id", "Item Name", "Starting Bid Amount", "End Date Time");
            for (AuctionListing auctionListing : auctionListings) {
                if (auctionListing.getEnabled() == true) {

                    cal = auctionListing.getEndDate();
                    System.out.printf("%20s%15s%20s%25s\n", auctionListing.getAuctionListingID(), auctionListing.getProductName(), auctionListing.getStartingBid(), dateFormat.format(cal.getTime()));
                }
            }
        } catch (AuctionListingNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.print("Press enter to continue...");
        sc.nextLine();
    }

    private void viewAuctionListingDetails() {
        System.out.println("*** View Auction Listing Details ***\n");
        Scanner sc = new Scanner(System.in);
        Long auctionListingId = new Long(-1);
        AuctionListing auctionListing;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal;
        try {
            do {
                System.out.print("Enter Auction Listing ID> ");
                try {
                    auctionListingId = Long.parseLong(sc.next());
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter numeric values.");
                }
            } while (auctionListingId.equals(-1));
            sc.nextLine();
            try {
                auctionListing = auctionListingController.retrieveAuctionListingByID(auctionListingId);
                cal = auctionListing.getEndDate();
                Integer input = 0;
                OUTER:
                while (true) {
                    BigDecimal curentBid = ((auctionListing.getCurrentBid() == null) ? auctionListing.getStartingBid() : auctionListing.getCurrentBid());
                    System.out.printf("%20s%15s%25s%25s%25s\n", "Auction Listing Id", "Item Name", "Starting Bid Amount", "Current Bid Amount", "End Date Time");
                    System.out.printf("%20s%15s%25s%25s%25s\n", auctionListing.getAuctionListingID(), auctionListing.getProductName(), auctionListing.getStartingBid(), curentBid, dateFormat.format(cal.getTime()));
                    System.out.println("------------------------");
                    System.out.println("1: Place New Bid");
                    System.out.println("2: Refresh Auction Listing Bids");
                    System.out.println("3: Back\n");
                    System.out.print("> ");
                    try {
                        input = Integer.parseInt(sc.next());
                    } catch (NumberFormatException ex) {
                        System.out.println("Please enter numeric values.\n");
                        continue;
                    }
                    switch (input) {
                        case 1:
                            placeNewBid(auctionListing);
                            break OUTER;
                        case 2:
                            auctionListing = auctionListingController.retrieveAuctionListingByID(auctionListingId);
                            break;
                        case 3:
                            return;
                        default:
                            System.out.println("Invalid option, please try again!\n");
                            break;
                    }
                }
                sc.nextLine();
            } catch (AuctionListingNotFoundException ex) {
                System.out.println(ex.getMessage());
                System.out.print("Press enter to continue...");
                sc.nextLine();
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }
    }

    private void placeNewBid(AuctionListing auctionListing) {
        System.out.println("*** Place Bid ***\n");
        Scanner sc = new Scanner(System.in);
        Bid bid;
        BigDecimal userBid, currentBid, increment, minimumBid;
        Calendar now = Calendar.getInstance();
        currentBid = ((auctionListing.getCurrentBid() == null) ? auctionListing.getStartingBid() : auctionListing.getCurrentBid());
        increment = bidController.getBidIncrement(currentBid);
        minimumBid = currentBid.add(increment);

        do {
            System.out.println("Minimum bid price is " + minimumBid.doubleValue() + "\n");
            System.out.print("Enter Bid Amount> ");
            userBid = sc.nextBigDecimal();
        } while (userBid.compareTo(BigDecimal.ZERO) <= 0 || userBid.compareTo(minimumBid) < 0);
        sc.nextLine();
        bid = new Bid(userBid, now);
        bid.setAuctionListing(auctionListing);
        bid.setCustomer(customer);
        try {
            bidController.placeBid(bid);
            try {
                customerController.spendCredits(customer.getCustomerID(), userBid);
            } catch (InsufficientCreditsException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("Bid ID: " + bid.getBidID() + " placed!");
        } catch (InvalidBidException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.print("Press enter to continue...");
        sc.nextLine();
    }

    private void browseWonAuctionListing() {
        Scanner sc = new Scanner(System.in);

        List<AuctionListing> auctionListings = auctionListingController.retrieveWonAuctionListings(customer.getCustomerID());

        System.out.printf("%5s%25s%20s\n", "Row", "Auction Listing ID", "Item name");
        int i = 1;
        for (AuctionListing auctionListing : auctionListings) {
            System.out.printf("%5s%25s%20s\n", i, auctionListing.getAuctionListingID(), auctionListing.getProductName());
            i++;
        }
        System.out.println("------------------------");
        int response = 0;
        OUTER:
        while (true) {
            System.out.println("1: Select Delivery Address for Won Auction Listing");
            System.out.println("2: Back\n");
            System.out.print("> ");
            response = sc.nextInt();
            switch (response) {
                case 1:
                    selectDeliveryAddress(auctionListings);
                    break OUTER;
                case 2:
                    return;
                default:
                    System.out.println("Invalid option, please try again!\n");
                    break;
            }
        }

    }

    private void selectDeliveryAddress(List<AuctionListing> auctionListings) {
        Scanner sc = new Scanner(System.in);

        int row, count = 0;
        do {
            if (count > 0) {
                System.out.println("Please enter valid rows.");
            }

            System.out.print("Select item row> ");
            row = sc.nextInt();

        } while (row > auctionListings.size() || row <= 0);

        sc.nextLine(); //consume enter character

        AuctionListing auctionListing = auctionListings.get(row - 1);

        if (auctionListing.getDeliveryAddress() != null) {
            System.out.println("This auction listing has a delivery address");
            System.out.print("Press enter to continue...");
            sc.nextLine();
        } else {
            List<Address> addresses = addressController.retrieveAllAddress(customer.getCustomerID());
            System.out.printf("%5s%10s%40s%20s%20s\n", "Row", "Address ID", "Street Address", "Unit Number", "Postal Code");
            int i = 1;
            for (Address address : addresses) {
                System.out.printf("%5s%10s%40s%20s%20s\n", i, address.getAddressID().toString(),
                        address.getStreetAddress(), address.getUnitNumber(), address.getPostalCode());
                i++;
            }
            int choiceOfRow;
            do {
                System.out.print("Select row> ");
                choiceOfRow = sc.nextInt();
                if (choiceOfRow >= addresses.size() || choiceOfRow <= 0) {
                    System.out.println("Please enter valid rows.");
                }
            } while (choiceOfRow >= addresses.size() || choiceOfRow <= 0);

            sc.nextLine();

            auctionListing.setDeliveryAddress(addresses.get(choiceOfRow - 1));
            auctionListingController.updateAuctionListing(auctionListing);
            System.out.println("Delivery address added!");
            System.out.print("Press enter to continue...");
            sc.nextLine();

        }

    }
}
