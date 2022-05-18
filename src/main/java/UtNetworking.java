import com.jme3.network.AbstractMessage;
import com.jme3.network.HostedConnection;
import com.jme3.network.serializing.Serializable;
import com.jme3.network.serializing.Serializer;

import java.nio.ByteBuffer;

public class UtNetworking {
    public static final int PORT = 6000;

    public static void initialiseSerializables() {
        Serializer.registerClass(StartGameMessage.class);
        Serializer.registerClass(EnterLobbyMessage.class);
        Serializer.registerClass(LobbyDebugMess.class);
        Serializer.registerClass(DBMess.class);
        Serializer.registerClass(CheckLogin.class);
        Serializer.registerClass(ClientInformation.class);
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



}
