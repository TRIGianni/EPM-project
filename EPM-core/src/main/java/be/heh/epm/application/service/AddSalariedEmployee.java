package be.heh.epm.application.service;


import be.heh.epm.application.port.in.AddEmployeeSalariedValidating;
import be.heh.epm.application.port.in.EmployeeSalariedValidating;
import be.heh.epm.application.port.in.IAddSalariedEmployee;
import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.domain.*;

public class AddSalariedEmployee  implements IAddSalariedEmployee {

    private AddEmployeeSalariedValidating addEmployeeSalariedValidating;
    private EmployeePort employeePort;

    public AddSalariedEmployee(EmployeePort employeePort) {
        this.employeePort = employeePort;
    }

    public void execute(EmployeeSalariedValidating EmployeeSalariedValidating) {
        PaymentClassification pc = new SalariedClassification(EmployeeSalariedValidating.getMonthlySalary());
        PaymentSchedule ps = new MonthlyPaymentSchedule();
        PaymentMethod pm = new DirectDepositMethod("Fortis","be332211");

        Employee e = new Employee(EmployeeSalariedValidating.getEmpId(),EmployeeSalariedValidating.getName(),EmployeeSalariedValidating.getAddress(),EmployeeSalariedValidating.getMail());
        e.setPayClassification(pc);
        e.setPaySchedule(ps);
        e.setPayMethod(pm);

        employeePort.save(e);

    }

}
