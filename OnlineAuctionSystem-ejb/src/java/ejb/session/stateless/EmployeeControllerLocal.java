/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Employee;
import java.util.List;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author josh
 */
public interface EmployeeControllerLocal {

    public Employee createEmployee(Employee employee);

    public void deleteEmployee(Long employeeID) throws EmployeeNotFoundException;

    public void updateEmployee(Employee employee);

    public Employee retrieveEmployeeByID(Long employeeID) throws EmployeeNotFoundException;

    public Employee retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException;

    public List<Employee> retrieveAllEmployees();

    public Employee employeeLogin(String username, String password) throws InvalidLoginCredentialException;
    
}
