package be.heh.epm.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

public class WeeklyPaymentSchedule implements PaymentSchedule {

    @Override
    public boolean isPayDate(LocalDate payDate) {
        DayOfWeek dayOfWeek = payDate.getDayOfWeek();
        return dayOfWeek.equals(DayOfWeek.FRIDAY);
    }

    @Override
    public String getType() {
        return "weekly";
    }
}
