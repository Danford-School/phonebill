package edu.pdx.cs410J.danford;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

public class TextParser implements PhoneBillParser {

    /**
     * This is the override, but I don't use it. I like arguments.
     * @return Nope.
     * @throws ParserException It sure will.
     */
    @Override
    public AbstractPhoneBill parse() throws ParserException {
        throw new ParserException("parse(String, PhoneBill)");
    }

    /**
     * This is the parse that I actually use. It parses a string in the correct format. The checks should make it fail
     * if the data is wrong.
     * @param line The string from a file that gets parsed.
     * @param bill The bill that the parsed data gets added to
     * @return Returns the updated bill when it's done
     * @throws ParserException It does, it really does.
     */
    public AbstractPhoneBill parse(String line, PhoneBill bill) throws ParserException {
        String[] temp = line.split(",");
        bill.CustomersName = temp[0];
        PhoneCall call = new PhoneCall();
        call.theCustomer = temp[0];
        call.theCustomersNumber = phoneNumberCheck(temp[1]);
        call.isCallingThisNumber = phoneNumberCheck(temp[2]);
        call.thisIsTheStartDate = dateChecker(temp[3]);
        call.thisIsTheStartTime = timeChecker(temp[4]);
        call.startTimeAMPM = checkAMPM(temp[5]);
        call.thisIsTheEndDate = dateChecker(temp[6]);
        call.thisIsTheEndTime = timeChecker(temp[7]);
        call.endTimeAMPM = checkAMPM(temp[8]);
        call.startTimeAndDate = fakeDateString(call.thisIsTheStartDate, call.thisIsTheStartTime, call.startTimeAMPM);
        call.endTimeAndDate = fakeDateString(call.thisIsTheEndDate, call.thisIsTheEndTime, call.endTimeAMPM);
        bill.phoneCallList.add(call);
        return bill;
    }

    /**
     * This just checks to ensure that AM or PM has been selected.
     * @param AMPM The AM or PM field of a call
     * @return Returns the string if it is correct
     */
    public static String checkAMPM(String AMPM) {
        if(AMPM.contains("AM") | AMPM.contains("am") | AMPM.contains("PM") | AMPM.contains("pm")) {
            return AMPM.toLowerCase();
        } else {
            System.err.println("AM or PM required (Case insensitive)");
            throw new IllegalArgumentException();
        }
    }

    /**
     * This checks to ensure the time is in the correct format using regex
     * It returns the date or an IllegalArgumentException()
     * @param time A string of the time that is being checked
     * @return Returns the time if it passes the test.
     */
    public static String timeChecker(String time) {
        if (!time.matches("(1[012]|[1-9]):[0-5][0-9]")) {
            System.out.println("\nThe time is not valid.\nPlease use the form hh:mm or h:mm\n");
            throw new IllegalArgumentException();
        }
        return time;
    }

    /**
     * This checks to ensure the date is in the correct format using regex.
     * It returns the number if it is acceptable, or an IllegalArgumentException
     * @param date A string of the date being checked
     * @return Returns the phone number if it passes
     */
    public static String dateChecker(String date) {
        if(!date.matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$")) {
            System.out.println("\nThe date is not valid.\nPlease use the form mm/dd/yyyy, m/dd/yyyy, mm/d/yyyy, or m/d/yyyy\n");
            throw new IllegalArgumentException();
        }
        return date;
    }

    /**
     * This checks to ensure the phone number is in the correct format using regex.
     * It returns the phone number or an IllegalArgumentException
     * @param aPhoneNumber A string of the phone number that is being tested
     * @return This returns the phone number on success.
     */
    public static String phoneNumberCheck(String aPhoneNumber) {
        if (!aPhoneNumber.matches("\\d{3}-\\d{3}-\\d{4}")) {
            System.out.println("\nThe phone number is not in the correct format.\nPlease use ###-###-####\n");
            throw new IllegalArgumentException();
        }
        return aPhoneNumber;
    }

    /**
     * This is my work-around for date. I will need to fix the date objects in the next version
     * @param date The date of the call in question
     * @param time The time of the call in question
     * @param AMPM The am/pm associated with the time of the call in question
     * @return A string of all of these things that looks pretty. Maybe need a comma?
     */
    public String fakeDateString(String date, String time, String AMPM) {
        return date.concat(", ").concat(time).concat(" ").concat(AMPM);
    }
}
