package be.heh.epm.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Employee {
    @Getter
    private int empID;
    @Getter @Setter
    private String name;
    @Getter
    private String address;
    @Getter @Setter
    private String mail;
    @Getter @Setter
    private PaymentClassification payClassification;
    @Getter @Setter
    private PaymentMethod payMethod;
    @Getter @Setter
    private PaymentSchedule paySchedule;

    public Employee(int empID, String name, String address, String mail) {
        this.empID = empID;
        this.name = name;
        this.address = address;
        this.mail = mail;
    }

    public void payDay(PayCheck pc) {
        double salary = getPayClassification().calculatePay(pc);
        pc.setPay(salary);
        payMethod.pay(pc);
    }

    public boolean isDatePay(LocalDate date) {
        return paySchedule.isPayDate(date);
    }
}
