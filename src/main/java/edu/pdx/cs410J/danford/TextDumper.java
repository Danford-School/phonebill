package edu.pdx.cs410J.danford;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * This is the TextDumper class. It Writes out a phone bill to an external file.
 */
public class TextDumper implements PhoneBillDumper {

    /**
     * This is the dump that is not used.
     * @param abstractPhoneBill This is not used. Dont use this format. Don't.
     */
    @Override
    public void dump(AbstractPhoneBill abstractPhoneBill)  {
        System.out.println("The file to be written to is a required argument");
        throw new IllegalArgumentException();
    }

    /**
     * This is the real dump method. It takes an abstract phone bill that is casted to a proper PhoneBill.
     * It then takes the phone bill and writes it to the file using a PhoneCall method.
     * @param abstractPhoneBill This is casted to a PhoneBill and the info is extracted.
     * @param file  This is the file where the info from the bill will be written. Will be created if needed, can be path
     * @throws IOException Sometimes, sure.
     */
    public void dump(AbstractPhoneBill abstractPhoneBill, String file) throws IOException {
        try {
            FileWriter writer = new FileWriter(file);

            PhoneBill bill = (PhoneBill)abstractPhoneBill;
            Iterator<PhoneCall> iterate = bill.phoneCallList.iterator();
            PhoneCall temp;
            while(iterate.hasNext()) {
                temp = iterate.next();
                writer.write(temp.phoneCallToDataFile());
                writer.write("\n");
            }
            writer.close();
            System.out.println("Phone bill written to " + file);
        } catch (IOException e) {
            System.out.println("Failed to write phone bill to " + file);
        }
    }

    /**
     * This dumps to a file in the 'pretty' (as in readable by humans) format.
     * @param abstractPhoneBill The phoneBill to be written out
     * @param file  The file to write to
     * @throws IOException It does.
     */
    public void prettyDump(AbstractPhoneBill abstractPhoneBill, String file) throws IOException {
        try {
            FileWriter writer = new FileWriter(file);

            PhoneBill bill = (PhoneBill)abstractPhoneBill;
            Iterator<PhoneCall> iterate = bill.phoneCallList.iterator();
            PhoneCall temp;
            while(iterate.hasNext()) {
                temp = iterate.next();
                writer.write(temp.phoneCallToOutput());
                writer.write("\n");
            }
            writer.close();
            System.out.println("Phone bill written to " + file);
        } catch (IOException e) {
            System.out.println("Failed to write phone bill to " + file);
            throw new IOException();
        }
    }
}
