package edu.pdx.cs410J.danford;

import edu.pdx.cs410J.ParserException;
import junit.framework.TestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class TextParserTest {

    @Test
    public void testParse() throws ParserException {
        TextParser parser = new TextParser();
        String testMe = "Benny Schwazz,111-222-3333,444-555-6666,5/29/1984,4:20,pm,05/29/1984,4:30,pm";
        PhoneBill billy = new PhoneBill();
        parser.parse(testMe, billy);
        PhoneCall call = billy.phoneCallList.last();
        assertThat(call.theCustomer, containsString("Benny Schwazz"));
        assertThat(call.theCustomersNumber, containsString("111-222-3333"));
        assertThat(call.isCallingThisNumber, containsString("444-555-6666"));
        assertThat(call.thisIsTheStartDate, containsString("5/29/1984"));
        assertThat(call.thisIsTheStartTime, containsString("4:20"));
        assertThat(call.startTimeAMPM, containsString("pm"));
        assertThat(call.thisIsTheEndDate, containsString("05/29/1984"));
        assertThat(call.thisIsTheEndTime, containsString("4:30"));
        assertThat(call.endTimeAMPM, containsString("pm"));
    }

    @Test (expected = ParserException.class)
    public void testUnusedParseCommand() throws ParserException {
        TextParser parser = new TextParser();
        parser.parse();
    }

    @Test
    public void checkAMPMPass() {
        String pass = "am";
        PhoneCall.checkAMPM(pass);
    }

    @Test (expected = IllegalArgumentException.class)
    public void checkAMPMFail() {
        String fail = "NO";
        PhoneCall.checkAMPM(fail);
    }

    @Test(expected = IllegalArgumentException.class)
    public void phoneNumbersAreNotTacos() {
        PhoneCall call = new PhoneCall("Tommy Middle-D", "ImA-big-Taco", "Ihe-art-taco", "5/29/1984", "4:20", "pm", "05/29/1984",
                "4:30", "pm");
    }

    /**
     * This tests to ensure that dates are not letters. It should fail.
     */
    @Test(expected = IllegalArgumentException.class)
    public void youDontTellTimeWithLetters() {
        PhoneCall call = new PhoneCall("Tommy Middle-D", "111-222-3333", "444-555-6666", "5/29/1984", "A:BC", "PM", "05/29/1984",
                "CD:EF", "PM");
    }

    /**
     * This tests to ensure that the phone numbers cannot have letters. Should fail.
     */
    @Test(expected = IllegalArgumentException.class)
    public void datesAreNotTheAlamo() {
        PhoneCall call = new PhoneCall("Tommy Middle-D", "111-222-3333", "444-555-6666", "Th/eA/lamo", "4:20", "PM", "A/l/amoe",
                "4:30", "PM");
    }
}