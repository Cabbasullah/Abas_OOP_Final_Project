package Login;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import ProcessAccount.ProcessAccount;
import Reservations.Reservations;
import Dashboard.Dashboard;

import java.util.*;

public class Login {

    Scanner scn = new Scanner(System.in);
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String BG = "\u001B[47m";
    public static final String BCyan = "\u001B[46m";
    public static final String Cyan = "\u001B[36m";
    public static final String Black = "\u001B[30m";
    public static final String Green = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String Blue = "\u001B[32m";
    public static final String Red = "\u001B[31m";

    public void Signin(String loginName, Object password) {
        // Check for null values
        if (loginName == null || password == null) {
            System.out.println("Invalid login name or password");
            return;
        }

        String username = "Login Name: ";
        int screenWidth = 80;
        int leftPadding = (screenWidth - username.length()) / 5;

        System.out.print(String.format("%" + leftPadding + "s%s", "", username));
        String login = scn.nextLine();

        String passwordText = "Password: ";
        System.out.print(String.format("%" + leftPadding + "s%s", "", passwordText));
        scn.nextLine();

        String passwordString = ((String) password).trim();
        boolean found = false;

        try (BufferedReader bReader = new BufferedReader(new FileReader("Newfile.txt"))) {
            String line = bReader.readLine();

            while (line != null) {
                if (line.contains(login) && line.contains(passwordString)) {
                    String name = BLUE + "\t\t\t\t\nHi, " + login + ANSI_RESET;
                    System.out.println(
                            "\n" + BG
                                    + "\t\t\t========================================================================="
                                    + ANSI_RESET);
                    int ndashboardWidth = 80;
                    int ndashboardLeftPadding = (ndashboardWidth - name.length());
                    System.out.printf("%n%" + ndashboardLeftPadding + "s%s%n%n", "", name);

                    String welcomeText = Blue + "\n\t\t\tWelcome to your dashboard:"
                            + ANSI_RESET;
                    int dashboardWidth = 80;
                    int dashboardLeftPadding = (dashboardWidth - welcomeText.length()) / 2;

                    System.out.printf("%n%" + dashboardLeftPadding + "s%s%n%n", "", welcomeText);
                    found = true;
                    Dashboard dboard = new Dashboard();
                    dboard.dashboard();

                    System.out.println(Blue
                            + "\n=================\n\nAccount Setting\n\n=================" + ANSI_RESET);
                    System.out.println(
                            Black + "\na: Change username \nb: add reservations\nd: update reservations \ne: Cancel Reservations f. Payment\n"
                                    + ANSI_RESET);
                    System.out.print("\nSelect the Choices above: \n");
                    char Option = scn.next().charAt(0);
                    switch (Option) {
                        case 'a':
                            ProcessAccount pa = new ProcessAccount();
                            pa.Update();
                            break;
                        case 'b':
                            Reservations rs = new Reservations();
                            rs.reserveEvent();
                            break;

                        case 'f':
                            Reservations re = new Reservations();
                            re.searchByCategory("");
                            break;

                        case 'e':
                            Reservations ju = new Reservations();
                            ju.deleteEvent();
                            break;
                    }

                }
                line = bReader.readLine();

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        if (!found) {
            System.out.println("Invalid login name or password");
        }
    }
}
