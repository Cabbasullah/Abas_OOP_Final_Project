import ProcessAccount.ProcessAccount;

import AdminManagement.AdminManagement;
import ReservationsManagement.ReservationsManagement;

import Login.Login;
import java.util.*;

class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String BG = "\u001B[47m";
    public static final String BCyan = "\u001B[46m";
    public static final String Cyan = "\u001B[36m";
    public static final String Black = "\u001B[30m";
    public static final String Green = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String Blue = "\u001B[32m";
    public static final String Red = "\u001B[31m";

    public static void main(String[] args) {
        ReservationsManagement rm = new ReservationsManagement();

        Scanner scn = new Scanner(System.in);
        ProcessAccount pa = new ProcessAccount();

        char Option;
        System.out.println(Black
                + "\n\n\t\t\t###########################\n\n\t\t\tUse as a: adminstrator b: user \n\n\t\t\t###########################"
                + ANSI_RESET);
        System.out.print(Blue + "\t\t\t\tGet Started: " + ANSI_RESET);
        char adminuser = scn.next().charAt(0);
        if (adminuser == 'b') {
            do {
                System.out.println(
                        Black + "\n\t\t\t====================\n\t\t\tUser Account Management Setting\n\t\t\ta: create new account\n\t\t\tb: login\n\t\t\t====================\n"
                                + ANSI_RESET);
                System.out.print(Blue + "\t\t\tJoin: " + ANSI_RESET);
                Option = scn.next().charAt(0);
                switch (Option) {
                    case 'a':
                        pa.createAccount();
                        break;
                    case 'b':
                        Login lg = new Login();
                        lg.Signin("", "");

                        break;

                    case 'T':
                        System.exit(0);
                        break;
                }

            } while (Option != 'T');

        }
        if (adminuser == 'a') {
            AdminManagement am = new AdminManagement();

            char aminO;
            do {
                System.out.println(
                        BLUE +
                                "\n\n\t\t\t\tManagement System()" + ANSI_RESET);
                System.out.println(Black
                        + "\t\t\t\t====================\n\t\t\t\ta: Add Events\n\t\t\t\tb: update events \n\t\t\t\tc: delete Events \n\t\t\t\t d: View Users List \n\t\t\t\t e: exit\n\t\t\t\t====================\n"
                        + ANSI_RESET);
                System.out.print("\t\t\t\tAccount: ");
                aminO = scn.next().charAt(0);
                switch (aminO) {
                    case 'd':
                        am.deleteUsers();
                        break;
                    case 'c':
                        am.viewUserslist();
                        break;
                    case 'e':
                        System.exit(0);
                        break;

                    case 'b':
                        rm.updateEvent();
                        break;

                    case 'a':
                        rm.addEvents();
                        break;

                }
            } while (aminO != 'e');

        }

        scn.close();
    }
}