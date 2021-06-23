package edu.pdx.cs410J.danford;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class SorterTest {
    @Test
    public void testCompare() {
        PhoneCall firstCall = new PhoneCall("Hot Soccermom", "111-222-3333", "444-555-6666", "6/26/1985", "3:20", "PM", "06/26/1985",
                "4:30", "PM");
        PhoneCall secondCall = new PhoneCall("Tommy Middle-D", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM");
        Sorter sort = new Sorter();
        int test = sort.compare(firstCall, secondCall);
        String realTest = String.valueOf(test);
        assertThat(realTest, containsString("1"));
    }

    @Test
    public void testCompare2() {
        PhoneCall firstCall = new PhoneCall("Hot Soccermom", "111-222-3333", "444-555-6666", "6/26/1985", "4:20", "PM", "06/26/1985",
                "4:30", "PM");
        PhoneCall secondCall = new PhoneCall("Tommy Middle-D", "222-333-4444", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM");
        Sorter sort = new Sorter();
        int test = sort.compare(firstCall, secondCall);
        String realTest = String.valueOf(test);
        assertThat(realTest, containsString("1"));
    }
}
