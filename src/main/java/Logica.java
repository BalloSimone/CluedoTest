import com.jme3.network.Client;
import com.jme3.network.HostedConnection;

import java.awt.*;
import java.util.*;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;

public class Logica {

    /* id carte nelle note
    *  da 0 a 6 -> persone
    *  da 6 a 12 -> armi
    *  da 13 a 21 -> luoghi
    * */

    //lancio dei dadi numero tra 2 e 12

    /* fase turno
        0 -> lancio dadi
        1 -> spostamento
        2 -> predizione (eventuale)
        3 -> ipotesi finale (eventuale)
        4 -> non il mio turno
     */

    //mappa
    public static final String[][] mappa = {
            {"w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w"},
            {"w", "w", "w", "r", "w", "w", "w", "r", "w", "w", "w", "w", "w"},
            {"w", "w", "e", "e", "e", "e", "e", "e", "e", "e", "e", "w", "w"},
            {"w", "w", "e", "e", "e", "e", "e", "e", "e", "e", "e", "w", "w"},
            {"w", "w", "e", "e", "e", "v", "e", "e", "e", "e", "e", "w", "w"},
            {"w", "w", "e", "e", "e", "e", "e", "e", "e", "e", "e", "w", "w"},
            {"w", "w", "w", "w", "r", "w", "w", "w", "r", "w", "w", "w", "w"},
            {"w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w"}
    };


    //informazioni relative al giocatore
    private List<String> carteInMano, carteViste;
    private List<Integer> note;
    private boolean isMyTurn;
    private Point posizione;
    private HashMap<ClientInformation, Point> posizioneAltriGiocatori;
    private int faseTurno;
    private Client client;

    //altre variabili
    private int numeroMosse;




    public Logica(Client client)
    {
        isMyTurn = false;
        note = new ArrayList<Integer>();
        carteInMano = new LinkedList<>();
        carteViste = new ArrayList<>();
        posizione = new Point();
        faseTurno = 0;
        this.client = client;
        posizioneAltriGiocatori = new HashMap<>();
    }

    /////////////////////////////////////////////////////////////////////////////

    public HashMap<ClientInformation, Point> getPosizioniAltriGiocatori(){
        return posizioneAltriGiocatori;
    }

    public void initPosizioniAltriGiocatori(HashMap<ClientInformation, Point> posizioni){
        posizioneAltriGiocatori = posizioni;
    }

    public void cambiaPosizioneAltroGiocatore(ClientInformation giocatore, Point newPosition){
        posizioneAltriGiocatori.put(giocatore, newPosition);
    }

    /////////////////////////////////////////////////////////////////////////////

    public void setNote(int index, int value){
        note.set(index, value);
    }

    public List<Integer> getNote(){
        return note;
    }

    public void initNote(){
        for(int i=0; i<21; i++){
            note.set(i,0);
        }
    }


    /////////////////////////////////////////////////////////////////////////////

    public void setCarteInMano(List<String> carteInMano)
    {
        this.carteInMano = carteInMano;
    }

    public List<String> getCarteInMano()
    {
        return carteInMano;
    }

    /////////////////////////////////////////////////////////////////////////////

    public void setMyTurn(boolean turn){
        this.isMyTurn = turn;
    }

    public boolean getMyTurn(){return isMyTurn;}

    /////////////////////////////////////////////////////////////////////////////

    public void setFaseTurno(int phase){
        faseTurno = phase;
    }

    public int getFaseTurno(){
        return faseTurno;
    }

    /////////////////////////////////////////////////////////////////////////////

    public void setCarteViste(List<String> carte){
        carteViste = carte;
    }

    public List<String> getCarteViste(){return carteViste;}

    public void addToCarteViste(String carta){carteViste.add(carta);}

    /////////////////////////////////////////////////////////////////////////////

    public void lanciaDadi()
    {
        numeroMosse = (int)(Math.random() * 10 + 2);
    }

    public int getNumeroMosse(){
        return numeroMosse;
    }

    /////////////////////////////////////////////////////////////////////////////

    public void setMiaPosizione(Point newPosition){
        posizione = newPosition;
    }

    public Point getMiaPosizione(){
        return posizione;
    }


    public void movimento(Point newPosition) {
        //cambiaMosse
        if (newPosition.x < 8 && newPosition.x > -1 && newPosition.y < 13 && newPosition.y > -1 && !mappa[newPosition.x][newPosition.y].equals("w")) {
            posizione.move(newPosition.x, newPosition.y);
        }


        //cambia il numero di movimenti disponibili
        numeroMosse--;

        //se il client entra in una stanza non si può più muovere
        if(mappa[posizione.x][posizione.y].equals("r")){
            numeroMosse = 0;
        }


        //invia il movimento al server
        //client.send();
    }

    /////////////////////////////////////////////////////////////////////////////

    public List<String> carteDaPoterMostrareAlPlayer(List<String> carteRichieste){

        List<String> solve = new ArrayList<>();

        for (String carta: carteRichieste) {
            if(carteInMano.contains(carta))
                solve.add(carta);
        }

        return solve;
    }

}
