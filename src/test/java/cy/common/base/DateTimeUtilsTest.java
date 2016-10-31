
package cy.common.base;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DateTimeUtilsTest {

    @Before
    public void setUp() throws Exception {}

    @Test
    public void testGetDaysOfYear() {
        Assert.assertEquals(DateTimeUtils.getDaysOfYear(2016), 366);
        Assert.assertEquals(DateTimeUtils.getDaysOfYear(2017), 365);
        Assert.assertEquals(DateTimeUtils.getDaysOfYear(2018), 365);
        Assert.assertEquals(DateTimeUtils.getDaysOfYear(2019), 365);
        Assert.assertEquals(DateTimeUtils.getDaysOfYear(2020), 366);
        Assert.assertEquals(DateTimeUtils.getDaysOfYear(2021), 365);
        Assert.assertEquals(DateTimeUtils.getDaysOfYear(2022), 365);
    }

    @Test
    public void testGetEndDayOfYear() {
        int year = 2016;
        Assert.assertEquals(DateTimeUtils.getEndDayOfYear(year), new LocalDate(year, 12, 31));
        year++;
        Assert.assertEquals(DateTimeUtils.getEndDayOfYear(year), new LocalDate(year, 12, 31));
        year++;
        Assert.assertEquals(DateTimeUtils.getEndDayOfYear(year), new LocalDate(year, 12, 31));
        year++;
        Assert.assertEquals(DateTimeUtils.getEndDayOfYear(year), new LocalDate(year, 12, 31));
        year++;
        Assert.assertEquals(DateTimeUtils.getEndDayOfYear(year), new LocalDate(year, 12, 31));
        year++;
        Assert.assertEquals(DateTimeUtils.getEndDayOfYear(year), new LocalDate(year, 12, 31));
        year++;
        Assert.assertEquals(DateTimeUtils.getEndDayOfYear(year), new LocalDate(year, 12, 31));
        year++;
        Assert.assertEquals(DateTimeUtils.getEndDayOfYear(year), new LocalDate(year, 12, 31));
        year++;
        Assert.assertEquals(DateTimeUtils.getEndDayOfYear(year), new LocalDate(year, 12, 31));
        year++;
        Assert.assertEquals(DateTimeUtils.getEndDayOfYear(year), new LocalDate(year, 12, 31));
        year++;
    }

    @Test
    public void testGetFirstDayOfYear() {
        int year = 2016;
        System.out.println(DateTimeUtils.getFirstDayOfYear(year));
        for (int i = 0; i < 100; i++) {
            Assert.assertEquals(DateTimeUtils.getFirstDayOfYear(year), new LocalDate(year, 1, 1));
            year++;
        }
    }

    @Test
    public void testGetFirstDayOfNextYear() {
        int year = 2016;
        for (int i = 0; i < 100; i++) {
            Assert.assertEquals(DateTimeUtils.getFirstDayOfNextYear(year), new LocalDate(year + 1, 1, 1));
            year++;
        }
    }

    @Test
    public void testGetWorkingDayOfYear() {
        DateTimeUtils.getWorkingDaysOfYear(2016, null);
    }

}
