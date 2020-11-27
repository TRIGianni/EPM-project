package be.heh.epm.application.service;

import be.heh.epm.application.port.in.AddSalariedEmployeeUseCase;
import be.heh.epm.application.port.in.EmployeeSalariedValidating;
import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.common.UseCase;
import be.heh.epm.domain.*;

@UseCase
public class AddSalariedEmployeeService implements AddSalariedEmployeeUseCase {

    //private AddEmployeeSalariedValidating addEmployeeSalariedValidating;
    private EmployeePort employeePort;

    public AddSalariedEmployeeService(EmployeePort employeePort) {
        this.employeePort = employeePort;
    }

    public void execute(EmployeeSalariedValidating employeeSalariedValidating) {
        PaymentClassification pc = new SalariedClassification(employeeSalariedValidating.getMonthlySalary());
        PaymentSchedule ps = new MonthlyPaymentSchedule();
        PaymentMethod pm = new DirectDepositMethod("Fortis","be332211");

        Employee e = new Employee(employeeSalariedValidating.getName(),employeeSalariedValidating.getAddress(),employeeSalariedValidating.getMail());
        e.setPayClassification(pc);
        e.setPaySchedule(ps);
        e.setPayMethod(pm);

        Employee employee = employeePort.save(e);

    }

}
