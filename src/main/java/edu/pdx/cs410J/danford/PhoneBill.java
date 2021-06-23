

package edu.pdx.cs410J.danford;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.*;

/**
 * This is the PhoneBill class
 * It holds a string of the CustomersName and a treeset of PhoneCalls.
 */

public class PhoneBill extends AbstractPhoneBill {

    String CustomersName;
    TreeSet<PhoneCall> phoneCallList = new TreeSet<>(new Sorter());

    /**
     * This returns the customer's name. I should made the member private
     * @return returns the name of the customer.
     */
    @Override
    public String getCustomer() {
        return this.CustomersName;
    }

    /**
     * This adds a phone call to the phone call list
     * I should add a return? At least a fail condition
     * @param abstractPhoneCall this is a phone call, it gets casted when being added.
     */
    @Override
    public void addPhoneCall(AbstractPhoneCall abstractPhoneCall) {
        phoneCallList.add((PhoneCall) abstractPhoneCall);
        Sorter sorter = new Sorter();
     //   phoneCallList.sort(sorter);
    }

    /**
     * this gets a list of phonecalls. I need to change this to a collection
     * At the moment it prints out all of the phonecalls.
     * @return returns the list of phone calls.
     */
    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        Iterator<PhoneCall> iterate = phoneCallList.iterator();
        PhoneCall temp;
        while(iterate.hasNext()) {
            temp = iterate.next();
            System.out.println(temp.phoneCallToOutput());
        }
        return phoneCallList;
    }
/*
    /**
     * Helper function to write a data string from a phone bill
     * @param index The index of the arraylist that holds the phone bill
     * @return returns the data string
     */
    /*
    public String writeToDataString(int index) {
        PhoneCall call = this.phoneCallList.get(index);
        return call.phoneCallToDataFile();
    }

     */
}
