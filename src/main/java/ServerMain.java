import com.jme3.app.SimpleApplication;
import com.jme3.network.*;
import com.jme3.network.serializing.Serializable;
import com.jme3.system.JmeContext;
import org.mindrot.jbcrypt.BCrypt;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

//gameServer.broadcast(Filters.in(activeLobbies.get(id).usersInLobby));


public class ServerMain extends SimpleApplication {
    Server gameServer;
    List<lobbyClass> activeLobbies = new LinkedList<lobbyClass>() {};
    DataDB database;

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


    public static final List<Coord> startPositions = new ArrayList<Coord>(Arrays.asList(new Coord(2,2), new Coord(2,2), new Coord(2,2), new Coord(2,2), new Coord(2,2)));

    private static String[] carte = {"Green", "Mustard", "white", "Peacock", "Plum", "Scarlett", "persone",
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
        gameServer.addMessageListener(new ServerListener(), UtNetworking.sendMove.class);
        gameServer.addMessageListener(new ServerListener(), UtNetworking.sendCardRequest.class);

    }



    @Serializable
    public static class UserManager{
        ClientInformation cInfo;
        HostedConnection cNetwork;

        public UserManager(ClientInformation cInfo, HostedConnection cNetwork){
            this.cInfo = cInfo;
            this.cNetwork = cNetwork;
        }
    }

    public static String[] getCarte(){
        return carte;
    }


    public static class lobbyClass{
        private String idLobby;
        private HostedConnection GameHost;
        private List<UserManager> userInLobbyInfo = new ArrayList<UserManager>();
        private boolean CanSomeoneEntry;
        private boolean isInGame;
        List<String> mazzo = new LinkedList<>(Arrays.asList(getCarte()));
        LogicaServer gameLobbyLogic;

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
        public List<HostedConnection> getAllUserConnection(){

            List<HostedConnection> connections = new ArrayList<HostedConnection>();

            for (UserManager user:userInLobbyInfo) {
                connections.add(user.cNetwork);
            }

            return connections;
        }

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

    private lobbyClass getLobbyByUser(HostedConnection user){
        for (lobbyClass lobby: activeLobbies){
            for (UserManager users: lobby.userInLobbyInfo) {
               if(users.cNetwork == user)
                   return lobby;
            }
        }

        return null;
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

        lobbyClass lobby = getLobbyById(idLobby);
        lobby.gameLobbyLogic = new LogicaServer(mappa, carte, lobby.userInLobbyInfo);

        //ESTRAGGO LE CARTE VINCENTI
        lobby.gameLobbyLogic.estraiCarteVincenti();

        //DISTRIBUISCO LE CARTE AI GIOCATORI
        //ottengo la lobby in cui sta partendo la partita
        int nCartePerGiocatore = (lobby.gameLobbyLogic.mazzo.size())/lobby.userInLobbyInfo.size();
        //invio le informazioni ai giocatori per iniziare a giocare
        for (UserManager user: lobby.userInLobbyInfo) {
            //ottieniNote(mazzo);//fornitura ai player delle carte da metter
            List<String> carteInMano = new ArrayList<>();
            for(int j = 0; j<nCartePerGiocatore; j++) //distribuzione carte ai giocatori
            {
                int temp = (int)(Math.random()*(lobby.gameLobbyLogic.mazzo.size()-1));
                carteInMano.add(lobby.gameLobbyLogic.mazzo.get(temp));
                lobby.gameLobbyLogic.mazzo.remove(temp);
            }
            gameServer.getConnection(user.cNetwork.getId()).send(new UtNetworking.InitForStartingGame(nCartePerGiocatore, carteInMano));
        }


        //FACCIO VEDERE LE CARTE RIMANENTI A TUTTI I GIOCATORI
        List<String> carteRimanenti =  lobby.gameLobbyLogic.getCarteRimanenti();

        //DECIDO L'ORDINE DEI TURNI
        lobby.gameLobbyLogic.setOrdineTurni();

        HashMap<ClientInformation, Coord> posizioni = new HashMap<>();

        int cont = 0;
        //DECIDO LE POSIZIONI DI PARTENZA
        for (UserManager user:lobby.userInLobbyInfo) {
            posizioni.put(user.cInfo, startPositions.get(cont));
            cont++;
        }


        Collection<HostedConnection> collection = lobby.getAllUserConnection();
        gameServer.broadcast(Filters.in(collection), new UtNetworking.setGameForStart(lobby.gameLobbyLogic.giocatori.get(0).cInfo, carteRimanenti, posizioni));

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

            }else if(m instanceof UtNetworking.sendMove) {
                UtNetworking.sendMove mess = (UtNetworking.sendMove) m;
                //mi salvo la nuova posizione
                Coord newPosition = mess.getNewPosition();

                //trovo la lobby
                lobbyClass lobby = getLobbyByUser(source);

                //trovo gli utenti a cui devo inviare il messaggio
                List<UserManager> destinationHosts = getAllUsers(lobby);

                //invio il messaggio
                for (UserManager user:destinationHosts) {
                    if(user.cNetwork.getId() != source.getId())
                        gameServer.getConnection(user.cNetwork.getId()).send(new UtNetworking.sendMove(newPosition, mess.getClient()));
                }
            }else if(m instanceof UtNetworking.sendCardRequest) {
                UtNetworking.sendCardRequest mess = (UtNetworking.sendCardRequest) m;




            }
        }
    }

}
