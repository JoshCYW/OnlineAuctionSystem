/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Employee;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author josh
 */
@Stateless
@Local(EmployeeControllerLocal.class)
@Remote(EmployeeControllerRemote.class)
public class EmployeeController implements EmployeeControllerRemote, EmployeeControllerLocal {

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @Override
    public Employee createEmployee(Employee employee) {
        em.persist(employee);
        em.flush();
        em.refresh(employee);

        return employee;
    }

    @Override
    public void deleteEmployee(Long employeeID) throws EmployeeNotFoundException {
        try {
            Employee employee = retrieveEmployeeByID(employeeID);
            em.remove(employee);
        } catch (EmployeeNotFoundException ex) {
            throw new EmployeeNotFoundException("Employee ID: " + employeeID + " does not exist");
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        em.merge(employee);
    }

    @Override
    public Employee retrieveEmployeeByID(Long employeeID) throws EmployeeNotFoundException {
        Employee employee = em.find(Employee.class, employeeID);

        if (employee != null) {
            return employee;
        } else {
            throw new EmployeeNotFoundException("Employee ID: " + employeeID + " does not exist");
        }
    }

    @Override
    public Employee retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException {
        Query query = em.createQuery("SELECT c FROM Employee c WHERE c.username = :inUsername");
        query.setParameter("inUsername", username);
        try {
            return (Employee) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new EmployeeNotFoundException("Employee Username " + username + " does not exist!");
        }
    }

    @Override
    public List<Employee> retrieveAllEmployees() {
        Query query = em.createQuery("SELECT c FROM Employee c");

        return query.getResultList();
    }

    @Override
    public Employee employeeLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Employee employee = retrieveEmployeeByUsername(username);
            if (employee.getPassword().equals(password)) {
                return employee;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (EmployeeNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

}
