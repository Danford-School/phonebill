package edu.pdx.cs410J.danford;

import edu.pdx.cs410J.ParserException;

import javax.print.DocFlavor;
import java.io.*;
import java.util.Scanner;
import java.io.Serializable;


/**
 * The main class for the CS410J Phone Bill Project2
 * This one is by Danford Compton
 */
public class Project3 {

    /**
     * This is the main class
     * It takes arguments from the command line, it needs 7 to 11 arguments.
     * options are -README, -print -textFile 'filename'
     * It just calls the doAllTheThings method that does everything.
     * @param args the command line arguments
     */

    public static void main(String[] args) throws IOException {

        if(args.length == 0) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        }
        try {
            doAllTheThings(args);
        } catch (IOException | ArrayIndexOutOfBoundsException e)  {
            System.err.println("Missing command line arguments");
            System.exit(1);
        }
        System.exit(0);
    }
    /**
     * This is the method that replaces main for testing purposes. It takes the command line arguments as a Sting array
     * It tests the input, creates a bill and a call, populates the call, then adds it to the bill.
     * @param args the command line arguments
     */

    public static void doAllTheThings(String[] args) throws IOException {

        boolean shouldPrint = false;

        /* Arguments to help my brain:
        0. -README: shows readme and exits
        1 & 2 [option] -pretty filename
        3 & 4 [option] -textFile filename
        5 [option] -print
        --------------
        Required fields:
        1. Customer's name (string)
        2. Customer's number
        3. Callee's number
        4 & 5 & 6. start Date, time, AM/PM
        7 & 8 & 9. end date, time, AM/PM

        So at least 9 and up to 14
         */
        //Check for README first
        Project3.checkForReadme(args);

        //It will return -1 if there are anything but 9 to 14 arguments.
        int count = checkArgsLength(args);

        if(count == -1){
            System.exit(1);
        }


        //This checks for -print
        shouldPrint = shouldIPrint(args);

        String theOutsideFileName = grabFileName(args);
        String prettyFile = grabPrettyName(args);

        //The total argument length is already checked as is -README. In this case the program has not errored out
        // and the count is within range. So if shouldPrint has not been selected and there is no file name then
        //there is something weird in the arguments.
        int check = 9;
        if(shouldPrint) { check++; }
        if(prettyFile != null) { check = check + 2; }
        if(theOutsideFileName != null) {check = check + 2; }
        if(check != count) {
            System.out.println("Unknown command line argument(s).");
            throw new IllegalArgumentException();
        }

        PhoneBill bill = new PhoneBill();

        if(theOutsideFileName != null) {
            parseDataFile(theOutsideFileName, bill);
        }
        //This creates the call based on the arguments and the count
        PhoneCall call = createCall(args, count);

        //If -print was selected, this prints
        if(shouldPrint) {
            System.out.println(call.toString());
        }
        //This may be redundant if it is loading from an external file.
        //When I think about it more, this will be trouble in the future. ***
        bill.CustomersName = call.theCustomer;
        bill.phoneCallList.add(call);

        if(theOutsideFileName != null) {
            writeOutToTextFile(theOutsideFileName, bill);
        }

        if(prettyFile != null) {
            writeOutPretty(prettyFile, bill);
        }
    }

    /**
     * This method populates the PhoneCall argument that it is given. It takes the command line arguments, a blank
     * Phonecall, and the count from the checkArgsLength which does some checking and returns the count of arguments.
     * This count is modulo 9 to get where we start populating the call.
     * My Reasoning:
     * if there are 9, then it is just the arguments, and if they are incorrect the input checks will error them out
     * If there are 10-14 then move down appropriately with modulo
     * If there are 15, it would include -README so it shouldn't get here.
     * If there was a random option, it should be caught before this is called.
     * @param args The command line arguments
     * @param count The count taken from checkArgsLength which is modulo 7'd to get the beginning of call info
     */
    public static PhoneCall createCall(String[] args, int count) {
        PhoneCall call = new PhoneCall();
        int i = count%9;
        call.theCustomer = args[i];
        call.theCustomersNumber = phoneNumberCheck(args[++i]);
        call.isCallingThisNumber = phoneNumberCheck(args[++i]);
        call.thisIsTheStartDate = dateChecker(args[++i]);
        call.thisIsTheStartTime = timeChecker(args[++i]);
        call.startTimeAMPM = checkAMPM(args[++i]);
        call.thisIsTheEndDate = dateChecker(args[++i]);
        call.thisIsTheEndTime = timeChecker(args[++i]);
        call.endTimeAMPM = checkAMPM(args[++i]);
        checkTimeOrder(call.thisIsTheStartTime, call.startTimeAMPM, call.thisIsTheEndTime, call.endTimeAMPM);
        call.startTimeAndDate = call.fakeDateString(call.thisIsTheStartDate, call.thisIsTheStartTime,
                call.startTimeAMPM);
        call.endTimeAndDate = call.fakeDateString(call.thisIsTheEndDate, call.thisIsTheEndTime,
                call.endTimeAMPM);
        return call;
    }

    /**
     * This checks the start and end times to ensure that start comes before end
     * @param startTime the time the call starts
     * @param startAMPM the am/pm associated with the start time
     * @param endTime the time the call ends
     * @param endAMPM the am/pm associated with the end time
     */
    public static void checkTimeOrder(String startTime, String startAMPM, String endTime, String endAMPM) {
        String[] checkStart = startTime.split(":");
        String[] checkEnd = endTime.split(":");
        //case where the start hour is after the end hour with the same am/pm
        if(Integer.parseInt(checkStart[0]) > Integer.parseInt(checkEnd[0]) && (startAMPM.equals(endAMPM))) {
            System.err.println("Calls cannot start after they end.");
            throw new IllegalArgumentException();
        }
        //case where the end minute is before the start minute with the same or less hour
        if((Integer.parseInt(checkStart[1]) > Integer.parseInt(checkEnd[1])) && Integer.parseInt(checkStart[0]) >= Integer.parseInt(checkEnd[0])) {
            System.err.println("Calls cannot start after they end");
            throw new IllegalArgumentException();
        }
    }

    /**
     * This checks to see if -print option was selected. If so it changes the shouldPrint boolean to true.
     * @param args the command line arguments
     * @return Returns the shouldPrint boolean
     */
    public static boolean shouldIPrint(String[] args) {
        for (int i = 0; i < 4; i++) {
            if (args[i].contains("-print")) {
                return true;
            }
        }
        return false;
    }

    /**
     * This parses a data file that is in the correct format for the data to create a PhoneCall. It will add said phone
     * call to the PhoneBill argument. This should only be doing stuff if it passes a prior check to ensure that the
     * file is not null.
     * @param fileName the name (and/or path) of the file to be parsed.
     * @param bill This is an empty bill that is populated using the TextParser and is then returned.
     */
    public static PhoneBill parseDataFile(String fileName, PhoneBill bill) {
        try {
            TextParser parser = new TextParser();
            File readMyData = new File(fileName);
            Scanner scanner = new Scanner(readMyData);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                parser.parse(data, bill);
            }
            scanner.close();
            return bill;
        } catch (FileNotFoundException | ParserException e) {
            System.err.println("There was an error in Parsing");
        }
        return bill;
    }

    /**
     * This writes out to a text file using the TextDumper dump(PhoneBill, String filename) method.
     * I don't really test this particular method because it doesn't do anything by itself, and
     * the methods it calls are tested by themselves.
     * @param fileName The file where the information will be written to. If not a path it will be local.
     * @param bill The bill that is to be written to the text file.
     * @throws IOException When it can't find the file or fails to write in some way.
     */
    public static void writeOutToTextFile(String fileName, PhoneBill bill) throws IOException {
        TextDumper dumper = new TextDumper();
        dumper.dump(bill, fileName);
    }

    /**
     * This writes out a pretty form of the data to a file named prettyFile.
     * @param prettyFile The file that gets written out to.
     * @param bill the bill that gets written out
     * @throws IOException It sure does!
     */
    public static void writeOutPretty(String prettyFile, PhoneBill bill) throws IOException {
        TextDumper dumper = new TextDumper();
        dumper.prettyDump(bill, prettyFile);
    }

    /**
     * This method takes the name/path of a file from the command line arguments.
     * @param args  These are the command line arguments.
     * @return  This is a string that is the file name or file path. Local if not a path.
     * Returns null if no file name present.
     */
    public static String grabFileName(String[] args) {
        String thisIsTheFileName;
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("-textFile")) {
                thisIsTheFileName = args[++i];
                return thisIsTheFileName;
            }
        }
        return null;
    }

    /**
     * This checks the arguments for a -pretty flag and grab the argument after as the file name to write out to
     * @param args the command line arguments
     * @return returns the file name or null
     */
    public static String grabPrettyName(String[] args) {
        String thisIsTheFileName;
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("-pretty")) {
                thisIsTheFileName = args[++i];
                return thisIsTheFileName;
            }
        }
        return null;
    }

    /**
     * This checks the length of the command line arguments. It returns -1 if outside the range of [9-14]
     * If it is within [9-14] then it returns the argument length.
     * @param args the command line arguments
     * @return returns 9,10,11,12,13, 14 or -1
     */
    public static int checkArgsLength(String[] args) {

        int count = args.length;

        if (count < 9) {
            System.err.println("Missing command line arguments");
            return -1;
        }
        if (count > 14) {
            System.err.println("Too many command line arguments");
            return -1;
        }
        return  count;
    }

    /**
     * This just checks for the README flag.
     * If it is there it prints the README and exits the program.
     * @param args The command line arguments
     */
    public static void checkForReadme(String[] args) {
        for (String arg : args) {
            if (arg.contains("-README")) {
                printReadMe();
                System.exit(0);
            }
        }
    }

    /**
     * This checks to ensure the phone number is in the correct format using regex. If it fails it throws
     * an IllegalArgumentException. Phone numbers are hard, so it really returns any phone number.
     * @param aPhoneNumber a sting that is a phone number in the format ###-###-####
     * @return returns the phone number if it matches the regex
     */
    public static String phoneNumberCheck(String aPhoneNumber) {
        if (!aPhoneNumber.matches("\\d{3}-\\d{3}-\\d{4}")) {
            System.err.println("\nThe phone number is not in the correct format.\nPlease use ###-###-####\n");
            throw new IllegalArgumentException();
        }
        return aPhoneNumber;
    }

    /**
     * This checks the date to ensure it matches the format of #/#/#### or ##/#/#### or #/##/#### or ##/##/####
     * @param date this is a string of the date
     * @return this returns the date on success.
     */
    public static String dateChecker(String date) {
        if(!date.matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$")) {
            System.err.println("\nThe date is not valid.\nPlease use the form mm/dd/yyyy, m/dd/yyyy, mm/d/yyyy, or m/d/yyyy\n");
            throw new IllegalArgumentException();
        }
        return date;
    }

    /**
     * This just checks to ensure that AM or PM has been selected.
     * @param AMPM The AM or PM field of a call
     * @return Returns the string in lower case if it is correct
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
     * This takes a time String and checks the format.
     * @param time This is the time string that was extracted based on placement.
     * @return  This returns the time as long as it is in the correct format.
     */
    public static String timeChecker(String time) {
        if (!time.matches("(1[012]|[1-9]):[0-5][0-9]")) {
            System.err.println("\nThe time is not valid.\nPlease use the form hh:mm or h:mm in 12-hour format\n");
            throw new IllegalArgumentException();
        }
        return time;
    }

    /**
     * This prints the README file if that option is selected
     */
    public static void printReadMe() {
        try (
                InputStream readme = Project3.class.getResourceAsStream("README.txt")
        ) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("README file not found :(");
        }
    }
}

