import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Logica {

    //informazioni relative al giocatore
    List<String> carteInMano, carteViste;
    HashMap<String, Boolean> note;

    //informazioni generiche di gioco da inserire nella classe gameApplication
    //String[][] mappa;
    //String turno, faseTurno;//


    public Logica()
    {
        //inizializzazione variabili del giocatore
        note = new HashMap<String, Boolean>();
        carteInMano = new LinkedList<>();
        carteViste = new LinkedList<>();
    }

    //funzioni utili per il setup del gioco
    public void ottieniNote(List<String> carte)
    {
        for(String s : carte)
        {
            note.put(s, false);
        }
    }

    public void ottieniMano(List<String> carteInMano)
    {
        this.carteInMano = carteInMano;
    }
    /////////////////////////////////////////////////////////////////////////////

    //funzioni utili durante il gioco
    public void cartaVista(String carta)
    {
        carteViste.add(carta);
    }

    public void modificaNota(String carta)
    {
        boolean valoreAttuale = note.get(carta);
        if(valoreAttuale) note.replace(carta, false);
        else note.replace(carta, true);
    }

    public void movimento(int x, int y, ClientInformation c, String turno, String faseTurno, String mappa[][]) {
        if (turno == c.getUsername() && c.numeroMosse > 0 && faseTurno == "movimento") {
            if (x < 8 && x > -1 && y < 13 && y > -1) {
                if ((x - c.pos.x == 1 && y - c.pos.y == 0) ||
                        (x - c.pos.x == -1 && y - c.pos.y == 0) ||
                        (x - c.pos.x == 0 && y - c.pos.y == 1) ||
                        (x - c.pos.x == 0 && y - c.pos.y == -1)) {
                    if (mappa[x][y] != "w") {
                        //Server.effettuaMovimento(x, y);
                    }
                }
            }
            //a = Server.mappaTemporanea;
        }
    }

    public void predizione(String persona, String arma, String luogo, ClientInformation c, String turno, String faseTurno, String mappa[][])
    {
        if(turno == c.getUsername() && faseTurno == "movimento" && !(mappa[c.pos.x][c.pos.y].contains("e")) && luogo==mappa[c.pos.x][c.pos.y])
        {
            //Server.effettuaPredizione(persona, arma, luogo);
        }
    }

    public void lanciaDadi(String turno, ClientInformation c)
    {
        if(turno == c.getUsername())
        {
            //Server.numeroMosse = (int)(Math.random()*11+2);
        }
    }
}
