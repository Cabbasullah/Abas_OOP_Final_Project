package ReservationsManagement;

import java.util.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;

import java.io.*;

class eventmanagement {
    Scanner scn = new Scanner(System.in);
    private String evname;
    private Date date;
    private LocalTime time;
    private Object venuename;
    private double price;
    private int VIPseats;
    private int generalseats;

    public eventmanagement(String evname, Date date, LocalTime time, Object venuename, double price,
            int VIPseats, int generalseats) {
        this.evname = evname;
        this.date = date;
        this.time = time;
        this.venuename = venuename;
        this.price = price;
        this.VIPseats = VIPseats;
        this.generalseats = generalseats;

    }

    public String getEvent() {
        return evname;
    }

    public Date getdate() {
        return date;

    }

    public LocalTime getTime() {
        return time;
    }

    public Object getvenName() {
        return venuename;
    }

    public int getVipSeats() {
        return VIPseats;
    }

    public int getGeneralseats() {
        return generalseats;
    }

    public double getPrice() {
        return price;
    }
    // Parse the user input into LocalTime object

}

public class ReservationsManagement {

    LinkedList<eventmanagement> reservelist = new LinkedList<>();
    HashMap<Object, LinkedList<eventmanagement>> hashlist = new HashMap<>();

    Scanner scn = new Scanner(System.in);

