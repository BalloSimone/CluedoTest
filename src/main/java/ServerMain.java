import com.jme3.app.SimpleApplication;
import com.jme3.network.*;
import com.jme3.system.JmeContext;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

//gameServer.broadcast(Filters.in(activeLobbies.get(id).usersInLobby));


public class ServerMain extends SimpleApplication {
    Server gameServer;
    List<lobbyClass> activeLobbies = new LinkedList<lobbyClass>(){};
    DataDB database;
    public static void main(String[] args) throws Exception {
        UtNetworking.initialiseSerializables();
        ServerMain app = new ServerMain();
        app.start(JmeContext.Type.Headless);
    }
    @Override
    public void simpleInitApp() {
        try {
            DataDB database = new DataDB("root", "", "usersClue");
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

    }

    private class UserManager{
        ClientInformation cInfo;
        HostedConnection cNetwork;

        public UserManager(ClientInformation cInfo, HostedConnection cNetwork){
            this.cInfo = cInfo;
            this.cNetwork = cNetwork;
        }
    }



    private class lobbyClass{
        private int idLobby;
        private HostedConnection GameHost;
        private final List<ClientInformation> userInLobbyInfo = new ArrayList<ClientInformation>();
        private final Collection<HostedConnection> usersInLobby = new ArrayList<HostedConnection>();
        private boolean CanSomeoneEntry;
        private boolean isInGame;

        public lobbyClass(){}

        public lobbyClass(HostedConnection user, ClientInformation usInfo){
            CanSomeoneEntry = true;
            isInGame = false;
            userInLobbyInfo.add(usInfo);
            usersInLobby.add(user);
            GameHost = user;

        }

        public void setLobbyOpened(){ CanSomeoneEntry = true;}
        public void setLobbyClosed(){ CanSomeoneEntry = false;}
        public void startGame(){ isInGame = true;}


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
                    //crea una nuova lobby al di la delle lobby con posti disponibili
                    activeLobbies.add(new lobbyClass(source, sourceInfo));
                }else if(request.equals("1")) {
                    //Entra nella prima lobby con un posto libero, se non ci sono lobby con un posto libero allora entra in una lobby come host
                    try {
                        //controlla ogni lobby
                        for(lobbyClass lobby: activeLobbies){
                            if (lobby != null && lobby.usersInLobby.size() < 6 && lobby.CanSomeoneEntry) {
                                lobby.usersInLobby.add(source);
                                lobby.userInLobbyInfo.add(sourceInfo);
                                return;
                            }
                        }
                        //se il client non ha trovato una lobby, allora ne entra in una nuova come host
                        activeLobbies.add(new lobbyClass(source, sourceInfo));

                    } catch (IndexOutOfBoundsException e) {
                        //nel caso in cui non ci siano lobby ne viene creata una nuova in cui il client diventa l'host
                        activeLobbies.add(new lobbyClass(source, sourceInfo));
                    }
                }
            }else if(m instanceof UtNetworking.LobbyDebugMess){
                UtNetworking.LobbyDebugMess mess = (UtNetworking.LobbyDebugMess) m;
                for(lobbyClass lobby: activeLobbies){
                    System.out.println(lobby.CanSomeoneEntry);
                    System.out.println(lobby.GameHost);
                    for(HostedConnection host : lobby.usersInLobby ){
                        System.out.println(host.getId());
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
                        id =  checkUsrPass(""+dbMess.getUserName(),""+dbMess.getPassword());
                    } catch (SQLException e) {}

                    if(id > 0) {
                        gameServer.getConnection(source.getId()).send(new UtNetworking.CheckLogin("accepted",id+"", dbMess.getUserName()));
                    }else{
                        gameServer.getConnection(source.getId()).send(new UtNetworking.CheckLogin("not accepted"));
                    }


                }


            }
        }
    }
}



