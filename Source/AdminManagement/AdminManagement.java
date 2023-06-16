package AdminManagement;
import java.util.*;
import java.io.*;


public class AdminManagement{
    Scanner scn=new Scanner(System.in);
    public void deleteUsers(){
           try {
            BufferedReader breader = new BufferedReader(new FileReader("Newfile.txt"));
            System.out.println("Number of user to remove: ");
            int rmvuser=scn.nextInt();
            scn.nextLine();
            for(int i=0;i<rmvuser;i++){
                System.out.print("Remove Name# "+(i+1)+": ");
            String name = scn.nextLine().toLowerCase();
            
            String line;
            boolean removed=false;
            File temfile=new File("temp.txt");
            PrintWriter pWriter=new PrintWriter(temfile);
            System.out.println("\nSearch Result for Employee " + name);
            while ((line = breader.readLine()) != null) {
                if (line.toLowerCase().contains(name)) {
                    //line.replace(line, "");
                    System.out.println("Sucessfully Removed");
                    removed=true;
                }else{
                    pWriter.println(line);
                }

            }
            pWriter.close();
            breader.close();
            if(removed){
                File originafile=new File("Newfile.txt");
                if(originafile.delete()){
                    temfile.renameTo(originafile);
                }else{
                    System.out.println("Error deleting original file");
                }
            }else{
                System.out.println("Employee Not found: ");
            }

        } 
        } catch (IOException e) {
            e.printStackTrace();

    }
            
    }
    public void viewUserslist(){
        try{
            BufferedReader bReader=new BufferedReader(new FileReader("Newfile.txt"));
            String line=bReader.readLine();
            int count=0;
            System.out.println("Current Users of this Reservation System");
            while(line!=null){
           
            System.out.println(line+"\n");
            count++;
            line=bReader.readLine();
            }
           bReader.close();
            System.out.println("Total number of users: "+count);
        
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}