package be.heh.epm.application.service;


import be.heh.epm.application.port.in.AddEmployeeSalariedValidating;
import be.heh.epm.application.port.in.EmployeeSalariedValidating;
import be.heh.epm.application.port.out.EmployeeGateway;
import be.heh.epm.domain.*;

public class AddSalariedEmployee  {

    private AddEmployeeSalariedValidating addEmployeeSalariedValidating;
    private EmployeeGateway employeeGateway;

    public AddSalariedEmployee(EmployeeGateway employeeGateway) {
        this.employeeGateway = employeeGateway;
    }

    public void execute(EmployeeSalariedValidating EmployeeSalariedValidating) {
        PaymentClassification pc = new SalariedClassification(EmployeeSalariedValidating.getMonthlySalary());
        PaymentSchedule ps = new MonthlyPaymentSchedule();
        PaymentMethod pm = new DirectDepositMethod("Fortis","be332211");

        Employee e = new Employee(EmployeeSalariedValidating.getEmpId(),EmployeeSalariedValidating.getName(),EmployeeSalariedValidating.getAddress(),EmployeeSalariedValidating.getMail());
        e.setPayClassification(pc);
        e.setPaySchedule(ps);
        e.setPayMethod(pm);

        employeeGateway.save(EmployeeSalariedValidating.getEmpId(),e);

    }

}
