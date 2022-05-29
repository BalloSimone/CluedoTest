import com.jme3.network.AbstractMessage;
import com.jme3.network.HostedConnection;
import com.jme3.network.serializing.Serializable;
import com.jme3.network.serializing.Serializer;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
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
        Serializer.registerClass(setGameForStart.class);
        Serializer.registerClass(sendMoveToServer.class);
        Serializer.registerClass(sendMoveToOtherClient.class);
        Serializer.registerClass(sendCardRequestToServer.class);
        Serializer.registerClass(sendCardRequestToClient.class);


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
    public static class InitForStartingGame extends AbstractMessage{  //inizializzazione carte giocatori
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

    @Serializable
    public static class setGameForStart extends AbstractMessage{  //scelta ordine turni e altre cose
        private HostedConnection firstUser;
        private List<String> carteVisibili;
        private HashMap<ClientInformation, Point> posizioniAltriGiocatori;


        public setGameForStart(){};

        public setGameForStart(HostedConnection firstUser, List<String> carteVisibili, HashMap<ClientInformation, Point> posizioniAltriGiocatori){
           this.carteVisibili = carteVisibili;
           this.firstUser = firstUser;
           this.posizioniAltriGiocatori = posizioniAltriGiocatori;
        };

        public HashMap<ClientInformation, Point> getPosizioniAltriGiocatori(){
            return posizioniAltriGiocatori;
        }

        public HostedConnection getFirstUser(){
            return firstUser;
        }

        public List<String> getCarteVisibili(){
            return carteVisibili;
        }
    }


    @Serializable
    public static class sendMoveToServer extends AbstractMessage{  //scelta ordine turni e altre cose
        private Point newPosition;


        public sendMoveToServer(){};

        public sendMoveToServer(Point newPosition){
            this.newPosition = newPosition;
        };


        public Point getNewPosition(){
            return newPosition;
        }

    }


    @Serializable
    public static class sendMoveToOtherClient extends AbstractMessage{
        private Point newPosition;
        private HostedConnection client;


        public sendMoveToOtherClient(){};

        public sendMoveToOtherClient(Point newPosition, HostedConnection client){
            this.newPosition = newPosition;
            this.client = client;
        };

        public Point getNewPosition() {
            return newPosition;
        }

        public HostedConnection getClient() {
            return client;
        }
    }


    @Serializable
    public static class sendCardRequestToServer extends AbstractMessage{
        private List<String> carteRichieste;

        public sendCardRequestToServer(){};

        public sendCardRequestToServer(List<String> carteRichieste){
            this.carteRichieste = carteRichieste;
        };

        public List<String> getCarteRichieste() {
            return carteRichieste;
        }
    }

    @Serializable
    public static class sendCardRequestToClient extends AbstractMessage{
        private List<String> carteRichieste;
        private HostedConnection client;

        public sendCardRequestToClient(){};

        public sendCardRequestToClient(List<String> carteRichieste, HostedConnection client){
            this.carteRichieste = carteRichieste;
            this.client = client;
        };

        public List<String> getCarteRichieste() {
            return carteRichieste;
        }

        public HostedConnection getClient() {
            return client;
        }
    }




}
