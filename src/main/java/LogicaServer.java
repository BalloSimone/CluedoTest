import com.jme3.network.HostedConnection;

import java.util.*;

public class LogicaServer {
    //variabili necessarie per iniziare la partita

    /* Carte vincenti
    * Lista (persona, arma, luogo)
    *
    * */

    static String mappa[][];
    static String carte[];
    List<ServerMain.UserManager> giocatori;
    List<String> mazzo;
    static List<String> carteVincenti;

    ////////////////////////////////////////////////////////////////////////////////////

    //variabili necessarie in fase di game
    static String faseTurno = "lancia dadi";
    static int turno;
    static boolean finePartita = false;

    public LogicaServer(String mappa[][], String carte[], List<ServerMain.UserManager> users)
    {
        this.mappa = mappa;
        giocatori = users;
        mazzo = new LinkedList<>(Arrays.asList(carte));

    }


    //////////////////////////////////////////////////////////////////////////

    public void estraiCarteVincenti()
    {
        int indexArma=0, indexLuogo=0, indexPersona=0;
        for(int i = 0; i<carte.length; i++)
        {
            if(carte[i]=="persone")
                indexPersona = i;
            if(carte[i]=="armi")
                indexArma = i;
            if(carte[i]=="luoghi")
                indexLuogo = i;
        }

        //salvataggio e rimozione dal mazzo delle carte vincenti
        carteVincenti.add(mazzo.remove((int)(Math.random()*indexPersona)));
        carteVincenti.add(mazzo.remove((int)(Math.random()*(indexArma-indexPersona-1)+indexPersona+1)-1));
        carteVincenti.add(mazzo.remove((int)(Math.random()*(indexLuogo-indexArma-1)+indexArma+1)-2));

        //rimozione delle stringhe "persone" "armi" "luoghi" dal mazzo
        mazzo.remove(indexPersona-1);
        mazzo.remove(indexArma-3);
        mazzo.remove(indexLuogo-5);
    }


    //////////////////////////////////////////////////////////////////////////


    public List<String> getCarteRimanenti(){
        return mazzo;
    }

    //////////////////////////////////////////////////////////////////////////

    public void setOrdineTurni()
    {
        //mescolo l'ordine dei giocatori
        Collections.shuffle(giocatori);
    }

    //////////////////////////////////////////////////////////////////////////


    public boolean effettuaSoluzione(List<String> carteIpotizzate)
    {
        if(carteVincenti == carteIpotizzate)
            return true;
        return false;
    }




}
