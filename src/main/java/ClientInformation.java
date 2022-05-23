import com.jme3.network.serializing.Serializable;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Serializable
public class ClientInformation {
    private int id;
    private String UserName;
    List<String> carteInMano, carteViste;
    HashMap<String, Boolean> note;
    int posX, posY;
    int numeroMosse;
    //ServerMain server;

    public ClientInformation(){}

    public ClientInformation(String UserName){
        this.UserName = UserName;
    }

    public ClientInformation(int id, String UserName){
        this.id = id;
        this.UserName = UserName;
    }

    public void setId(int id){this.id = id;}
    public void setUserName(String usr){UserName = usr;}

    public int getId(){return id;}
    public String getUsername(){return UserName;}
}
