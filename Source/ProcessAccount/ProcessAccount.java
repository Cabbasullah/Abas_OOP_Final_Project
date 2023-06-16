package ProcessAccount;

import java.util.*;
import java.io.*;
import NewUser.NewUser;;

public class ProcessAccount {

    Scanner scn = new Scanner(System.in);

    public void createAccount() {
        System.out.print("Enter username: ");
        String username = scn.nextLine();
        System.out.print("Password: ");
        Object password = scn.next();
        NewUser newuser = new NewUser(username, password);
        /* luser.add(newuser); */
        try {
            BufferedWriter bwriter = new BufferedWriter(new FileWriter("Newfile.txt", true));
            bwriter.write(String.format("Name: %-8s Password: %-8s\n", newuser.getUsername(), newuser.getPassword()));
            System.out.println("Successfully Created an Account");
            bwriter.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

    }

    public LinkedList<NewUser> loDetails(){
        LinkedList<NewUser> prlist=new LinkedList<>();
        try{
            BufferedReader breader=new BufferedReader(new FileReader("Newfile.txt"));
            String line=breader.readLine();
            while(line!=null){
                String[] list=line.split("\\s+");
                while(list.length>=4){
                    String name=list[1];
                    Object pass=list[3];
                    NewUser ud=new NewUser(name, pass);
                    
                    prlist.add(ud);

                }
            }
            breader.close();
        
        }catch(IOException e){
            System.out.println("Error: "+e);
        }
        return prlist;
    }

    public void Update() {
        try {
            System.out.println("Name To Update: ");
            String name = scn.nextLine().toLowerCase();

            File inputFile = new File("Newfile.txt");
            File tempFile = new File("Somfie_temp.txt");
            BufferedReader breader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bwriter = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean nameFound = false;
            while ((line = breader.readLine()) != null) {
                if (line.toLowerCase().contains(name)) {
                    System.out.println("new Name: ");
                    String Nname = scn.nextLine();

                    System.out.println("new Password: ");
                   Object Password=scn.next();


                    bwriter.write(
                            String.format("Name: %-8s Password: %-8s \n", Nname,Password));
                    nameFound = true;
                } else {
                    bwriter.write(line + "\n");
                }
            }
            breader.close();
            bwriter.close();

            if (!nameFound) {
                System.out.println("Name not found");
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
}