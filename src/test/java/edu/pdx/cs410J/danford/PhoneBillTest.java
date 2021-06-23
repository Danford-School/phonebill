package edu.pdx.cs410J.danford;

import junit.framework.TestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This is the PhoneBill test suite.
 */

public class PhoneBillTest extends TestCase {

    //These are the testing members
    PhoneBill bill = new PhoneBill();
    PhoneCall call = new PhoneCall("Hot Soccermom", "111-222-3333", "444-555-6666", "6/26/1985", "4:20", "PM", "06/26/1985",
            "4:30", "PM");

    /**
     * This tests the addPhoneCall method for accuracy.
     */
    public void testAddPhoneCall() {
        bill.phoneCallList.add(call);
        bill.CustomersName = call.theCustomer;
        assertThat(bill.CustomersName, containsString("Hot Soccermom"));
    }

    /**
     * This tests the getCustomer method for accuracy.
     */
    @Test
    public void testGetCustomer() {
        bill.phoneCallList.add(call);
        bill.CustomersName = call.theCustomer;
        assertEquals(bill.getCustomer(), "Hot Soccermom");
    }

    /**
     * This tests the getPhoneCalls method for accuracy.
    * This isn't great. When I try to assert other things it doesn't work well. I will look for clarification.
    * Now I think because it's an arraylist and not a collection. I'm really just reverting to C so I should investigate.
     * It will need to be fixed in the next version when I change to collection
     */

    public void testGetPhoneCalls() {
        bill.phoneCallList.add(call);
        PhoneBill phoneBillTest = new PhoneBill();
        phoneBillTest.addPhoneCall(call);
        assertEquals(bill.getPhoneCalls(), phoneBillTest.getPhoneCalls());

        if(!bill.phoneCallList.contains(call)) {
            System.err.println("Phone bill does not contain phone calls");
        }
    }


}