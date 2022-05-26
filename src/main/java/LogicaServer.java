import com.jme3.network.HostedConnection;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class LogicaServer {
    //variabili necessarie per iniziare la partita

    static String mappa[][];

    static String carte[];
    static List<ServerMain.UserManager> giocatori;
    static List<String> mazzo;
    static int numeroGiocatori;
    ////////////////////////////////////////////////////////////////////////////////////

    //variabili necessarie in fase di game
    static int turno;
    static String faseTurno = "lancia dadi";
    static String personaTurno = "";
    static int numeroMosse = 0;
    static String persona, arma, luogo;
    static boolean finePartita = false;

    public LogicaServer(String mappa[][], String carte[], List<ServerMain.UserManager> users)
    {
        giocatori = new LinkedList<>();
        mazzo = new LinkedList<>(Arrays.asList(carte));
        turno = 0;
        numeroGiocatori = 0;
    }


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
        persona = mazzo.remove((int)(Math.random()*indexPersona));
        arma = mazzo.remove((int)(Math.random()*(indexArma-indexPersona-1)+indexPersona+1)-1);
        luogo = mazzo.remove((int)(Math.random()*(indexLuogo-indexArma-1)+indexArma+1)-2);

        //rimozione delle stringhe "persone" "armi" "luoghi" dal mazzo
        mazzo.remove(indexPersona-1);
        mazzo.remove(indexArma-3);
        mazzo.remove(indexLuogo-5);
    }

    public void iniziaPartita()
    {

        //ottieniNote(mazzo);
        estraiCarteVincenti();
        int nCartePerGiocatore = (mazzo.size())/numeroGiocatori;

        for(ServerMain.UserManager g : giocatori)
        {
            List<String> carteInMano = new LinkedList<>();
            for(int j = 0; j<nCartePerGiocatore; j++) //distribuzione carte hai giocatori
            {
                int temp = (int)(Math.random()*mazzo.size());
                carteInMano.add(mazzo.get(temp));
                mazzo.remove(temp);
            }
            //g.ottieniMano(carteInMano);   -->da fixare

            ServerMain.lobbyClass.mappaTemporanea[2][2+g.cInfo.getId()] = "g"+g.cInfo.getId(); // metodo temporaneo per assegnazione della posizione di un giocatore(da cambiare)
            g.cInfo.pos.x = 2;
            g.cInfo.pos.y = 2+g.cInfo.getId();
        }

        // per decidere chi comincia
        turno = (int)(Math.random()*numeroGiocatori);
        personaTurno = giocatori.get(turno).cInfo.getUsername();

        for(ServerMain.UserManager g : giocatori){//do a tutti i giocatori le carte extra la mappa e decido chi comincia
            //for(int i=0; i<mazzo.size(); i++)
                //g.cartaVista(mazzo.get(i));    -->da fixare
            //g.mappa = mappaTemporanea;
            //g.turno = personaTurno;
        }

    }

    //////////////////////////////////////////////////////////////////////////
    //funzioni che servono durante la partita
    public static void effettuaMovimento(int x, int y) //spostamento del player a cui bisogna implementare il numero di mosse disponibili
    {
        for(ServerMain.UserManager g : giocatori)
        {
            //if(g.idGiocatore == turno && faseTurno == "movimento")
            if(g.cInfo.getUsername()==giocatori.get(turno).cInfo.getUsername() && faseTurno == "movimento")
            {
                if(ServerMain.lobbyClass.mappaTemporanea[x][y].contains("g"))
                    ServerMain.lobbyClass.mappaTemporanea[x][y] += "g"+ turno;
                else
                    ServerMain.lobbyClass.mappaTemporanea[x][y] = "g"+ turno;

                ServerMain.lobbyClass.mappaTemporanea[g.cInfo.pos.x][g.cInfo.pos.y] = ServerMain.lobbyClass.mappaTemporanea[g.cInfo.pos.x][g.cInfo.pos.y].replace("g"+turno, "");
                //System.out.println(mappaTemporanea[g.pos.x][g.pos.y]);
                if(ServerMain.lobbyClass.mappaTemporanea[g.cInfo.pos.x][g.cInfo.pos.y]=="")
                    ServerMain.lobbyClass.mappaTemporanea[g.cInfo.pos.x][g.cInfo.pos.y]=ServerMain.mappaOriginale[g.cInfo.pos.x][g.cInfo.pos.y];

                g.cInfo.pos.x = x;
                g.cInfo.pos.y = y;


                if(ServerMain.lobbyClass.mappaTemporanea[x][y]=="r")
                {
                    numeroMosse=0;
                    g.cInfo.numeroMosse=0;
                    faseTurno = "predizione";
                    //g.faseTurno = "predizione";   -->  da sistemare
                }
                else
                {
                    numeroMosse--;
                    g.cInfo.numeroMosse--;
                    if(numeroMosse==0)
                        cambiaTurno();
                }
            }
        }
    }

    public static void effettuaPredizione(String persona, String arma, String luogo)
    {

        for(ServerMain.UserManager g : giocatori)
        {   //g.cInfo.getUSername() --> g.nomeGiocatore
            if(g.cInfo.getUsername()==giocatori.get(turno).cInfo.getUsername() && faseTurno == "predizione")
            {
                if(ServerMain.mappaOriginale[g.cInfo.pos.x][g.cInfo.pos.y].equals("v"))
                {
                    if(ServerMain.lobbyClass.persona==persona && ServerMain.lobbyClass.arma==arma && ServerMain.lobbyClass.luogo==luogo)
                    {
                        finePartita = true;
                    }
                    else
                    {
                        numeroGiocatori--;
                        giocatori.remove(g);
                    }
                }
                else
                {
                    //g.cartaVista(vediCarta(turno, persona, arma, luogo));   --> da sistemare
                }
            }
        }
    }

    static private String vediCarta(int index, String persona, String arma, String luogo)
    {
        int temp;

        if(index == numeroGiocatori-1)
            temp=0;
        else
            temp = index+1;


        while(temp!=index)
        {
            ServerMain.UserManager g = giocatori.get(temp);
    //DA RIGUARDARE
   /*         for(int i=0; i<g.carteInMano.size(); i++)
            {
                if(g.carteInMano.get(i) == persona || g.carteInMano.get(i) == arma || g.carteInMano.get(i) == luogo)
                    return g.carteInMano.get(i);
            }

*/
            if(index == numeroGiocatori-1)
                temp=0;
            else
                temp++;
        }

        return "";
    }


    public static void cambiaTurno()
    {
        if(turno==numeroGiocatori)
            turno=0;
        else
            turno++;

        faseTurno = "lancia dadi";

        for(ServerMain.UserManager g : giocatori)
        {
            //g.turno = giocatori.get(turno).nomeGiocatore;   -->da fixare
            //g.faseTurno = "lancia dadi";   --> da fixare
        }
    }

    public void ottieniNote(List<String> carte)
    {
        for(String s : carte)
        {
            //note.put(s, false);   --> note non esiste
        }
    }


}
