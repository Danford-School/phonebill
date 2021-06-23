package edu.pdx.cs410J.danford;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class Project3Test {
    /**
     * This tests the doAllTheThings method which is really just a 'main' replacement.
     */
    @Test
    public void testDoAllTheThings() throws IOException {
        String[] args = {"Benny Schwazz", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/84",
                "4:30", "PM"};

        Project3.doAllTheThings(args);
    }

    /**
     * This tests the doAllTheThings method again, but with options to ensure that I am not mathing badly later on.
     */
    @Test
    public void testDoAllTheThingsWithOptions() throws IOException {
        String[] args = {"-print", "-textFile", "testOutput1.txt", "Benny Schwazz", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM"};

        Project3.doAllTheThings(args);
    }

    /**
     * This checks the createCall function for accuracy.
     */
    @Test
    public void checkCreateCall() {
        String[] args = {"Benny Schwazz", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM"};
        int count = 9;
        PhoneCall call = Project3.createCall(args, count);
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

    /**
     * This also tests the createCall method, but with 8 arguments. This will fail because 'a thing' is not an option.
     */
    @Test(expected = IllegalArgumentException.class)
    public void checkDoAllTheThingsWithEightFail() throws IOException {
        String[] args = {"a thing", "Benny Schwazz", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM"};
        Project3.doAllTheThings(args);
    }

    /**
     * This also tests the createCall method, but with 9 arguments. This will fail because 'a thing' is not an option.
     */
    @Test(expected = IllegalArgumentException.class)
    public void checkDoAllTheThingsWithNineFail() throws IOException {
        String[] args = {"a thing", "-print", "Benny Schwazz", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM"};
        Project3.doAllTheThings(args);
    }

    /**
     * This also tests the createCall method, but with 10 arguments. This will fail because 'a thing' is not an option.
     */
    @Test(expected = IllegalArgumentException.class)
    public void checkDoAllTheThingsWithTenFail() throws IOException {
        String[] args = {"a thing", "-textFile", "fileName.txt", "Benny Schwazz", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM"};
        Project3.doAllTheThings(args);
    }

    /**
     * This checks to ensure that the code returned by passing too few arguments is correct.
     */
    @Test
    public void tooFewArgumentsInCheckArgsLength() {
        String[] args = {"Jim Dandy", "111-222-3333", "444-555-6666"};
        if (Project3.checkArgsLength(args) != -1) {
            System.err.println("Too few Args test failed");
        }
    }

    /**
     * This checks to ensure that the code returned when there are too many arguments is accuate.
     */
    @Test
    public void tooManyArgumentsInCheckArgsLength() {
        String[] args = {"Jim Dandy", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984", "4:30", "PM", "I", "needed", "more", "strings", "for", "testing" };
        if(Project3.checkArgsLength(args) != -1) {
            System.err.println("Too many args test failed");
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void checkTimeOrderMinFail() {
        Project3.checkTimeOrder("2:30", "pm", "2:20", "pm");
    }

    @Test (expected = IllegalArgumentException.class)
    public void checkTimeOrderHoursFail() {
        Project3.checkTimeOrder("3:30", "pm", "2:40", "pm");
    }

    @Test
    public void checkTimeOrderAMPMException() {
        Project3.checkTimeOrder("12:30", "am", "1:30", "pm");
    }

    /**
     * This tests the phoneNumberCheck to ensure accuracy.
     */
    @Test
    public void testPhoneNumberCheckPass() {
        PhoneCall call = new PhoneCall("Benny Schwazz", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM");
        assertThat(call.theCustomersNumber, containsString("111-222-3333"));
        assertThat(call.isCallingThisNumber, containsString("444-555-6666"));
    }

    /**
     * This tests the phoneNumberCheck and should fail.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPhoneNumberCheckFail(){
        PhoneCall call = new PhoneCall("Benny Schwazz", "Ima-big-taco", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM");
    }

    /**
     * This tests the phoneNumberCheck and should fail in a different way.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPhoneNumberCheckFailPartTwo(){
        PhoneCall call = new PhoneCall("Benny Schwazz", "5555-6666-7777", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM");
    }

    /**
     * This tests the dateChecker and should pass.
     */
    @Test
    public void testDateCheckerPass() {
        PhoneCall call = new PhoneCall("Benny Schwazz", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM");
        assertThat(call.thisIsTheStartDate, containsString("5/29/1984"));
        assertThat(call.thisIsTheEndDate, containsString("05/29/1984"));
    }

    /**
     * This tests the dateChecker and should fail.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDateCheckerFail() {
        PhoneCall call = new PhoneCall("Benny Schwazz", "111-222-3333", "444-555-6666", "Tr/ea/sure", "4:20", "PM", "05/29/1984",
                "4:30", "PM");
    }

    /**
     * This tests the timeChecker method and should pass.
     */
    @Test
    public void testTimeCheckerPass() {
        PhoneCall call = new PhoneCall("Benny Schwazz", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM");
        assertThat(call.thisIsTheStartTime, containsString("4:20"));
        assertThat(call.thisIsTheEndTime, containsString("4:30"));
    }

    /**
     * This tests the timeChecker method and should fail.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTimeCheckerFail() {
        PhoneCall call = new PhoneCall("Benny Schwazz", "111-222-3333", "444-555-6666", "5/29/1984", "TA:CO", "PM", "05/29/1984",
                "4:30", "PM");
    }

    /**
     * This tests to ensure it can grab the file name for pretty print
     */
    @Test
    public void testPrettyFileGrab() {
        String[] args = {"-pretty", "fileName", "some", "other", "stuff"};
        String test = Project3.grabPrettyName(args);
        assertThat(test, containsString("fileName"));
    }

    @Test
    public void testPrettyOutput(){
        PhoneCall call = new PhoneCall("Tommy Middle-D", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM");
        assertThat(call.phoneCallToOutput(), containsString("Tommy Middle-D at 111-222-3333 is calling 444-555-6666 starting on 5/29/1984 at 4:20 pm, and ending on 05/29/1984 at 4:30 pm."));
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

    /**
     * This tests to ensure that the shouldPrint boolean changes when -print is selected.
     */
    @Test
    public void testPrint() {
        String[] args = {"-print", "Benny Schwazz", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM"};
        Boolean shouldPrintTest = false;
        shouldPrintTest = Project3.shouldIPrint(args);
        assertThat(shouldPrintTest.toString(), true);
    }

    /**
     * This test the parser. It takes a formatted string and a bill and returns a bill populated with said info.
     * @throws IOException It doesn't really.
     * @throws ParserException  It would do this if this was designed to fail.
     */
    @Test
    public void testParseDataFile() throws IOException, ParserException {
        String args = "Alimony Tony,999-888-7777,666-555-4444,05/29/1984,4:20,PM,5/29/1984,4:30,PM";
        PhoneBill bill = new PhoneBill();
        TextParser parser = new TextParser();
        parser.parse(args, bill);
        PhoneCall call = bill.phoneCallList.first();
        assertThat(call.theCustomer, containsString("Alimony Tony"));
        assertThat(call.theCustomersNumber, containsString("999-888-7777"));
        assertThat(call.isCallingThisNumber, containsString("666-555-4444"));
        assertThat(call.thisIsTheStartDate, containsString("05/29/1984"));
        assertThat(call.thisIsTheStartTime, containsString("4:20"));
        assertThat(call.startTimeAMPM, containsString("pm"));
        assertThat(call.thisIsTheEndDate, containsString("5/29/1984"));
        assertThat(call.thisIsTheEndTime, containsString("4:30"));
        assertThat(call.endTimeAMPM, containsString("pm"));
    }

    /**
     * This checks to see if the README.txt can be read.
     * @throws IOException when it cannot read the file
     */
    @Test
    public void readmeCanBeReadAsResource() throws IOException {
        try (

                InputStream readme = Project3.class.getResourceAsStream("README.txt")
        ) {
            assertThat(readme, not(nullValue()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String line = reader.readLine();
            assertThat(line, containsString("This is a README file!"));
        }
    }
}
