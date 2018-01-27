/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oasadminpanel;

import Entity.Employee;
import ejb.session.stateless.EmployeeControllerRemote;
import java.util.List;
import java.util.Scanner;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidAccessRightException;

/**
 *
 * @author josh
 */
class AdminModule {

    private EmployeeControllerRemote employeeController;

    private Employee employee;

    public AdminModule() {
    }

    public AdminModule(Employee employee, EmployeeControllerRemote employeeController) {
        this.employeeController = employeeController;
        this.employee = employee;
    }

    public void adminMenu() throws InvalidAccessRightException {
        if (employee.getAccessRight() != EmployeeAccessRightEnum.SYSADMIN) {
            throw new InvalidAccessRightException("Insufficient system rights to access admin module.");
        }

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** OAS System Admin Menu ***\n");
            System.out.println("1: Create New Employee");
            System.out.println("2: View Employee Details");
            System.out.println("3: View All Employees");
            System.out.println("4: Back\n");
            response = 0;

            OUTER:
            while (response < 1 || response > 4) {
                System.out.print("> ");
                response = scanner.nextInt();
                switch (response) {
                    case 1:
                        createNewEmployee();
                        break;
                    case 2:
                        viewEmployeeDetails();
                        break;
                    case 3:
                        viewAllEmployees();
                        break;
                    case 4:
                        break OUTER;
                    default:
                        System.out.println("Invalid input, please try again!\n");
                        break;
                }
            }

            if (response == 4) {
                break;
            }
        }
    }

    private void createNewEmployee() {
        System.out.println("*** Create New Employee ***\n");
        Scanner sc = new Scanner(System.in);
        String firstName, lastName, username, password;
        EmployeeAccessRightEnum accessRight;
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

        while (true) {
            System.out.print("Select Access Right (1: Finance, 2: Sales, 3: Adminstrator)> ");
            Integer choice = 0;
            try {
                choice = Integer.parseInt(sc.next());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter numeric values.\n");
                continue;
            }

            if (choice >= 1 && choice <= 3) {
                accessRight = EmployeeAccessRightEnum.values()[choice - 1];
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }
        sc.nextLine(); 
        Boolean uniqueUsername = false;
        do {
            System.out.print("Enter username> ");
            username = sc.nextLine().trim();
            try {
                Employee a = employeeController.retrieveEmployeeByUsername(username);
            } catch (EmployeeNotFoundException ex) {
                uniqueUsername = true;
            }
            if (username.isEmpty()) {
                System.out.println("Last name cannot be empty!\n");
                uniqueUsername = true;
            }
            if (!uniqueUsername) {
                System.out.println("Username is not available.\n");
            }
        } while (username.isEmpty() || !uniqueUsername);

        Boolean samePassword = true;
        do {
            System.out.print("Enter password> ");
            password = sc.nextLine().trim();
            System.out.print("Confirm password> ");
            String confirmPassword = sc.nextLine().trim();
            samePassword = password.equals(confirmPassword);
            if (!samePassword) {
                System.out.println("Passwords do not match!\n");
            }
            if (password.isEmpty()) {
                System.out.println("Password must not be empty!\n");
            }

        } while (password.isEmpty() || !samePassword);

        System.out.println();

        Employee newEmployee = new Employee(firstName, lastName, username, password, accessRight);

        newEmployee = employeeController.createEmployee(newEmployee);

        System.out.println("Employee ID: " + newEmployee.getEmployeeID() + " created!\n\n");

    }

    private void viewEmployeeDetails() {
        System.out.println("*** OAS Administration Panel :: System Administration :: View Employee Details ***\n");
        Scanner sc = new Scanner(System.in);

        Long employeeID = new Long(-1);

        do {
            System.out.print("Enter Employee ID> ");
            try {
                employeeID = Long.parseLong(sc.next());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter numeric values.");
            }
        } while (employeeID.equals(-1));

        try {
            Employee employee = employeeController.retrieveEmployeeByID(employeeID);
            System.out.println("Employee ID\tFirst Name\tLast Name\t\t\tUsername\tPassword\tAccess Right");
            System.out.println(employee.getEmployeeID().toString() + "\t\t" + employee.getFirstName() + "\t\t" + employee.getLastName() + "\t\t\t" + employee.getUsername() + "\t\t" + employee.getPassword() + "\t\t" + employee.getAccessRight().toString());
            System.out.println("------------------------");
            System.out.println("1: Update Employee");
            System.out.println("2: Delete Employee");
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
                    updateEmployee(employee);
                    break;
                case 2:
                    deleteEmployee(employee);
                    break;
                case 3:
                default:
                    break;
            }

        } catch (EmployeeNotFoundException ex) {
            System.out.println(ex);
        }

    }

    private void updateEmployee(Employee employee) {
        Scanner sc = new Scanner(System.in);
        String input;

        System.out.println("*** Update Employee ***\n");
        System.out.print("Enter First Name (blank if no change)> ");
        input = sc.nextLine().trim();
        if (input.length() > 0) {
            employee.setFirstname(input);
        }

        System.out.print("Enter Last Name (blank if no change)> ");
        input = sc.nextLine().trim();
        if (input.length() > 0) {
            employee.setLastName(input);
        }

        while (true) {
            System.out.print("Select Access Right (0: No Change, 1: Finance, 2: Sales, 3: Adminstrator)> ");

            Integer accessRightInt = 0;
            try {
                accessRightInt = Integer.parseInt(sc.next());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter numeric values.\n");
                continue;
            }

            if (accessRightInt >= 1 && accessRightInt <= 3) {
                employee.setAccessRight(EmployeeAccessRightEnum.values()[accessRightInt - 1]);
                break;
            } else if (accessRightInt == 0) {
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }

        sc.nextLine();
        System.out.print("Enter Username (blank if no change)> ");
        input = sc.nextLine().trim();
        if (input.length() > 0) {
            employee.setUsername(input);
        }

        System.out.print("Enter Password (blank if no change)> ");
        input = sc.nextLine().trim();
        if (input.length() > 0) {
            employee.setPassword(input);
        }

        employeeController.updateEmployee(employee);
        System.out.println("Employee updated successfully!\n");
    }

    private void deleteEmployee(Employee employee) {
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.println("*** Delete Employee ***\n");
        System.out.printf("Confirm Delete Employee %s %s (Employee ID: %d) (Enter 'Y' to Delete)> ", employee.getFirstName(), employee.getLastName(), employee.getEmployeeID());
        input = sc.nextLine().trim();

        if (input.equalsIgnoreCase("Y")) {
            try {
            employeeController.deleteEmployee(employee.getEmployeeID());
            System.out.println("Employee deleted successfully!\n");
            } catch (EmployeeNotFoundException ex) {
                System.out.println("Employee not found!\n");
            }

        } else {
            System.out.println("Employee NOT deleted!\n");
        }
    }

    private void viewAllEmployees() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** View All Employees ***\n");
        List<Employee> employees = employeeController.retrieveAllEmployees();
        System.out.printf("%10s%20s%20s%15s%20s%20s\n", "Employee ID", "First Name", "Last Name", "Username", "Password", "Access Right");
        for (Employee employeeHere : employees) {
            System.out.printf("%10s%20s%20s%15s%20s%20s\n", employeeHere.getEmployeeID().toString(), employeeHere.getFirstName(), employeeHere.getLastName(), employeeHere.getUsername(), employeeHere.getPassword(), employeeHere.getAccessRight().toString());
        }
        System.out.print("Press enter to continue...> ");
        sc.nextLine();
    }
}
