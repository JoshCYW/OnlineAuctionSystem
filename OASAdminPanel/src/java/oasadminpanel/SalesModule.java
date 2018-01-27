/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oasadminpanel;

import Entity.AuctionListing;
import Entity.Employee;
import ejb.session.stateless.AuctionListingControllerRemote;
import ejb.session.stateless.customerControllerRemote;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.AuctionListingNotFoundException;
import util.exception.InvalidAccessRightException;

/**
 *
 * @author josh
 */
class SalesModule {

    private AuctionListingControllerRemote auctionListingController;
    private customerControllerRemote customerController;
    private Employee employee;

    public SalesModule() {
    }

    public SalesModule(Employee employee, AuctionListingControllerRemote auctionListingController) {
        this.employee = employee;
        this.auctionListingController = auctionListingController;
    }

    public void salesStaffMenu() throws InvalidAccessRightException {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        if (employee.getAccessRight() != EmployeeAccessRightEnum.SALES) {
            throw new InvalidAccessRightException("Insufficient system rights to access sales module.");
        }

        while (true) {
            System.out.println("*** OAS Sales Staff Menu ***\n");
            System.out.println("1: Create Auction Listing");
            System.out.println("2: View Auction Listing Details");
            System.out.println("3: View All Auction Listings");
            System.out.println("4: View All Auction Listings with Bids Below Reserve Price");
            System.out.println("5: Back\n");
            response = 0;
            
            while (response < 1 || response > 5) {
                System.out.print("> ");
                response = scanner.nextInt();

                if (response == 1) {
                    createAuctionListing();
                } else if (response == 2) {
                    viewAuctionListingDetails();
                } else if (response == 3) {
                    viewAllAuctionListings();
                } else if (response == 4) {
                    viewListingsBelowReservePrice();
                } else if (response == 5) {
                    break;
                } else {
                    System.out.println("Invalid input, please try again!\n");
                }
            }
            if (response == 5) {
                break;
            }
        }
    }

    private void createAuctionListing() {
        System.out.println("*** Create New Auction Listing ***\n");
        Scanner sc = new Scanner(System.in);
        BigDecimal startingBid, reservePrice, currentBid = null;
        String name, start, end, result;
        Boolean open = false, enabled = true, manualAssignment = false;
        int year, month, day, hour, min;
        Calendar startingDate = Calendar.getInstance();
        Calendar endingDate = Calendar.getInstance();
        AuctionListing auctionListing;
        Calendar present = Calendar.getInstance();

        do {
            System.out.print("Enter auction item name> ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty!\n");
            }
        } while (name.isEmpty());

        do {
            System.out.print("Enter starting bid amount> ");
            startingBid = sc.nextBigDecimal();
            if (startingBid.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Starting bid amount must be more than zero!\n");
            }
        } while (startingBid.compareTo(BigDecimal.ZERO) <= 0);

        sc.nextLine();

        do {
            System.out.print("Enter starting date and time (yyyymmddhhmm)> ");
            start = sc.nextLine().trim();
            if (start.isEmpty()) {
                System.out.println("Starting date time cannot be empty!\n");
                continue;
            }
            try {
                year = Integer.parseInt(start.substring(0, 4).trim());
                month = Integer.parseInt(start.substring(4, 6).trim());
                day = Integer.parseInt(start.substring(6, 8).trim());
                hour = Integer.parseInt(start.substring(8, 10).trim());
                min = Integer.parseInt(start.substring(10).trim());
                if (isDateValid(year, month, day, hour, min)) {
                    startingDate.clear();
                    startingDate.set(year, month - 1, day, hour, min);
                } else {
                    System.out.println("Invalid starting date time");
                }
            } catch (StringIndexOutOfBoundsException | NumberFormatException ex) {

            }
            if (present.compareTo(startingDate) > 0) {
                System.out.println("Starting date time cannot be before current date time!\n");
            }
        } while (present.compareTo(startingDate) > 0);

