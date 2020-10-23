package be.heh.epm.domain;

public class SalariedClassification implements PaymentClassification{

    private double salary;

    public SalariedClassification(double salary) {
        this.salary = salary;
    }

    @Override
    public double calculatePay(PayCheck paychek) {
        return salary;
    }

    @Override
    public String getType() {
        return "salaried";
    }
}
