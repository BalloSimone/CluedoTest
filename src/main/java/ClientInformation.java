public class ClientInformation {
    private int id;
    private String UserName;

    public ClientInformation(){}

    public ClientInformation(int id, String UserName){
        this.id = id;
        this.UserName = UserName;
    }

    public void setId(int id){this.id = id;}
    public void setUserName(String usr){UserName = usr;}

    public int getId(){return id;}
    public String getUsername(){return UserName;}
}
