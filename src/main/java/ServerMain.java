import com.jme3.app.SimpleApplication;
import com.jme3.network.*;
import com.jme3.network.serializing.Serializable;
import com.jme3.system.JmeContext;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

//gameServer.broadcast(Filters.in(activeLobbies.get(id).usersInLobby));


public class ServerMain extends SimpleApplication {
    Server gameServer;
    List<lobbyClass> activeLobbies = new LinkedList<lobbyClass>() {};
    DataDB database;

    public final String mappaOriginale[][] = {
            {"w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w"},
            {"w", "w", "w", "r", "w", "w", "w", "r", "w", "w", "w", "w", "w"},
            {"w", "w", "e", "e", "e", "e", "e", "e", "e", "e", "e", "w", "w"},
            {"w", "w", "e", "e", "e", "e", "e", "e", "e", "e", "e", "w", "w"},
            {"w", "w", "e", "e", "e", "v", "e", "e", "e", "e", "e", "w", "w"},
            {"w", "w", "e", "e", "e", "e", "e", "e", "e", "e", "e", "w", "w"},
            {"w", "w", "w", "w", "r", "w", "w", "w", "r", "w", "w", "w", "w"},
            {"w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w"}
    };

    private String carte[] = {"Green", "Mustard", "Orchid", "Peacock", "Plum", "Scarlett", "persone",
            "Candeliere", "Pugnale", "Tubo di piombo", "Pistola", "Corda", "Chiave inglese", "armi",
            "Sala da ballo", "Sala del biliardo", "Serra", "Sala da pranzo", "Ingresso", "Cucina", "Biblioteca", "Salotto", "Studio", "luoghi"};



