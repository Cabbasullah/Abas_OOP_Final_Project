package Dashboard;

import java.util.*;

import Reservations.Reservations;

import java.io.*;

public class Dashboard {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String BG = "\u001B[47m";
    public static final String BCyan = "\u001B[46m";
    public static final String Cyan = "\u001B[36m";
    public static final String Black = "\u001B[30m";
    public static final String Green = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String Blue = "\u001B[32m";
    public static final String Red = "\u001B[31m";
    Scanner scn = new Scanner(System.in);
    ArrayList<String> Viplist = new ArrayList<>();
    ArrayList<String> generalist = new ArrayList<>();

    public void seatRetreive() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("totaltickets.txt"));
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("VIP Seats:")) {
                    generalist.add(line);
                } else if (line.startsWith("General Seats:")) {
                    generalist.add(line);
                }
                line = reader.readLine(); // Advance to the next line
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void seatTypes() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("seatsperevent.txt"));
            String line = reader.readLine();
            while (line != null) {
                System.out.println("\t\t\t" + line + "\n");
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*
     * public void printVipcount() {
     * System.out.println("\n\t\tReserved VIP Seats:");
     * for (String printlist : arrlist) {
     * System.out.println("\t\t" + printlist);
     * }
     * }
     * 
     * public void printGencount() {
     * System.out.println("\n\t\tReserved General Seats:");
     * for (String printlist : garrlist) {
     * System.out.println("\t\t" + printlist);
     * }
     * }
     */

    public void dashboard() {
        seatRetreive();
        Reservations rs = new Reservations();
        rs.trackTickets();
        System.out.println("\t\t\t\t\tSeat Types\n");
        seatTypes();

        System.out.println(
                Cyan + "\n\t\tThe Following Events Are Available on Our Platform\n" + ANSI_RESET);
        System.out.println(
                "\n" + BG + "\t|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
                        + ANSI_RESET);
        ArrayList<String> dataArrayList = new ArrayList<>();
        try (BufferedReader records = new BufferedReader(new FileReader("Events.txt"))) {
            String line1 = records.readLine();

            while (line1 != null) {
                dataArrayList.add(line1);
                line1 = records.readLine();
            }

            for (String eventlist : dataArrayList) {
                System.out.println("\t" + eventlist + "\t");
            }
            System.out.println(
                    "\n" + BG + "\t|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
                            + ANSI_RESET);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
