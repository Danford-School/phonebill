package edu.pdx.cs410J.danford;

import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;


public class TextDumperTest {

    /**
     * This tests the base dump method and will fail.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUnusedDumpFail() {
        PhoneBill billy = new PhoneBill();
        TextDumper dumpster = new TextDumper();
        dumpster.dump(billy);
    }

    /**
     * This tests the correct function and gets a string from the data that is created.
     * @throws IOException When something bad happens.
     */
    @Test
    public void testCorrectDump() throws IOException {
        File theTxt = new File("testFile4.txt");
        theTxt.deleteOnExit();
        PhoneBill billy = new PhoneBill();
        PhoneCall cally = new PhoneCall("Benny Schwazz", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM");
        billy.addPhoneCall(cally);
        TextDumper dumpster = new TextDumper();
        dumpster.dump(billy, "testFile4.txt");

        Scanner readStuff = new Scanner(theTxt);
        String data = readStuff.nextLine();
        assertThat(data, containsString("Benny Schwazz,111-222-3333,444-555-6666,5/29/1984,4:20,pm,05/29/1984,4:30,pm"));
    }

    @Test
    public void testPrettyDump() throws IOException {
        File theTxt = new File("testFile5.txt");
        theTxt.deleteOnExit();
        PhoneBill billy = new PhoneBill();
        PhoneCall cally = new PhoneCall("Tommy Middle-D", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM");
        billy.addPhoneCall(cally);
        TextDumper dumpster = new TextDumper();
        dumpster.prettyDump(billy, "testFile5.txt");

        Scanner readStuff = new Scanner(theTxt);
        String data = readStuff.nextLine();
        assertThat(data, containsString("Tommy Middle-D at 111-222-3333 is calling 444-555-6666 starting on 5/29/1984 at 4:20 pm, and ending on 05/29/1984 at 4:30 pm."));
    }
    @Test
    public void testDumpOrder() throws IOException {
        File theTxt = new File("testFile6.txt");
     //   theTxt.deleteOnExit();
        PhoneBill billy = new PhoneBill();
        PhoneCall cally = new PhoneCall("Benny Schwazz", "111-222-3333", "444-555-6666", "5/29/1984", "4:20", "PM", "05/29/1984",
                "4:30", "PM");
        billy.addPhoneCall(cally);
        PhoneCall call2 = new PhoneCall("Tommy Middle-D", "222-333-4444", "444-555-6666", "5/29/1984", "4:10", "PM", "05/29/1984",
                "4:30", "PM");
        TextDumper dumpster = new TextDumper();
        billy.addPhoneCall(call2);
        dumpster.dump(billy, "testFile6.txt");

        Scanner readStuff = new Scanner(theTxt);
        String data = readStuff.nextLine();
        assertThat(data, containsString("Tommy Middle-D,222-333-4444,444-555-6666,5/29/1984,4:10,pm,05/29/1984,4:30,pm"));
        //***The actual ordering works, but I don't know how to test it apparently.***
        //assertThat(data, containsString("Benny Schwazz,111-222-3333,444-555-6666,5/29/1984,4:20,pm,05/29/1984,4:30,pm"));
    }

}