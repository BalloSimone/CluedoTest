import com.jme3.network.AbstractMessage;
import com.jme3.network.HostedConnection;
import com.jme3.network.serializing.Serializable;
import com.jme3.network.serializing.Serializer;

import java.nio.ByteBuffer;
import java.util.List;

public class UtNetworking {
    public static final int PORT = 6000;

    public static void initialiseSerializables() {
        Serializer.registerClass(StartGameMessage.class);
        Serializer.registerClass(EnterLobbyMessage.class);
        Serializer.registerClass(LobbyDebugMess.class);
        Serializer.registerClass(DBMess.class);
        Serializer.registerClass(CheckLogin.class);
        Serializer.registerClass(ClientInformation.class);
        Serializer.registerClass(PlayingMessage.class);
        Serializer.registerClass(LobbyInformation.class);
        Serializer.registerClass(YouAreTheHost.class);
        Serializer.registerClass(StartNewGame.class);
        Serializer.registerClass(InitForStartingGame.class);
    }


    @Serializable
    public static class StartGameMessage extends AbstractMessage{
        private static String messSend;

        public StartGameMessage(){}

        public StartGameMessage(String mess){
            setMess(mess);
        }

        public static void setMess(String buttonName){
            messSend = buttonName;
        }

        public static String getMess(){
            return messSend;
        }
    }

    //messaggio per entrare in una lobby
    @Serializable
    public static class EnterLobbyMessage extends AbstractMessage{
        private String request;
        private ClientInformation sourceInfo;

        public EnterLobbyMessage(){}

        public EnterLobbyMessage(String request, ClientInformation sourceInfo){
            this.request = request;
            this.sourceInfo = sourceInfo;
        }

        public ClientInformation getSourceInfo(){return sourceInfo;}
        public String getRequest(){
            return request;
        }
    }

    @Serializable
    public static class LobbyDebugMess extends AbstractMessage{
        private static String request;

        public LobbyDebugMess(){}

        public LobbyDebugMess(String request){
            this.request = request;
        }


        public static String getRequest(){
            return request;
        }
    }

    @Serializable
    public static class DBMess extends AbstractMessage{
        private String request;
        private String nomeUtente;
        private String passw;

        public DBMess(){}

        public DBMess(String request, String nomeUtente, String passw){
            this.request = request;
            this.nomeUtente = nomeUtente;
            this.passw = passw;
        }


        public String getRequest(){
            return request;
        }
        public String getUserName() {return nomeUtente;}
        public  String getPassword(){return passw;}

    }

    @Serializable
    public static class CheckLogin extends AbstractMessage{
        private String response;
        private String id;
        private String nomeUtente;

        public CheckLogin(){}

        public CheckLogin(String response){
            this.response = response;
        }

        public CheckLogin(String response, String id, String nomeUtente){
            this.response = response;
            this.id = id;
            this.nomeUtente = nomeUtente;
        }

        public String getId(){return id;}
        public String getResponse(){
            return response;
        }
        public String getUserName() {return nomeUtente;}

    }

    @Serializable
    public static class PlayingMessage extends AbstractMessage{
        private int nTurno;
        private int x, y;//coordinate

        public PlayingMessage(){};

        public PlayingMessage(int nTurno, int x, int y){
            this.nTurno=nTurno;
            this.x=x;
            this.y=y;
        }

        public int getTurno(){
            return nTurno;
        }
        public int getX(){return x;}
        public int getY() {return y;}


    }

    @Serializable
    public static class LobbyInformation extends AbstractMessage{
        private List<String> userNames;
        private String lobbyId;

        public LobbyInformation(){};

        public LobbyInformation(String lobbyId, List<String> userNames) {
            this.userNames = userNames;
            this.lobbyId = lobbyId;
        }


        public List<String> getNames() { return userNames;}
        public String getLobbyId() {return lobbyId;}

    }

    @Serializable
    public static class YouAreTheHost extends AbstractMessage{
        public YouAreTheHost(){};

    }


    @Serializable
    public static class StartNewGame extends AbstractMessage{
        private String idLobby;

        public StartNewGame(){};

        public StartNewGame(String idLobby){
            this.idLobby = idLobby;
        }

        public String getIdLobby(){
            return idLobby;
        }

    }

    @Serializable
    public static class InitForStartingGame extends AbstractMessage{  //messaggio di inizio gioco
        private int nCarte;
        private List<String> carteInMano;



        public InitForStartingGame(){};

        public InitForStartingGame(int nCarte, List<String> carteInMano){
            this.nCarte = nCarte;
            this.carteInMano = carteInMano;
        };
        //QUI CI VANNO LE INFORMAZIONI DELLA LOGICA DI FILO

        public int getNCarte(){
            return nCarte;
        }

        public List<String> getCarteInMano(){
            return carteInMano;
        }
    }




}
