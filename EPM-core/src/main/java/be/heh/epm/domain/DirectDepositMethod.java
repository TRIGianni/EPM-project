package be.heh.epm.domain;

import lombok.Getter;

public class DirectDepositMethod implements PaymentMethod {

    @Getter
    private String bank;
    @Getter
    private String accountNumber;

    public DirectDepositMethod(String bank, String accountNumber) {
        this.bank = bank;
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString(){
        return String.format("direct deposit into %s : %s", bank,accountNumber);
    }

    @Override
    public void pay(PayCheck payCheck) {
        payCheck.setField("Disposition","Bank");
    }

    @Override
    public String getType() {
        return "direct";
    }
}
