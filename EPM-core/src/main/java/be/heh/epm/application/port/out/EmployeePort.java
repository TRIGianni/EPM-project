package be.heh.epm.application.port.out;


import be.heh.epm.domain.Employee;

import java.util.ArrayList;

public interface EmployeePort {
    Employee getEmployee(int empID);

    Employee save(Employee employee);

    void deleteEmployee(int empID);

    ArrayList<Employee> getAllEmployee();
}
