package cy.common.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

public class DateTimeUtils {

    public static LocalDate getFirstDayOfNextYear(int year) {
        return new LocalDate(year + 1, 1, 1);
    }

    public static LocalDate getFirstDayOfYear(int year) {
        return new LocalDate(year, 1, 1);
    }

    public static LocalDate getEndDayOfYear(int year) {
        return new LocalDate(year + 1, 1, 1).minusDays(1);
    }

    /**
     * get days of the whole year.
     */
    public static int getDaysOfYear(int year) {

        LocalDate date = new LocalDate(year, 1, 1);
        LocalDate end = new LocalDate(year + 1, 1, 1);
        return new Period(date, end, PeriodType.days()).getDays();
    }

    /**
     * get day lists except holidays
     */
    public static List<LocalDate> getWorkingDaysOfYear(int year, List<LocalDate> holidays) {

        LocalDate date = new LocalDate(year, 1, 1);
        LocalDate end = new LocalDate(year + 1, 1, 1);
        List<LocalDate> list = new ArrayList<LocalDate>();

        while (date.isBefore(end)) {
            if (holidays != null && !holidays.contains(date)) {
                list.add(date);
            }
            date = date.plusDays(1);
        }

        return Collections.unmodifiableList(list);
    }
}
