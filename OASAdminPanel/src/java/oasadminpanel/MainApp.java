/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oasadminpanel;

import Entity.Employee;
import ejb.session.stateless.AuctionListingControllerRemote;
import ejb.session.stateless.BidControllerRemote;
import ejb.session.stateless.CreditPackageControllerRemote;
import ejb.session.stateless.EmployeeControllerRemote;
import java.util.Scanner;
import util.exception.InvalidAccessRightException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author josh
 */
class MainApp {

    private AuctionListingControllerRemote auctionListingController;
    private BidControllerRemote bidController;
    private CreditPackageControllerRemote creditPackageController;
    private EmployeeControllerRemote employeeController;
    private AdminModule AdminModule;
    private FinanceModule financeModule;
    private SalesModule salesModule;

    private Employee employee;

    public MainApp() {
    }

    public MainApp(AuctionListingControllerRemote auctionListingController, BidControllerRemote bidController, CreditPackageControllerRemote creditPackageController, EmployeeControllerRemote employeeController) {
        this.auctionListingController = auctionListingController;
        this.bidController = bidController;
        this.creditPackageController = creditPackageController;
        this.employeeController = employeeController;
    }



    public void runApp() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to OAS Administration Panel ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;

            while (response < 1 || response > 2) {
                System.out.print("> ");

                String input = sc.next();

                try {
                    response = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter numeric values.\n");
                    continue;
                }

                if (response == 1) {

                    try {
                        if (doLogin()) {
                            AdminModule = new AdminModule(employee, employeeController);
                            financeModule = new FinanceModule(employee, creditPackageController);
                            salesModule = new SalesModule(employee, auctionListingController);
                            menuMain();
                        }
                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid login credential!\n");
                    }
                } else if (response == 2) {
                    break;

                } else {
                    System.out.println("Invalid input, please try again!\n");
                }
            }

            if (response == 2) {
                break;
            }
        }

    }

    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** OAS Administration Panel ***\n");
            System.out.println("You are login as " + employee.getFirstName() + " " + employee.getLastName() + " with " + employee.getAccessRight() + " rights\n");
            System.out.println("1: System Administrator");
            System.out.println("2: Finance Staff");
            System.out.println("3: Sales Staff");
            System.out.println("4: Change Password");
            System.out.println("5: Back\n");
            response = 0;

            OUTER:
            while (response < 1 || response > 5) {
                System.out.print("> ");
                response = scanner.nextInt();
                switch (response) {
                    case 1:
                        try {
                            AdminModule.adminMenu();
                        } catch (InvalidAccessRightException ex) {
                            System.out.println("Invalid input, please try again!: " + ex.getMessage() + "\n");
                        }   break;
                    case 2:
                        try {
                            financeModule.financeStaffMenu();
                        } catch (InvalidAccessRightException ex) {
                            System.out.println("Invalid input, please try again!: " + ex.getMessage() + "\n");
                        }   break;
                    case 3:
                        try {
                            salesModule.salesStaffMenu();
                        } catch (InvalidAccessRightException ex) {
                            System.out.println("Invalid input, please try again!: " + ex.getMessage() + "\n");
                        }   break;
                    case 4:
                        changePassword();
                        break;
                    case 5:
                        break OUTER;
                    default:
                        System.out.println("Invalid input, please try again!\n");
                        break;
                }
            }

            if (response == 5) {
                break;
            }
        }
    }

    private boolean doLogin() throws InvalidLoginCredentialException {
        Scanner sc = new Scanner(System.in);
        String username = "";
        String password = "";
        while (true) {
            System.out.println("*** Login: OAS Administration System ***\n");
            System.out.print("Enter username> ");
            username = sc.nextLine().trim();
            System.out.print("Enter password> ");
            password = sc.nextLine().trim();
            if (username.length() > 0 && password.length() > 0) {
                try {
                    employee = employeeController.employeeLogin(username, password);
                    System.out.println("Login successful!\n");
                    return true;
                } catch (InvalidLoginCredentialException ex) {
                    throw new InvalidLoginCredentialException();
                }
            } else {
                System.out.println("Please enter valid username/password!\n");
            }
        }
    }

    private void changePassword() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter current password> ");
        String current = sc.nextLine().trim();
        if (current.equals(employee.getPassword())) {
            System.out.print("Enter New Password> ");
            String newPassword = sc.nextLine().trim();
            System.out.print("Confirm New Password> ");
            String confirmation = sc.nextLine().trim();
            if (newPassword.equals(confirmation)) {
                employee.setPassword(newPassword);
                employeeController.updateEmployee(employee);
            } else {
                System.out.println("Passwords do not match!");
            }
            System.out.println("Password Changed Successfully!");
        } else {
            System.out.println("Invalid password!");
        }

    }

}
