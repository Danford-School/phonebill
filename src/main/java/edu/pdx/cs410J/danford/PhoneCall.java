package edu.pdx.cs410J.danford;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * This is the PhoneCall class. It extends the abstract version.
 * It has many members.
 */
public class PhoneCall extends AbstractPhoneCall {

  //These are the members that hold all of the pertinent information about the phone call.
  //I should probably make them private. Or if protected is a thing in Java
  String theCustomer;
  String theCustomersNumber;
  String isCallingThisNumber;
  String thisIsTheStartDate;
  String thisIsTheStartTime;
  String startTimeAMPM;
  String thisIsTheEndDate;
  String thisIsTheEndTime;
  String endTimeAMPM;
  String startTimeAndDate;
  String endTimeAndDate;
 // Date formattedStart;
 // Date formattedEnd;

  /**
   * This is the phone call constructor for when you have all of the information. This should be the primary, probably.
   * All fields except the name go through a check.
   * @param Customer The customer's name
   * @param caller The customer's phone number
   * @param callee The number the customer is calling
   * @param startDate The date of when the phone call begins
   * @param startTime The time of when the phone call begins
   * @param startTimeAMPM this is the AM/PM associated with the startTime
   * @param endDate The date of when the phone call ends
   * @param endTime The time of when the phone call ends
   * @param endTimeAMPM The AM/PM associated with the end time
   */
  public PhoneCall(String Customer, String caller, String callee, String startDate, String startTime, String startTimeAMPM, String endDate, String endTime, String endTimeAMPM) {
    this.theCustomer = Customer;
    this.theCustomersNumber = phoneNumberCheck(caller);
    this.isCallingThisNumber = phoneNumberCheck(callee);
    this.thisIsTheStartDate = dateChecker(startDate);
    this.thisIsTheStartTime = timeChecker(startTime);
    this.startTimeAMPM = checkAMPM(startTimeAMPM);
    this.thisIsTheEndDate = dateChecker(endDate);
    this.thisIsTheEndTime = timeChecker(endTime);
    this.endTimeAMPM = checkAMPM(endTimeAMPM);
    this.startTimeAndDate = fakeDateString(this.thisIsTheStartDate, this.thisIsTheStartTime, this.startTimeAMPM);
    this.endTimeAndDate = fakeDateString(this.thisIsTheEndDate, this.thisIsTheEndTime, this.endTimeAMPM);
  }

  /**
   * This is the empty phone call constructor. It initializes all of the fields to null
   */
  public PhoneCall() {
    this.theCustomer = null;
    this.theCustomersNumber = null;
    this.isCallingThisNumber = null;
    this.thisIsTheStartDate = null;
    this.thisIsTheStartTime = null;
    this.startTimeAMPM = null;
    this.thisIsTheEndDate = null;
    this.thisIsTheEndTime = null;
    this.endTimeAMPM = null;
    this.startTimeAndDate = null;
    this.endTimeAndDate = null;
 //   this.formattedStart = null;
 //   this.formattedEnd = null;
  }

  /**
   * This returns the number of the customer that is calling
   * @return This is the phone number of the customer
   */
  @Override
  public String getCaller() {
    return this.theCustomersNumber;
  }

  /**
   * This returns the number that the customer is calling.
   * @return The is the number the customer is calling
   */
  @Override
  public String getCallee() {
    return this.isCallingThisNumber;
  }

  /**
   * This returns the start time, date and am/pm of the phone call.
   * @return Returns "startDate, startTime am/pm
   */
  @Override
  public String getStartTimeString() {
    return fakeDateString(this.thisIsTheStartDate, this.thisIsTheStartTime, this.startTimeAMPM);
  }

  /**
   * This returns the end date, time am/pm.
   * @return Returns startDate, startTime am/pm
   */
  @Override
  public String getEndTimeString() {
    return fakeDateString(this.thisIsTheEndDate, this.thisIsTheEndTime, this.endTimeAMPM);
  }
/*  ******* This is broken and I don't have time to fix it. I will fix it for the next iteration of the program. *****
  /**
   * This takes the seperate strings of (start or end) date and time and the date parameter of the object and creates
   * the date object and populates it, then returns a string in the correct format.
   * @param date this is the start or end date of a phonecall
   * @param time this is the start of end time of a phonecall
   * @return this is the formatted string of the date and time.
   */
  /*
  public String formatAndStringStart(String date, String time, String AMPM, PhoneCall call) {
    Locale locale = Locale.US;
    DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
    Date formattedDate;
    String formattedFinalString = "";
    try {
      formattedDate = formatter.parse(date + time + AMPM);
      call.formattedStart = formattedDate;
      formattedFinalString = formatter.format(call.formattedStart);
//      formattedFinalString.concat(" " + AMPM);
      call.startTimeAndDate = formattedFinalString;
      return formattedFinalString;
    } catch (ParseException e) {
      System.err.println("Failed to create a Date object.");
    }
    return null;
  }

  public String formatAndStringEnd(String date, String time, String AMPM, PhoneCall call) {
    Locale locale = Locale.US;
    DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
    Date formattedDate;
    String formattedFinalString = "";
    try {
      formattedDate = formatter.parse(date + time + AMPM);
      call.formattedEnd = formattedDate;
      formattedFinalString = formatter.format(call.formattedEnd);
//      formattedFinalString.concat(" " + AMPM);
      call.endTimeAndDate = formattedFinalString;
      return formattedFinalString;
    } catch (ParseException e) {
      System.err.println("Failed to create a Date object.");
    }
    return null;
  }
   */

  /**
   * I had some issues with making a Date object, so this is my temporary work-around. Sorry, I'll fix it for the next
   * iteration of the program. This does have the correct output.
   * @param date Date of call in question
   * @param time Time of call in question
   * @param AMPM am/pm of the time of the call in question
   * @return a string with all of the above as a string.
   */
  public String fakeDateString(String date, String time, String AMPM) {
    return date.concat(", ").concat(time).concat(" ").concat(AMPM);
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
   * this takes a PhoneCall and turns it into compact data for a data file.
   * @return The data-fied PhoneCall string
   */
  public String phoneCallToDataFile() {
    return this.theCustomer + "," + this.theCustomersNumber + "," + this.isCallingThisNumber + "," +
            this.thisIsTheStartDate + "," + this.thisIsTheStartTime + "," + this.startTimeAMPM + "," +
            this.thisIsTheEndDate + "," + this.thisIsTheEndTime + "," + this.endTimeAMPM;
  }

  /**
   * This takes a PhoneCall and makes it readable. This is for Project 3 and I just made it because I was here.
   * @return This is the readable string from PhoneCall.
   */
  public String phoneCallToOutput() {
    return this.theCustomer + " at " + this.theCustomersNumber + " is calling " + this.isCallingThisNumber +
            " starting on " + this.thisIsTheStartDate + " at " + this.thisIsTheStartTime + " " + this.startTimeAMPM +
            ", and ending on " + this.thisIsTheEndDate + " at " + this.thisIsTheEndTime + " " + this.endTimeAMPM + ".";
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
}
