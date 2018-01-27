/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oasadminpanel;

import Entity.CreditPackage;
import Entity.Employee;
import ejb.session.stateless.CreditPackageControllerRemote;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.InvalidAccessRightException;
import util.exception.NoSuchCreditPackageException;

/**
 *
 * @author josh
 */
class FinanceModule {

    private CreditPackageControllerRemote creditPackageController;
    private Employee employee;

    public FinanceModule() {
    }

    public FinanceModule(Employee employee, CreditPackageControllerRemote creditPackageController) {
        this.employee = employee;
        this.creditPackageController = creditPackageController;

    }

    public void financeStaffMenu() throws InvalidAccessRightException {
        if (employee.getAccessRight() != EmployeeAccessRightEnum.FINANCE) {
            throw new InvalidAccessRightException("Insufficient system rights to access finance module.");
        }

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** OAS Finance Staff Menu ***\n");
            System.out.println("1: Create Credit Package");
            System.out.println("2: View Credit Package Details");
            System.out.println("3: View All Credit Packages");
            System.out.println("4: Back\n");
            response = 0;

            OUTER:
            while (response < 1 || response > 4) {
                System.out.print("> ");
                response = scanner.nextInt();
                switch (response) {
                    case 1:
                        createNewCreditPackage();
                        break;
                    case 2:
                        viewCreditPackageDetails();
                        break;
                    case 3:
                        viewAllCreditPackages();
                        break;
                    case 4:
                        break OUTER;
                    default:
                        System.out.println("Invalid option, please try again!\n");
                        break;
                }
            }

            if (response == 4) {
                break;
            }
        }
    }

    private void createNewCreditPackage() {
        System.out.println("*** Create New Credit Package ***\n");
        Scanner sc = new Scanner(System.in);

        String packageName;
        BigDecimal price, numOfCredits;

        CreditPackage creditPackage;

        int count = 0;

        do {
            System.out.print("Enter credit package name> ");
            packageName = sc.nextLine().trim();
            if (packageName.isEmpty()) {
                System.out.println("Credit package name cannot be empty!\n");
            }
        } while (packageName.isEmpty());

        count = 0;

        do {
            System.out.print("Enter price> ");
            price = sc.nextBigDecimal();
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Price cannot be negative or zero!\n");
            }
        } while (price.compareTo(BigDecimal.ZERO) <= 0);

        count = 0;

        do {
            System.out.print("Enter number of credits> ");
            numOfCredits = sc.nextBigDecimal();
            if (numOfCredits.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Number of credits cannot be negative or zero!\n");
            }
        } while (numOfCredits.compareTo(BigDecimal.ZERO) <= 0);

        creditPackage = new CreditPackage(packageName, price, numOfCredits, true);

        creditPackage.setEmployee(employee);

        creditPackage = creditPackageController.createCreditPackage(creditPackage);

        System.out.println("Credit Package ID: " + creditPackage.getCreditPackageID() + "created!\n\n");
    }

    private void viewCreditPackageDetails() {
        System.out.println("***View Credit Package Details ***\n");
        Scanner sc = new Scanner(System.in);
        Long creditPackageID = new Long(-1);
        do {
            System.out.print("Enter Credit Package ID> ");
            try {
                creditPackageID = Long.parseLong(sc.next());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter numeric values.");
            }
        } while (creditPackageID.equals(-1));

        try {
            CreditPackage creditPackage = creditPackageController.retrieveCreditPackageByID(creditPackageID);
            System.out.println("Credit Package ID\tCredit Package Name\tPrice\tNumber of Credits\tEnable");
            System.out.println("\t" + creditPackage.getCreditPackageID().toString() + "\t\t\t" + creditPackage.getCreditPackageName() + "\t" + creditPackage.getPrice() + "\t\t" + creditPackage.getNumOfCredits() + "\t\t" + creditPackage.getEnabled());
            System.out.println("------------------------");
            System.out.println("1: Update Credit Package");
            System.out.println("2: Delete Credit Package");
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
                    updateCreditPackage(creditPackage);
                    break;
                case 2:
                    deleteCreditPackage(creditPackage);
                    break;
                case 3:
                default:
                    break;
            }

        } catch (NoSuchCreditPackageException ex) {
            System.out.println(ex);
        }

    }

    private void viewAllCreditPackages() {
        System.out.println("*** View All Credit Packages ***\n");

        Scanner sc = new Scanner(System.in);

        try {
            List<CreditPackage> creditPackages = creditPackageController.retrieveAllCreditPackages();
            System.out.printf("%20s%20s%10s%20s%10s\n", "Credit Package ID", "Credit Package Name", "Price", "Number of Credits", "Enable");

            for (CreditPackage creditPackage : creditPackages) {
                System.out.printf("%15s%25s%10s%20s%10s\n", creditPackage.getCreditPackageID().toString(), creditPackage.getCreditPackageName(), creditPackage.getPrice().toString(), creditPackage.getNumOfCredits().toString(), creditPackage.getEnabled());
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        }

        System.out.print("Press enter to continue...> ");
        sc.nextLine();
    }

    private void updateCreditPackage(CreditPackage creditPackage) {
        System.out.println("*** Update Credit Packages ***\n");

        Scanner sc = new Scanner(System.in);
        String input;
        BigDecimal bigDecimal;

        System.out.print("Enter Credit Package Name (blank if no change)> ");
        input = sc.nextLine().trim();
        if (input.length() > 0) {
            creditPackage.setCreditPackageName(input);
        }

        System.out.print("Enter Price (Enter -1 if no change)> ");
        bigDecimal = sc.nextBigDecimal();
        if (bigDecimal.compareTo(BigDecimal.ZERO) > -1) {
            creditPackage.setPrice(bigDecimal);
        }

        System.out.print("Enter Number of Credits (Enter -1 if no change)> ");
        bigDecimal = sc.nextBigDecimal();
        if (bigDecimal.compareTo(BigDecimal.ZERO) > -1) {
            creditPackage.setNumOfCredits(bigDecimal);
        }

        System.out.print("Enable Package (Enter Y, N, or blank if no change)> ");
        sc.nextLine();
        input = sc.nextLine();
        if (input.equals("Y")) {
            creditPackage.setEnabled(true);
        } else if (input.equals("N")) {
            creditPackage.setEnabled(false);
        }

        creditPackageController.updateCreditPackage(creditPackage);
        System.out.println("Credit Package updated successfully!\n");
    }

    private void deleteCreditPackage(CreditPackage creditPackage) {
        System.out.println("*** View All Credit Packages ***\n");
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.printf("Confirm Delete Credit Package %s (Credit Package ID: %d) (Enter 'Y' to Delete)> ", creditPackage.getCreditPackageName(), creditPackage.getCreditPackageID());
        input = sc.nextLine().trim();
        if (input.equals("Y")) {
            creditPackageController.deleteCreditPackage(creditPackage.getCreditPackageID());
            System.out.println("Credit Package deleted successfully!\n");

        } else {
            System.out.println("Credit Package NOT deleted!\n");
        }
    }
}