    public static void main(String[] args) throws Exception {
        UtNetworking.initialiseSerializables();
        ServerMain app = new ServerMain();
        app.start(JmeContext.Type.Headless);
    }
    @Override
    public void simpleInitApp() {



        try {
            DataDB database = new DataDB("root", "", "usersclue");
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameServer = null;
        try {
            gameServer = Network.createServer(UtNetworking.PORT);
            gameServer.start();


        } catch (Exception e) { }


        Collection<HostedConnection> conn =  gameServer.getConnections(); //ottengo tutti gli host attualmente connessi al server
        System.out.println(conn.stream().count());

        //il server si mette in ascolto dei vari tipi di messaggi
        MessageListenerInit();




    }

    private void MessageListenerInit(){
        //server si mette in ascolto dei vari tipi di messaggi che gli arrivano dai client
        gameServer.addMessageListener(new ServerListener(), UtNetworking.StartGameMessage.class);
        gameServer.addMessageListener(new ServerListener(), UtNetworking.EnterLobbyMessage.class);
        gameServer.addMessageListener(new ServerListener(), UtNetworking.LobbyDebugMess.class);
        gameServer.addMessageListener(new ServerListener(), UtNetworking.DBMess.class);
        gameServer.addMessageListener(new ServerListener(), UtNetworking.StartNewGame.class);

    }



    @Serializable
    public class UserManager{
        ClientInformation cInfo;
        HostedConnection cNetwork;

        public UserManager(ClientInformation cInfo, HostedConnection cNetwork){
            this.cInfo = cInfo;
            this.cNetwork = cNetwork;
        }
    }

    public String[] getCarte(){
        return carte;

    }


    private class lobbyClass{
        private String idLobby;
        private HostedConnection GameHost;
        private List<UserManager> userInLobbyInfo = new ArrayList<UserManager>();
        private boolean CanSomeoneEntry;
        private boolean isInGame;

       String mappaTemporanea[][];

       List<String> mazzo = new LinkedList<>(Arrays.asList(getCarte()));


        //variabili necessarie in fase di game
        int turno;
        //static String nomeFaseTurno[] = {"lancia dadi", "movimento", "predizione"};
        String faseTurno = "lancia dadi";
        String personaTurno = "";
        int numeroMosse = 0;
        String persona, arma, luogo;

        public lobbyClass(){}

        public lobbyClass(String id, HostedConnection user, ClientInformation usInfo){
            this.idLobby = id;
            CanSomeoneEntry = true;
            isInGame = false;
            userInLobbyInfo.add(new UserManager(usInfo, user));
            GameHost = user;

        }

        public String getIdLobby() { return this.idLobby;}
        public void setLobbyOpened(){ CanSomeoneEntry = true;}
        public void setLobbyClosed(){ CanSomeoneEntry = false;}
        public void startGame(){ isInGame = true;}

        /*
        public void iniziaPartita()
        {
            for (int i = 0; i < mappaOriginale.length; i++) {
                System.arraycopy(mappaOriginale[i], 0, mappaTemporanea[i], 0, mappaOriginale[0].length);
            }

            estraiCarteVincenti();
            int nCartePerGiocatore = (mazzo.size())/numeroGiocatori;
            for(Giocatore g : giocatori)
            {
                g.ottieniNote(mazzo);//fornitura ai player delle carte
                List<String> carteInMano = new LinkedList<>();
                for(int j = 0; j<nCartePerGiocatore; j++) //distribuzione carte hai giocatori
                {
                    int temp = (int)(Math.random()*mazzo.size());
                    carteInMano.add(mazzo.get(temp));
                    mazzo.remove(temp);
                }
                g.ottieniMano(carteInMano);

                mappaTemporanea[2][2+g.id] = "g"+g.id; // metodo temporaneo per assegnazione della posizione di un giocatore(da cambiare)
                g.pos.x = 2;
                g.pos.y = 2+g.id;
            }

            // per decidere chi comincia
            turno = (int)(Math.random()*numeroGiocatori);
            personaTurno = giocatori.get(turno).nomeGiocatore;

            for(Giocatore g : giocatori){//do a tutti i giocatori le carte extra la mappa e decido chi comincia
                for(int i=0; i<mazzo.size(); i++)
                    g.cartaVista(mazzo.get(i));
                g.mappa = mappaTemporanea;
                g.turno = personaTurno;
            }

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
            estraiCarteVincenti();
            int nCartePerGiocatore = (mazzo.size())/numeroGiocatori;
            for(Giocatore g : giocatori)
            {
                g.ottieniNote(mazzo);//fornitura ai player delle carte
                List<String> carteInMano = new LinkedList<>();
                for(int j = 0; j<nCartePerGiocatore; j++) //distribuzione carte hai giocatori
                {
                    int temp = (int)(Math.random()*mazzo.size());
                    carteInMano.add(mazzo.get(temp));
                    mazzo.remove(temp);
                }
                g.ottieniMano(carteInMano);

                mappaTemporanea[2][2+g.id] = "g"+g.id; // metodo temporaneo per assegnazione della posizione di un giocatore(da cambiare)
                g.pos.x = 2;
                g.pos.y = 2+g.id;
            }

            // per decidere chi comincia
            turno = (int)(Math.random()*numeroGiocatori);
            personaTurno = giocatori.get(turno).nomeGiocatore;

            for(Giocatore g : giocatori){//do a tutti i giocatori le carte extra la mappa e decido chi comincia
                for(int i=0; i<mazzo.size(); i++)
                    g.cartaVista(mazzo.get(i));
                g.mappa = mappaTemporanea;
                g.turno = personaTurno;
            }

        }
        //////////////////////////////////////////////////////////////////////////+

        //funzioni che servono durante la partita
        public void effettuaMovimento(int x, int y) //spostamento del player a cui bisogna implementare il numero di mosse disponibili
        {
            for(Giocatore g : giocatori)
            {
                //if(g.idGiocatore == turno && faseTurno == "movimento")
                if(g.nomeGiocatore==giocatori.get(turno).nomeGiocatore && faseTurno == "movimento")
                {
                    if(mappaTemporanea[x][y].contains("g"))
                        mappaTemporanea[x][y] += "g"+ turno;
                    else
                        mappaTemporanea[x][y] = "g"+ turno;

                    mappaTemporanea[g.pos.x][g.pos.y] = mappaTemporanea[g.pos.x][g.pos.y].replace("g"+turno, "");
                    //System.out.println(mappaTemporanea[g.pos.x][g.pos.y]);
                    if(mappaTemporanea[g.pos.x][g.pos.y]=="")
                        mappaTemporanea[g.pos.x][g.pos.y]=mappaOriginale[g.pos.x][g.pos.y];

                    g.pos.x = x;
                    g.pos.y = y;


                    if(mappaTemporanea[x][y]=="r")
                    {
                        numeroMosse=0;
                        g.numeroMosse=0;
                        faseTurno = "predizione";
                        g.faseTurno = "predizione";
                    }
                    else
                    {
                        numeroMosse--;
                        g.numeroMosse--;
                        if(numeroMosse==0)
                            cambiaTurno();
                    }
                }
            }
        }

        public void cambiaTurno()
        {
            if(turno==numeroGiocatori)
                turno=0;
            else
                turno++;

            faseTurno = "lancia dadi";

            for(Giocatore g : giocatori)
            {
                g.turno = giocatori.get(turno).nomeGiocatore;
                g.faseTurno = "lancia dadi";
            }
        }*/
    }

    private lobbyClass getLobbyById(String lobbyId){
        for (lobbyClass lobby: activeLobbies) {
            if(lobby.getIdLobby().equals(lobbyId))
                return lobby;
        }
        return null;
    }

    private List<String> getAllUserNames(String lobbyId){
        List<String>  userNames = new ArrayList<String>();
        lobbyClass lobby = getLobbyById(lobbyId);
        for (UserManager user: lobby.userInLobbyInfo) {
            userNames.add(user.cInfo.getUsername());
        }
        return userNames;
    }

    private List<UserManager> getAllUsers(lobbyClass lobbyClass){
        return lobbyClass.userInLobbyInfo;
    }

    private String generateNewIdLobby(){
        String characters = "abcdefghijklmno1234567890";
        String result = "";
        for(int i=0; i<10; i++){
            result += characters.charAt((int)(Math.random() * characters.length()));
        }

        return result;
    }

    private void insertNewUser(String nomeUtente, String password) throws SQLException {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        //System
        database.stmt.executeUpdate("INSERT INTO tutenti(nomeUtente, password) VALUES(\""+nomeUtente+"\" ,\""+hashed+"\")");
    }

    private int checkUsrPass(String nomeUtente, String password) throws SQLException{
        String id = ""+0;

        ResultSet rs = database.stmt.executeQuery("SELECT id, nomeUtente, password from tutenti WHERE nomeUtente = \""+nomeUtente+"\"");
        if(rs.getMetaData().getColumnCount() > 0){
            rs.next();
            id = rs.getString(1);
            String hashed = rs.getString(3);
            if(BCrypt.checkpw(password, hashed))
                return Integer.parseInt(id);
            else
                return -1;
        }
        else return -1;
    }

    private void startGame(String idLobby){

        lobbyClass lobby = getLobbyById(idLobby); //ottengo la lobby in cui sta partendo la partita

        //invio le informazioni ai giocatori per iniziare a giocare
        for (UserManager user: lobby.userInLobbyInfo) {
            //ottieniNote(mazzo);//fornitura ai player delle carte da mettere nel server
            int nCartePerGiocatore = (lobby.mazzo.size())/lobby.userInLobbyInfo.size();
            List<String> carteInMano = new LinkedList<>();
            for(int j = 0; j<nCartePerGiocatore; j++) //distribuzione carte hai giocatori
            {
                int temp = (int)(Math.random()*lobby.mazzo.size());
                carteInMano.add(lobby.mazzo.get(temp));
                lobby.mazzo.remove(temp);
            }
            //g.ottieniMano(carteInMano);
            gameServer.getConnection(user.cNetwork.getId()).send(new UtNetworking.InitForStartingGame());
        }


    }


    private class ServerListener implements MessageListener<HostedConnection> {
        @Override
        public void messageReceived(HostedConnection source, Message m) {
            m.setReliable(false);     //!!senza settare reliable a true il server non riesce a vedere il messaggio che riceve dal client
            if(m instanceof UtNetworking.StartGameMessage){
                 //!!senza settare reliable a true il server non riesce a vedere il messaggio che riceve dal client
                UtNetworking.StartGameMessage mess = (UtNetworking.StartGameMessage) m;
                System.out.println(UtNetworking.StartGameMessage.getMess());

            }else if(m instanceof UtNetworking.EnterLobbyMessage) {
                UtNetworking.EnterLobbyMessage mess = (UtNetworking.EnterLobbyMessage) m;
                System.out.println("" + mess.getRequest() + "' from client " + source.getId());

                String request = mess.getRequest();
                ClientInformation sourceInfo = mess.getSourceInfo();


                if(request.equals("0")){
                    String idLobby = generateNewIdLobby();
                    //crea una nuova lobby al di la delle lobby con posti disponibili
                    activeLobbies.add(new lobbyClass(idLobby, source, sourceInfo));

                    //ottengo i nomi di tutti gli utenti presenti nella lobby
                    List<String> listNames = new ArrayList<String>();
                    listNames = getAllUserNames(idLobby);

                    //invio in risposta al client le informazioni sulla lobby in cui è entrato
                    gameServer.getConnection(source.getId()).send(new UtNetworking.LobbyInformation(idLobby, listNames));
                    gameServer.getConnection(source.getId()).send(new UtNetworking.YouAreTheHost());
                }else if(request.equals("1")) {
                    String idLobby;
                    //Entra nella prima lobby con un posto libero, se non ci sono lobby con un posto libero allora entra in una lobby come host
                    try {
                        //controlla ogni lobby
                        for(lobbyClass lobby: activeLobbies){
                            if (lobby != null && lobby.userInLobbyInfo.size() < 6 && lobby.CanSomeoneEntry) {
                                idLobby = lobby.getIdLobby();
                                lobby.userInLobbyInfo.add(new UserManager(sourceInfo, source)); //nel momento in cui trovo una lobby con dei posti liberi inserisco l'host nella lobby


                                //invio a tutti gli user nella lobby le informazioni aggiornate
                                for (UserManager host: lobby.userInLobbyInfo) {
                                    gameServer.getConnection(host.cNetwork.getId()).send(new UtNetworking.LobbyInformation(idLobby, getAllUserNames(idLobby)));
                                }
                                return;
                            }
                        }
                        idLobby = generateNewIdLobby();
                        //se il client non ha trovato una lobby, allora ne entra in una nuova come host
                        activeLobbies.add(new lobbyClass(idLobby, source, sourceInfo));

                    } catch (IndexOutOfBoundsException e) {
                        idLobby = generateNewIdLobby();
                        //nel caso in cui non ci siano lobby ne viene creata una nuova in cui il client diventa l'host
                        activeLobbies.add(new lobbyClass(idLobby, source, sourceInfo));
                    }
                    //invio in risposta al client le informazioni sulla lobby in cui è entrato
                    gameServer.getConnection(source.getId()).send(new UtNetworking.LobbyInformation(idLobby, getAllUserNames(idLobby)));
                    gameServer.getConnection(source.getId()).send(new UtNetworking.YouAreTheHost());
                }
            }else if(m instanceof UtNetworking.LobbyDebugMess){
                UtNetworking.LobbyDebugMess mess = (UtNetworking.LobbyDebugMess) m;
                for(lobbyClass lobby: activeLobbies){
                    System.out.println(lobby.CanSomeoneEntry);
                    System.out.println(lobby.GameHost);
                    for(UserManager host : lobby.userInLobbyInfo ){
                        System.out.println(host.cNetwork.getId());
                    }


                }

            }else if(m instanceof UtNetworking.DBMess){
                UtNetworking.DBMess dbMess = (UtNetworking.DBMess) m;


                System.out.println("database " + dbMess.getRequest() + "' from client " + source.getId());
                String request = dbMess.getRequest();
                int id = -1;
                if(request.equals("registrazione")){
                    try {
                        insertNewUser(""+dbMess.getUserName(),""+dbMess.getPassword());
                    } catch (SQLException e) {}
                }else if(request.equals("login")){
                    try {
                        id = checkUsrPass(""+dbMess.getUserName(),""+dbMess.getPassword());
                    } catch (SQLException e) {}

                    if(id > 0) {
                        gameServer.getConnection(source.getId()).send(new UtNetworking.CheckLogin("accepted",id+"", dbMess.getUserName()));
                    }else{
                        gameServer.getConnection(source.getId()).send(new UtNetworking.CheckLogin("not accepted"));
                    }


                }


            }else if(m instanceof UtNetworking.StartNewGame) {
                UtNetworking.StartNewGame mess = (UtNetworking.StartNewGame) m;
                String idLobby = mess.getIdLobby();
                startGame(idLobby); //starto il game di quella lobby

            }
        }
    }

}