        do {
            System.out.print("Enter end date and time (yyyymmddhhmm)> ");
            end = sc.nextLine().trim();
            if (end.isEmpty()) {
                System.out.println("End date time cannot be empty!");
                continue;
            }
            try {
                year = Integer.parseInt(end.substring(0, 4).trim());
                month = Integer.parseInt(end.substring(4, 6).trim());
                day = Integer.parseInt(end.substring(6, 8).trim());
                hour = Integer.parseInt(end.substring(8, 10).trim());
                min = Integer.parseInt(end.substring(10, 12).trim());
                if (isDateValid(year, month, day, hour, min)) {
                    endingDate.clear();
                    endingDate.set(year, month - 1, day, hour, min);
                } else {
                    System.out.println("Invalid ending date time");
                }
            } catch (StringIndexOutOfBoundsException | NumberFormatException ex) {

            }
            if ((present.compareTo(endingDate) > 0 || Long.parseLong(end) < Long.parseLong(start))) {
                System.out.println("End date time must be later than start date time!\n");
            }

        } while (present.compareTo(endingDate) > 0 || Long.parseLong(end) < Long.parseLong(start));

        do {
            System.out.print("Enter reserve price (0 for no reserve price)> ");
            reservePrice = sc.nextBigDecimal();
            if (reservePrice.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Reserve price cannot be negative!\n");
            }
        } while (reservePrice.compareTo(BigDecimal.ZERO) < 0);

        open = present.compareTo(startingDate) >= 0 && present.compareTo(endingDate) < 0;

        auctionListing = new AuctionListing(name, startingBid, currentBid, reservePrice, open, enabled, manualAssignment, startingDate, endingDate);

        auctionListing.setEmployee(employee);

        auctionListing = auctionListingController.createAuctionListing(auctionListing);

