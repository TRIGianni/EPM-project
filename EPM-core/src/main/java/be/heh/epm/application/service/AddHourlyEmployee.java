package be.heh.epm.application.service;


import be.heh.epm.application.port.out.EmployeeGateway;
import be.heh.epm.domain.MonthlyPaymentSchedule;
import be.heh.epm.domain.PaymentClassification;
import be.heh.epm.domain.PaymentSchedule;
import be.heh.epm.domain.SalariedClassification;

public class AddHourlyEmployee {

    private double monthlySalary;

    public AddHourlyEmployee(int empId, String name, String address, String mail, double monthlySalary, EmployeeGateway employeeGateway) {
        this.monthlySalary = monthlySalary;
    }

    protected PaymentSchedule makePaymentSchedule() {
        return new MonthlyPaymentSchedule();
    }

    protected PaymentClassification makePaymentClassification() {
        return new SalariedClassification(monthlySalary);
    }

}
