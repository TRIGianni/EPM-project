package be.heh.epm.application;

import be.heh.epm.application.port.out.EmployeeGateway;
import be.heh.epm.domain.Employee;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class InMemoryEmployeeGateway implements EmployeeGateway {
    private Map<Integer, Employee> employees = new HashMap();
    @Override
    public Employee getEmployee(int empID) {

        return employees.get(empID);
    }

    @Override
    public void save(int empId, Employee employee) {
        employees.put(empId, employee);
    }

    @Override
    public void deleteEmployee(int empID) {
        employees.remove(empID);
    }

    @Override
    public ArrayList<Employee> getAllEmployee() {
        return new ArrayList<>(employees.values());
    }
}