        System.out.println("Auction Listing ID: " + auctionListing.getAuctionListingID() + " created!\n");

    }

    private void viewAuctionListingDetails() {
        System.out.println("*** View Auction Listing Details ***\n");
        Scanner sc = new Scanner(System.in);
        Long auctionListingID = new Long(-1);

        try {
            do {
                System.out.print("Enter Auction Listing ID> ");
                try {
                    auctionListingID = Long.parseLong(sc.next());
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter numeric values.");
                }
            } while (auctionListingID.equals(-1));

            try {
                AuctionListing auctionListing = auctionListingController.retrieveAuctionListingByID(auctionListingID);
                System.out.printf("%20s%20s%15s%25s%25s%15s%15s%10s%20s\n", "Auction Listing ID", "Item Name", "Starting Bid", "Start Date", "End Date", "Reserve Price", "Open Listing", "Enable", "Delivery Address");

                String startDate = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(auctionListing.getStartDate().getTime());
                String endDate = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(auctionListing.getEndDate().getTime());

                String deliveryAddress;
                if (auctionListing.getDeliveryAddress() == null) {
                    deliveryAddress = "nil";
                } else {
                    deliveryAddress = auctionListing.getDeliveryAddress().getAddressID().toString();
                }

                System.out.printf("%20s%20s%15s%25s%25s%15.5f%15s%10s%20s\n", auctionListing.getAuctionListingID(), auctionListing.getProductName(),
                        auctionListing.getStartingBid(), startDate, endDate, auctionListing.getReservePrice(),
                        auctionListing.getOpenListing(), auctionListing.getEnabled(), deliveryAddress);

                System.out.println("------------------------");
                System.out.println("1: Update Auction Listing");
                System.out.println("2: Delete Auction Listing");
                System.out.println("3: Back\n");
                System.out.print("> ");
                Integer response = 0;

                while (true) {
                    try {
                        response = Integer.parseInt(sc.next());
                    } catch (NumberFormatException ex) {
                        System.out.println("Please enter numeric values.\n");
                        continue;
                    }

                    if (response >= 1 && response <= 3) {
                        break;
                    } else {
                        System.out.println("Invalid option, please try again!\n");
                    }
                }

                switch (response) {
                    case 1:
                        updateAuctionListing(auctionListing);
                        break;
                    case 2:
                        doDeleteAuctionListing(auctionListing);
                        break;
                    case 3:
                    default:
                        break;
                }

            } catch (AuctionListingNotFoundException ex) {
                System.out.println(ex);
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }

    }

    private void viewAllAuctionListings() {
        Scanner sc = new Scanner(System.in);

        try {
            List<AuctionListing> AuctionListings = auctionListingController.retrieveAllAuctionListings();

            System.out.printf("%20s%20s%15s%25s%25s%15s%15s%10s%20s\n", "Auction Listing ID", "Item Name", "Starting Bid", "Start Date", "End Date", "Reserve Price", "Open Listing", "Enable", "Delivery Address");
            for (AuctionListing auctionListing : AuctionListings) {
                String startDate = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(auctionListing.getStartDate().getTime());
                String endDate = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(auctionListing.getEndDate().getTime());
                String deliveryAddress;
                if (auctionListing.getDeliveryAddress() == null) {
                    deliveryAddress = "nil";
                } else {
                    deliveryAddress = auctionListing.getDeliveryAddress().getAddressID().toString();
                }
                System.out.printf("%20s%20s%15s%25s%25s%15.5f%15s%10s%20s\n", auctionListing.getAuctionListingID(), auctionListing.getProductName(),
                        auctionListing.getStartingBid(), startDate, endDate, auctionListing.getReservePrice(),
                        auctionListing.getOpenListing(), auctionListing.getEnabled(), deliveryAddress);
            }

            System.out.print("Press enter to continue...> ");
            sc.nextLine();
        } catch (AuctionListingNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void viewListingsBelowReservePrice() {
        Scanner sc = new Scanner(System.in);

        try {
            List<AuctionListing> belowReserve = auctionListingController.retrieveAuctionListingsBelowReservePrice();
            System.out.printf("%20s%20s%15s%25s%25s%15s%15s%10s\n", "Auction Listing ID", "Item Name", "Highest Bid", "Start Date", "End Date", "Reserve Price", "Open Listing", "Enable");
            for (AuctionListing auctionListing : belowReserve) {
                String startDate = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(auctionListing.getStartDate().getTime());
                String endDate = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(auctionListing.getEndDate().getTime());

                System.out.printf("%20s%20s%15s%25s%25s%15s%15s%10s\n", auctionListing.getAuctionListingID(), auctionListing.getProductName(), auctionListing.getCurrentBid(), startDate, endDate, auctionListing.getReservePrice(), auctionListing.getOpenListing(), auctionListing.getEnabled());
            }
            if (belowReserve.isEmpty()) {
                System.out.print("Press Enter to continue...");
                sc.nextLine();
                return;
            }
            System.out.println("------------------------");
            System.out.println("1: Manual Assignment of Winning Bid");
            System.out.println("2: Back\n");
            System.out.print("> ");
            Integer response = 0;

            while (true) {
                try {
                    response = Integer.parseInt(sc.next());
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter numeric values.\n");
                    continue;
                }

                if (response >= 1 && response <= 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 1) {
                Long listingID;
                while (true) {
                    System.out.print("Enter Auction Listing ID> ");
                    listingID = sc.nextLong();
                    AuctionListing selectedListing = auctionListingController.retrieveAuctionListingByID(listingID);

                    if (!belowReserve.contains(selectedListing)) {
                        System.out.println("Invalid Auction Listing ID");
                        continue;
                    } else {
                        manualAssignWinningBid(selectedListing);
                        break;
                    }
                }
            } else if (response == 2) {
                return;
            }

        } catch (AuctionListingNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void manualAssignWinningBid(AuctionListing auctionListing) {
        System.out.println("*** Manual Assignment of Winning Bid for auction listing ID: " + auctionListing.getAuctionListingID() + "***\n");
        System.out.println("1: Mark Highest Bid as Winning Bid");
        System.out.println("2: Mark Listing with No Winning Bid");
        System.out.println("3: Back\n");
        Scanner sc = new Scanner(System.in);

        System.out.print("> ");
        Integer response = 0;

        while (true) {
            try {
                response = Integer.parseInt(sc.next());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter numeric values.\n");
                continue;
            }

            if (response >= 1 && response <= 3) {
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }

        if (response == 1) {
            auctionListingController.setWinningBid(auctionListing.getAuctionListingID());
            customerController.deductCreditBalance(auctionListing.getWinningBid().getCustomer().getCustomerID(), auctionListing.getCurrentBid());
        } else if (response == 2) {
            auctionListingController.noWinningBid(auctionListing.getAuctionListingID());
            customerController.refundCredits(auctionListing.getWinningBid().getCustomer().getCustomerID(), auctionListing.getCurrentBid());
            System.out.println("No winning bid for auction listing " + auctionListing.getAuctionListingID());
            System.out.println(auctionListing.getManualAssign());
        } else if (response == 3) {
            return;
        }

        System.out.println("Press enter to continue...");
        sc.nextLine();
    }

    private void updateAuctionListing(AuctionListing auctionListing) {
        System.out.println("*** Update Auction Listing ***\n");
        Scanner sc = new Scanner(System.in);
        int year, month, day, hour, min;
        BigDecimal bigDecimal;
        String input, dateTime, type;
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        System.out.print("Enter Auction Listing Name (blank if no change)> ");
        input = sc.nextLine().trim();
        if (input.length() > 0) {
            auctionListing.setProductName(input);
        }

        System.out.print("Enter Starting Bid Amount (-1 if no change)> ");
        bigDecimal = sc.nextBigDecimal();
        if (bigDecimal.compareTo(BigDecimal.ZERO) > -1) {
            auctionListing.setStartingBid(bigDecimal);
        }
        sc.nextLine();

        do {
            System.out.print("Enter Start Date Time (format to yyyymmddhhmm; blank if no change)> ");
            dateTime = sc.nextLine().trim();
            if (dateTime.isEmpty()) {
                break;
            }
            try {
                year = Integer.parseInt(dateTime.substring(0, 4).trim());
                month = Integer.parseInt(dateTime.substring(4, 6).trim());
                day = Integer.parseInt(dateTime.substring(6, 8).trim());
                hour = Integer.parseInt(dateTime.substring(8, 10).trim());
                min = Integer.parseInt(dateTime.substring(10, 12).trim());
                if (isDateValid(year, month, day, hour, min)) {
                    startDate.clear();
                    startDate.set(year, month - 1, day, hour, min);
                } else {
                    System.out.println("Invalid ending date time");
                }
            } catch (StringIndexOutOfBoundsException | NumberFormatException ex) {
                System.out.println("Invalid input please adhere to format yyyymmddhhmm");
            }
            if (now.compareTo(startDate) > 0) {
                System.out.println("Starting date time cannot be before current date time!\n");
            }
        } while (now.compareTo(startDate) > 0);

        if (!dateTime.isEmpty()) {
            auctionListing.setStartDate(startDate);
        }

        do {
            System.out.print("Enter End Date Time (format to yyyymmddhhmm; Leave blank if no changes)> ");
            dateTime = sc.nextLine().trim();
            if (dateTime.isEmpty()) {
                break;
            }
            try {

                year = Integer.parseInt(dateTime.substring(0, 4).trim());
                month = Integer.parseInt(dateTime.substring(4, 6).trim());
                day = Integer.parseInt(dateTime.substring(6, 8).trim());
                hour = Integer.parseInt(dateTime.substring(8, 10).trim());
                min = Integer.parseInt(dateTime.substring(10, 12).trim());
                if (isDateValid(year, month, day, hour, min)) {
                    endDate.clear();
                    endDate.set(year, month - 1, day, hour, min);
                } else {
                    System.out.println("Invalid ending date time");
                }
            } catch (StringIndexOutOfBoundsException | NumberFormatException ex) {
                System.out.println("Invalid input please adhere to format yyyymmddhhmm");
            }
            if (endDate.before(startDate)) {
                System.out.println("ending date time cannot be before starting date time!\n");
            }
        } while (endDate.before(startDate));
        if (!dateTime.isEmpty()) {
            auctionListing.setEndDate(endDate);
        }

        System.out.print("Enter Reserve Price (-1 if no change)> ");
        bigDecimal = sc.nextBigDecimal();
        if (bigDecimal.compareTo(BigDecimal.ZERO) > -1) {
            auctionListing.setReservePrice(bigDecimal);
        }
        sc.nextLine();

        System.out.print("Enable Listing? (Enter Y, N, or blank if no change)> ");
        input = sc.nextLine();
        if (input.equals("Y")) {
            auctionListing.setEnabled(Boolean.TRUE);
        } else if (input.equals("N")) {
            auctionListing.setEnabled(Boolean.FALSE);
        }

        auctionListingController.updateAuctionListing(auctionListing);
        System.out.println("Auction listing updated successfully!\n");
    }

    private void doDeleteAuctionListing(AuctionListing auctionListing) {
        System.out.println("*** Delete Auction Listing ***\n");
        auctionListingController.deleteAuctionListing(auctionListing.getAuctionListingID());
        System.out.println("Auction Listing disabled/deleted successfully.");
    }

    private Boolean isDateValid(int year, int month, int day, int hours, int mins) {
        if (year > 9999 || month > 12 || hours > 60 || mins > 60) {
            return false;
        }
        if (month == 2 && day > 28) {
            return false;
        }
        return !(month == 4 || month == 6 || month == 9 || month == 11 && day > 30);
    }
}
