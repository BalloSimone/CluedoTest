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
    Point pos;
    int numeroMosse;
    String turno;
    String[][] mappa;
    //ServerMain server;

    public ClientInformation(){}

    public ClientInformation(int id, String UserName){
        this.id = id;
        this.UserName = UserName;
        pos = new Point();
        note = new HashMap<String, Boolean>();
        carteInMano = new LinkedList<>();
        carteViste = new LinkedList<>();
        numeroMosse = 0;
        //server=new ServerMain();


    }

    public void setId(int id){this.id = id;}
    public void setUserName(String usr){UserName = usr;}

    public int getId(){return id;}
    public String getUsername(){return UserName;}
}