    public void addEvents() {
        System.out.println("Number of events to add: ");
        int n = scn.nextInt();
        scn.nextLine();// consume left over new line character

        for (int i = 0; i < n; i++) {
            System.out.println("Event name# " + (i + 1) + ": ");
            String evname = scn.nextLine();

            System.out.println("Enter the date(MM/dd/yyyy): ");
            String dateString = scn.nextLine();
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            Date date = null;
            try {
                date = format.parse(dateString);
            } catch (Exception e) {
                System.out.println("Invalid date format");
                return;
            }

            System.out.print("Enter the time (HH:mm:ss): ");
            String userInput = scn.nextLine();

            // Parse the user input into LocalTime object
            LocalTime time = LocalTime.parse(userInput, DateTimeFormatter.ofPattern("HH:mm:ss"));

            System.out.println("Venue Name: ");
            Object venuename = scn.nextLine();

            System.out.print("Price Tag: ");
            Double price = scn.nextDouble();
            scn.nextLine();

            System.out.print("Number of Vipseats: ");
            int Vipseats = scn.nextInt();
            scn.nextLine();

            System.out.print("Number of generalseats: ");
            int generalseats = scn.nextInt();
            scn.nextLine();

            eventmanagement rs = new eventmanagement(evname, date, time, venuename, price, Vipseats,
                    generalseats);
            reservelist.add(rs);

        }
        for (

        eventmanagement newseats : reservelist) {
            LinkedList<eventmanagement> seatslist = hashlist.get(newseats.getEvent());
            if (seatslist == null) {
                seatslist = new LinkedList<>();
                hashlist.put(newseats.getEvent(), seatslist);
            }
            seatslist.add(newseats);
        }

        for (Object eventname : hashlist.keySet()) {
            LinkedList<eventmanagement> lisseats = hashlist.get(eventname);
            try {
                BufferedWriter bwriter = new BufferedWriter(new FileWriter("Events.txt", true));
                bwriter.write(eventname + ": ");
                bwriter.newLine();
                for (eventmanagement reserveevents : lisseats) {
                    bwriter.write(" " + " Date: " + reserveevents.getdate() + " Time: " + reserveevents.getTime() +
                            " Venue: " + reserveevents.getvenName() + " Number of Vip tickets: "
                            + reserveevents.getVipSeats() + " Number of general tickets: "
                            + reserveevents.getGeneralseats() + " price: " + reserveevents.getPrice() +
                            "\n");

                    bwriter.newLine();

                }

                bwriter.close();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        System.out.println("Total Number of Tickets: ");

        try {
            BufferedWriter bwriter = new BufferedWriter(new FileWriter("totaltickets.txt"));

            for (Object eventname : hashlist.keySet()) {
                LinkedList<eventmanagement> lisseats = hashlist.get(eventname);
                bwriter.write(eventname + ":");
                bwriter.newLine();
                for (eventmanagement reserveevents : lisseats) {
                    bwriter.write("VIP Tickets: " + reserveevents.getVipSeats());
                    bwriter.newLine();
                    bwriter.write("General Tickets: " + reserveevents.getGeneralseats());
                    bwriter.newLine();
                }
                bwriter.newLine();
            }

            bwriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateEvent() {
        try {
            System.out.println("Name To Update: ");
            String name = scn.nextLine().toLowerCase();
            File inputFile = new File("Events.txt");
            File tempFile = new File("Somfie_temp.txt");
            BufferedReader breader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bwriter = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean nameFound = false;
            while ((line = breader.readLine()) != null) {
                if (line.toLowerCase().contains(name)) {
                    System.out.println("new Event: ");
                    String evename = scn.nextLine();
                    bwriter.write(evename + ":");
                    bwriter.newLine();

                    System.out.println("Enter the date(MM/dd/yyyy): ");
                    String dateString = scn.nextLine();
                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                    Date date = null;
                    try {
                        date = format.parse(dateString);
                    } catch (Exception e) {
                        System.out.println("Invalid date format");
                        return;
                    }
                    System.out.print("Enter the time (HH:mm:ss): ");
                    String userInput = scn.nextLine();

                    // Parse the user input into LocalTime object
                    LocalTime time = LocalTime.parse(userInput, DateTimeFormatter.ofPattern("HH:mm:ss"));

                    System.out.println("new venue name: ");
                    Object venue = scn.nextLine();
                    System.out.print("Price Tag: ");
                    Double price = scn.nextDouble();
                    scn.nextLine();

                    System.out.print("Number of Vipseats: ");
                    int Vipseats = scn.nextInt();
                    scn.nextLine();

                    System.out.print("Number of generalseats: ");
                    int generalseats = scn.nextInt();
                    scn.nextLine();

                    bwriter.write(" " + " Date: " + date + " Time: " + time +
                            " Venue: " + venue + " Number of Vip tickets: "
                            + Vipseats + " Number of general tickets: "
                            + generalseats + "Price Tag: " + price +
                            "\n");

                    nameFound = true;
                } else {
                    bwriter.write(line + "\n");
                }

            }
            breader.close();
            bwriter.close();

            if (!nameFound) {
                System.out.println("event name not found");
                tempFile.delete();
            } else {
                inputFile.delete();
                tempFile.renameTo(inputFile);
                System.out.println("File updated successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteEvent() {
        try {
            BufferedReader breader = new BufferedReader(new FileReader("Events.txt"));
            System.out.println("Number of Events to delete: ");
            int rmvuser = scn.nextInt();
            scn.nextLine();
            for (int i = 0; i < rmvuser; i++) {
                System.out.print("Remove Event# " + (i + 1) + ": ");
                String name = scn.nextLine().toLowerCase();

                String line;
                boolean removed = false;
                File temfile = new File("titemp.txt");
                PrintWriter pWriter = new PrintWriter(temfile);
                System.out.println("\nSearch Result for Employee " + name);
                while ((line = breader.readLine()) != null) {
                    if (line.toLowerCase().contains(name)) {

                        System.out.println("Sucessfully Removed");
                        removed = true;
                        breader.readLine();
                        breader.readLine();
                        breader.readLine();
                        breader.readLine();
                    } else {
                        pWriter.println(line);
                    }

                }
                pWriter.close();
                breader.close();
                if (removed) {
                    File originafile = new File("Events.txt");
                    if (originafile.delete()) {
                        temfile.renameTo(originafile);
                    } else {
                        System.out.println("Error deleting original file");
                    }
                } else {
                    System.out.println("Employee Not found: ");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
