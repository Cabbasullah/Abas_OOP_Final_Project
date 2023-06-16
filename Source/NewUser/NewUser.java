package NewUser;


public class NewUser{
    private String username;
    private Object password;

    public NewUser(String UserName, Object Password){
        this.username=UserName;
        this.password=Password;
    }

    
    public String getUsername(){
        return username;
    }

    public Object getPassword(){
        return password;
    }

    public void setUsername(String newusername){
          this.username=newusername;
    }

    public void setPassword(Object newpassword){
        this.password=newpassword;
    }
}