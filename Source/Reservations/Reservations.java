package Reservations;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

abstract class Event {
    private String eventname;
    private String seattype;

    public Event(String eventname, String seattype) {
        this.eventname = eventname;
        this.seattype = seattype;
    }

    public String getEvent() {
        return eventname;
    }

    public String getSeat() {
        return seattype;
    }
}

class Reserve extends Event {
    private String urname;
    private String email;

    public Reserve(String eventname, String seattype, String urname, String email) {
        super(eventname, seattype);
        this.urname = urname;
        this.email = email;
    }

    public String getUrname() {
        return urname;
    }

    public String getEmail() {
        return email;
    }
}

class ReservedEvent extends Event {
    private String location;

    public ReservedEvent(String eventname, String seattype, String location) {
        super(eventname, seattype);
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}

class SeatCounts {
    private int vipCount;
    private int generalCount;

    public SeatCounts() {
        this.vipCount = 0;
        this.generalCount = 0;
    }

    public SeatCounts(int vipCount, int generalCount) {
        this.vipCount = vipCount;
        this.generalCount = generalCount;
    }

    public int getVipCount() {
        return vipCount;
    }

    public int getGeneralCount() {
        return generalCount;
    }
}

public class Reservations {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String BG = "\u001B[47m";
    public static final String BCyan = "\u001B[46m";
    public static final String Cyan = "\u001B[36m";
    public static final String Black = "\u001B[30m";
    public static final String Green = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String Blue = "\u001B[32m";
    public static final String Red = "\u001B[31m";
    private Pattern patternemail = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    Scanner scn = new Scanner(System.in);
    LinkedList<ReservedEvent> reservelist = new LinkedList<>();
    HashMap<String, LinkedList<ReservedEvent>> hashlist = new HashMap<>();

    public void reserveEvent() {
        String eventname = "";
        String seat = "";
        int vipCount = 0;
        int gcount = 0;
        try {
            BufferedReader countReader = new BufferedReader(new FileReader("seat_counts.txt"));
            String line;
            while ((line = countReader.readLine()) != null) {
                if (line.startsWith("VIP Seats:")) {
                    vipCount = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("General Seats:")) {
                    gcount = Integer.parseInt(line.split(":")[1].trim());
                }
            }
            countReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Number of Reservations to make: ");
        int n = scn.nextInt();
        scn.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("Full Name: ");
            String name = scn.nextLine();
            boolean validemail = false;
            String email;
            do {
                System.out.println("Your email: ");
                email = scn.nextLine();
                if (patternemail.matcher(email).matches()) {
                    validemail = true;
                } else {
                    System.out.println("Invalid email format. Please enter a valid format");
                }
            } while (!validemail);
            System.out.println("Event Name: ");
            eventname = scn.nextLine();
            Boolean match = false;
            while (!match) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("Events.txt"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith(eventname)) {
                            match = true;
                            break;
                        }
                    }
                    if (!match) {
                        System.out.println("Event not found. Please enter a valid event name:");
                        eventname = scn.nextLine();
                        continue;
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Seat type(VIP or General): ");
            seat = scn.nextLine();
            String stseat = seat.toString();
            if (stseat.equalsIgnoreCase("VIP")) {
                vipCount++;
            } else if (stseat.equalsIgnoreCase("General")) {
                gcount++;
            }

            System.out.println("Event Location: ");
            String location = scn.nextLine();

            ReservedEvent reservedEvent = new ReservedEvent(eventname, seat, location);
            reservelist.add(reservedEvent);

            for (ReservedEvent newseats : reservelist) {
                LinkedList<ReservedEvent> seatslist = hashlist.get(newseats.getEvent());
                if (seatslist == null) {
                    seatslist = new LinkedList<>();
                    hashlist.put(newseats.getEvent(), seatslist);
                }
                seatslist.add(newseats);
            }
        }
        try {
            HashMap<String, SeatCounts> seatCountsMap = readSeatCountsFromFile();

            for (String eventName : hashlist.keySet()) {
                LinkedList<ReservedEvent> lisSeats = hashlist.get(eventName);

                SeatCounts existingCounts = seatCountsMap.getOrDefault(eventName, new SeatCounts());

                int evpCount = existingCounts.getVipCount();
                int generalCount = existingCounts.getGeneralCount();

                for (ReservedEvent reserveEvent : lisSeats) {

                    if (reserveEvent.getSeat().equalsIgnoreCase("VIP")) {
                        evpCount++;
                    } else if (reserveEvent.getSeat().equalsIgnoreCase("General")) {
                        generalCount++;
                    }
                }

                // Update the seat counts in the seatCountsMap
                seatCountsMap.put(eventName, new SeatCounts(evpCount, generalCount));
            }

            writeSeatCountsToFile(seatCountsMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedWriter countWriter = new BufferedWriter(new FileWriter("seat_counts.txt"));
            countWriter.write("VIP Seats: " + vipCount);
            countWriter.newLine();
            countWriter.write("General Seats: " + gcount);
            countWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HashMap<String, SeatCounts> readSeatCountsFromFile() throws IOException {
        HashMap<String, SeatCounts> seatCountsMap = new HashMap<>();
        File file = new File("seatsperevent.txt");

        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String eventName = line.replace(": ", "");
                int vipCount = Integer.parseInt(reader.readLine().replace("VIP Seats: ", ""));
                int generalCount = Integer.parseInt(reader.readLine().replace("General Seats: ", ""));

                seatCountsMap.put(eventName, new SeatCounts(vipCount, generalCount));
            }

            reader.close();
        }

        return seatCountsMap;
    }

    private void writeSeatCountsToFile(HashMap<String, SeatCounts> seatCountsMap) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("seatsperevent.txt"));
        for (String eventName : seatCountsMap.keySet()) {
            SeatCounts counts = seatCountsMap.get(eventName);
            writer.write(eventName + ": ");
            writer.newLine();
            writer.write("VIP Seats: " + counts.getVipCount());
            writer.newLine();
            writer.write("General Seats: " + counts.getGeneralCount());
            writer.newLine();

        }
        writer.close();

    }

