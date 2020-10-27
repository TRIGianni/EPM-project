package be.heh.epm.application.service;

import static org.junit.Assert.*;

import be.heh.epm.application.InMemoryEmployeeGateway;
import be.heh.epm.application.port.in.AddEmployeeSalariedValidating;
import be.heh.epm.application.port.in.EmployeeSalariedValidating;
import be.heh.epm.application.port.out.EmployeeGateway;
import be.heh.epm.domain.*;
import org.junit.Before;

import org.junit.Ignore;
import org.junit.Test;

public class TestAddEmployee {
    private EmployeeGateway employeeGateway;

    @Before
    public void setUp() {
        employeeGateway = new InMemoryEmployeeGateway();
    }

    @Test
    public void testAddSalariedEmployee() {
        //AddEmployeeSalariedValidating addEmployeeSalariedValidating = new AddEmployeeSalariedValidating(1, "Bob", "Home", "toto@gmail.com", 1500);
        AddSalariedEmployee addSalariedEmployee = new AddSalariedEmployee(employeeGateway);

        EmployeeSalariedValidating employeeSalariedValidating = new EmployeeSalariedValidating();
        employeeSalariedValidating.setEmpId(1);
        employeeSalariedValidating.setName("toto");
        employeeSalariedValidating.setAddress("rue de Mons");
        employeeSalariedValidating.setMail("toto@heh.be");
        employeeSalariedValidating.setMonthlySalary(1500);

        addSalariedEmployee.execute(employeeSalariedValidating);

        Employee e = employeeGateway.getEmployee(employeeSalariedValidating.getEmpId());
        assertEquals("toto", e.getName());

        PaymentSchedule ps = e.getPaySchedule();
        assertTrue(ps instanceof MonthlyPaymentSchedule);

        PaymentMethod pm = e.getPayMethod();
        assertEquals("direct deposit into Fortis : be332211", pm.toString());
    }
/*
    @Ignore
    @Test
    public void testAddHourlyEmployee() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "BobHourly", "Home_Hourly","toto@gmail.com", 20.0,employeeGateway);
        t.execute();

        Employee e = employeeGateway.getEmployee(empId);
        assertEquals("BobHourly", e.getName());

        PaymentSchedule ps = e.getPaySchedule();
        assertTrue(ps instanceof WeeklyPaymentSchedule);

        PaymentMethod pm = e.getPayMethod();
        assertEquals("direct deposit into Fortis : be332211", pm.toString());
    }
    */

}