    public void trackTickets() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("totaltickets.txt"));
            String line;
            String currentEvent = null;

            while ((line = reader.readLine()) != null) {
                if (line.endsWith(":")) {
                    if (currentEvent != null) {
                        System.out.println();
                    }
                    currentEvent = line;
                    System.out.print(currentEvent);
                } else if (currentEvent != null) {
                    System.out.println("\t" + line);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* searchByCategory("Yool"); */
    }

    public void searchByCategory(String categoryName) {
        Scanner scn = new Scanner(System.in);
        List<String> foundEvents = new ArrayList<>();
        System.out.println("Search the event name: ");
        categoryName = scn.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader("Events.txt"))) {
            String line;
            StringBuilder eventBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    eventBuilder.append(line).append("\n");
                } else {
                    String event = eventBuilder.toString();
                    if (event.contains(categoryName)) {
                        int categoryStart = event.indexOf(categoryName);
                        int priceStart = event.indexOf("price:", categoryStart) + 7;
                        int priceEnd = event.indexOf("\n", priceStart);
                        String priceString = event.substring(priceStart, priceEnd).trim();
                        double price = Double.parseDouble(priceString);
                        foundEvents.add(categoryName + ": " + price);
                    }
                    eventBuilder = new StringBuilder();
                }
            }

            if (!foundEvents.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("payment.txt"))) {
                    for (String event : foundEvents) {
                        writer.write(event);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader breader = new BufferedReader(new FileReader("totaltickets.txt"));
            String line = breader.readLine();
            while (line != null) {
                System.out.println(line);
                line = breader.readLine();
            }

            breader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteEvent() {
        try {
            BufferedReader breader = new BufferedReader(new FileReader("seat_counts.txt"));
            System.out.println("Number of reservations to remove: ");
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

    // Existing code...
}
