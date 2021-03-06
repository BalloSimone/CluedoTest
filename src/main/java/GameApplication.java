import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import com.jme3.scene.*;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.jme3.texture.*;
import com.jme3.network.*;
import com.mysql.cj.conf.ConnectionUrlParser;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.Size;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;


import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class GameApplication extends SimpleApplication {
    Client client;
    Nifty nifty;
    ClientInformation cInfo;
    GUI gui;
    List<ClientInformation> usersInMyLobby;
    Logica logic;


    public static void main(String[] args) {
        UtNetworking.initialiseSerializables();
        GameApplication app = new GameApplication();

        AppSettings settings = new AppSettings(true);
        settings.setTitle("Cluedo");
        settings.setFullscreen(false);
        settings.setWidth(1800);
        settings.setHeight(900);
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        app.setSettings(settings);
        app.start(JmeContext.Type.Display); //standard type for clientApplication

    }

    @Override
    public void simpleInitApp() {
        //Inizializzo la cartella di default
        assetManager.registerLocator("Assets/", FileLocator.class);
        //PARTE DI CONNESSIONE AL SERVER
        //

        //connessione al server
        try {
            client = Network.connectToServer("localhost", UtNetworking.PORT);
            client.start();
        } catch (IOException e) {
        }
        //client si mette in ascolto dei messaggi che gli arrivano dal server
        messageListenerInit();
        //

        //PARTE DELL'INIZIALIZZAZIONE DELLA LOGICA
        logic = new Logica(client);

        //PARTE DELL'INIZIALIZZAZIONE DELLA GUI
        niftyGuiInit();
        flyCam.setEnabled(true);
        flyCam.setMoveSpeed(100f);
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);

        //INZIALIZZAZIONE DELLA MAPPA
        //loadMap();

        // You must add a light to make the model visible
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, 1.0f).normalizeLocal());
        rootNode.addLight(sun);

        //inizializzazione input handler
        inputListenerInit();
        inputMappingInit();
    }

    private void niftyGuiInit() {
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                getAssetManager(),
                getInputManager(),
                getAudioRenderer(),
                getGuiViewPort());

        nifty = niftyDisplay.getNifty();
        getGuiViewPort().addProcessor(niftyDisplay);
        getFlyByCamera().setDragToRotate(true);

        // load default styles
        nifty.loadStyleFile("nifty-default-styles.xml");
        // load standard controls
        nifty.loadControlFile("nifty-default-controls.xml");

        //caricamento della classe contenente la GUI
        gui = new GUI(nifty, client, new ClientInformation(), logic);


        nifty.gotoScreen("loginScreen"); // start the screen
    }


    private void loadMap() {
        //caricamento del modello
        //il modello 3d viene gestito come un nodo composto da diversi componenti
        //ogni componente ?? un figlio del nodo, quindi per esempio in un modello contenente due stanze, per accedere alla prima utilizzo la funzione getchild(0), per accedere alla seconda getchild(1)
        Node map = (Node) assetManager.loadModel("Models/testCluedo.j3o");
        rootNode.attachChild(map);

        //setto la texture del primo componente del modello caricato
        LoadTexture(map.getChild(0), "Models/Tiles040_1K_Color.jpg");
        scaleTexture(map.getChild(0), 1.625f, 1f);

        //setto la texture esclusivamente al primo componente
        Node app = (Node) map.getChild(2);
        app = (Node) app.getChild(2);

        setElementHidden(app.getChild(0));
        System.out.println(app.getChild(1).getName());
        Material glass = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        ColorRGBA c = new ColorRGBA(0.701f, 0.972f, 1f, 0.70f);
        glass.setColor("Color", c);
        app.getChild(1).setMaterial(glass);
    }


    private void messageListenerInit() {
        client.addMessageListener(new ClientLIstener(), UtNetworking.StartGameMessage.class);
        client.addMessageListener(new ClientLIstener(), UtNetworking.CheckLogin.class);
        client.addMessageListener(new ClientLIstener(), UtNetworking.LobbyInformation.class);
        client.addMessageListener(new ClientLIstener(), UtNetworking.YouAreTheHost.class);
        client.addMessageListener(new ClientLIstener(), UtNetworking.InitForStartingGame.class);
        client.addMessageListener(new ClientLIstener(), UtNetworking.setGameForStart.class);
        client.addMessageListener(new ClientLIstener(), UtNetworking.sendMove.class);
        client.addMessageListener(new ClientLIstener(), UtNetworking.sendCardRequest.class);
    }

    public void inputListenerInit() {
        //comandi che il giocatore utilizza nella fase di movimento durante il suo turno
        inputManager.addListener(new ActionListener() {
            @Override
            public void onAction(String name, boolean isPressed, float tpf) {
                if (isPressed & logic.getMyTurn() & logic.getFaseTurno() == 1){
                    if(logic.movimento(new Coord(logic.getMiaPosizione().x-1, logic.getMiaPosizione().y))) {
                        client.send(new UtNetworking.sendMove(logic.getMiaPosizione(), cInfo, logic.getNgiocatore()));


                        double valore = getElement("p"+logic.getNgiocatore()).getConstraintY().getValue(100);
                        valore = valore - 4.5;
                        String newY = valore + "%";
                        getElement("p"+logic.getNgiocatore()).setConstraintY(new SizeValue(newY));
                        getElement("p"+logic.getNgiocatore()).getParent().layoutElements(); //aggiornare la grafica
                    }
                }
            }
        }, "avanti");

        inputManager.addListener(new ActionListener() {
            @Override
            public void onAction(String name, boolean isPressed, float tpf) {
                if (isPressed & logic.getMyTurn() & logic.getFaseTurno() == 1 && logic.getNumeroMosse() > 0){
                    if(logic.movimento(new Coord(logic.getMiaPosizione().x+1, logic.getMiaPosizione().y))) {
                        client.send(new UtNetworking.sendMove(logic.getMiaPosizione(), cInfo, logic.getNgiocatore()));

                        double valore = getElement("p"+logic.getNgiocatore()).getConstraintY().getValue(100);
                        valore = valore + 4.5;
                        String newY = valore + "%";
                        getElement("p"+logic.getNgiocatore()).setConstraintY(new SizeValue(newY));
                        getElement("p"+logic.getNgiocatore()).getParent().layoutElements(); //aggiornare la grafica
                    }
                }
            }
        }, "indietro");

        inputManager.addListener(new ActionListener() {
            @Override
            public void onAction(String name, boolean isPressed, float tpf) {
                if (isPressed & logic.getMyTurn() & logic.getFaseTurno() == 1 && logic.getNumeroMosse() > 0){
                    if(logic.movimento(new Coord(logic.getMiaPosizione().x, logic.getMiaPosizione().y+1))) {
                        client.send(new UtNetworking.sendMove(logic.getMiaPosizione(), cInfo, logic.getNgiocatore()));

                        double valore = getElement("p"+logic.getNgiocatore()).getConstraintX().getValue(100);
                        valore = valore + 4.5;
                        String newX = valore + "%";
                        getElement("p"+logic.getNgiocatore()).setConstraintX(new SizeValue(newX));
                        getElement("p"+logic.getNgiocatore()).getParent().layoutElements(); //aggiornare la grafica

                    }
                }
            }
        }, "destra");

        inputManager.addListener(new ActionListener() {
            @Override
            public void onAction(String name, boolean isPressed, float tpf) {
                if (isPressed & logic.getMyTurn() & logic.getFaseTurno() == 1 && logic.getNumeroMosse() > 0){
                    if(logic.movimento(new Coord(logic.getMiaPosizione().x, logic.getMiaPosizione().y-1))) {
                        client.send(new UtNetworking.sendMove(logic.getMiaPosizione(), cInfo, logic.getNgiocatore()));

                        double valore = getElement("p"+logic.getNgiocatore()).getConstraintX().getValue(100);
                        valore = valore - 4.5;
                        String newX = valore + "%";
                        getElement("p"+logic.getNgiocatore()).setConstraintX(new SizeValue(newX));
                        getElement("p"+logic.getNgiocatore()).getParent().layoutElements(); //aggiornare la grafica
                    }
                }
            }
        }, "sinistra");
    }

    public void inputMappingInit() {
        inputManager.addMapping("avanti", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("indietro", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("destra", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("sinistra", new KeyTrigger(KeyInput.KEY_A));
    }

    @Override
    public void simpleUpdate(float tpf) {
        updateNiftyGUI();
        controlloFaseTurno();

    }

    private void controlloFaseTurno(){
        if(logic.getFaseTurno() == 1 && logic.getNumeroMosse() == 0)
            logic.setFaseTurno(2);
    }

    private void updateNiftyGUI() {
        Screen screen = nifty.getCurrentScreen();
        if (screen == null) return;
        String currentScreen = screen.getScreenId();
        switch (currentScreen) {
            case "home": {
                if (!getText(getElement("userName")).equals("Account: " + cInfo.getUsername()))
                    nifty_changeText("userName", "Account: " + cInfo.getUsername());
                break;
            }
            case "lobbyscreen": {
                if(usersInMyLobby == null) return;
                for(int i=1; i<=usersInMyLobby.size(); i++){
                    nifty_changeText("user"+i, usersInMyLobby.get(i-1).getUsername());
                }

                if(cInfo.getHost()) {
                    //System.out.println("Sono l'host!!");
                    getElement("StartGameButton").setVisible(true);

                }
                break;
            }
            case "Game": {

                //icone personaggi
                try {
                    for (int i = 1; i <= 6; i++) {
                        if (i <= usersInMyLobby.size()) {
                            getElement("IconUser" + i).setVisible(true);
                            getElement("p" + i).setVisible(true);
                        }else {
                            getElement("IconUser" + i).setVisible(false);
                            getElement("p" + i).setVisible(false);
                        }
                    }
                }catch(Exception e){}

                //AGGIORNO LE POSIZIONI DEI GIOCATORI DAL PUNTO DI VISTA GRAFICO
                updatePosition();

                //se non ?? il mio turno non faccio niente
                if(!logic.getMyTurn()){
                    getElement("LanciaDadi").setVisible(false);
                    getElement("Ipotesi").setVisible(false);
                    getElement("Soluzione").setVisible(false);
                    return;
                }

                //se ?? il mio turno
                switch (logic.getFaseTurno()){

                    //fase di lancio dadi
                    case 0:{
                        //se ?? il turno del giocatore vengono mostrati questi bottoni
                        getElement("LanciaDadi").setVisible(true);
                        getElement("Ipotesi").setVisible(false);
                        getElement("Soluzione").setVisible(false);

                        break;
                    }

                    //fase di spostamento
                    case 1:{
                        getElement("LanciaDadi").setVisible(false);
                        break;
                    }

                    //fase di predizione
                    case 2:{
                        //se ?? il turno del giocatore vengono mostrati questi bottoni
                        getElement("LanciaDadi").setVisible(false);
                        getElement("Ipotesi").setVisible(true);
                        getElement("Soluzione").setVisible(true);

                        break;
                    }

                    //fase di soluzione
                    case 3:{
                        //se ?? il turno del giocatore vengono mostrati questi bottoni
                        getElement("LanciaDadi").setVisible(false);
                        getElement("Ipotesi").setVisible(false);
                        getElement("Soluzione").setVisible(true);

                        break;
                    }

                    default:
                }



                break;
            }

            case "predictionScreen": {

            }

            default:
        }
    }

    private void updatePosition() {

        if(logic.getPosizioniAltriGiocatori() == null) return;

        for (Map.Entry<Integer, Coord> pos : logic.getPosizioniAltriGiocatori().entrySet()) {

            //calcolo le nuove coordinate
            ConnectionUrlParser.Pair<String, String> newValues = obtainPosByCoord(pos.getValue().x, pos.getValue().y);
            //setto le nuove coordinate
            getElement("p"+pos.getKey()).setConstraintX(new SizeValue(newValues.left));
            getElement("p"+pos.getKey()).setConstraintY(new SizeValue(newValues.right));

            getElement("p"+pos.getKey()).getParent().layoutElements(); //aggiornare la grafica
        }

    }

    private ConnectionUrlParser.Pair<String, String> obtainPosByCoord(int x, int y) {
        final double xStart = 17.25;
        final double yStart = 27.5;

        double newX = xStart + (4.5 * x);
        double newY = yStart + (4.5 * y);

        return new ConnectionUrlParser.Pair<>(newY + "%", newX + "%");
    }

    private void nifty_changeText(String obj, String newValue) {
        Element elementToFill = nifty.getCurrentScreen().findElementById(obj);
        elementToFill.getRenderer(TextRenderer.class).setText(newValue);
    }

    private Element getElement(String id) {
        return nifty.getCurrentScreen().findElementById(id);
    }

    private String getText(Element e) {
        return e.getRenderer(TextRenderer.class).getOriginalText();
    }


    //funzione per settare texture trasparenti su diversi oggetti (viene utilizzata per i booleani di blender nella creazione di finestre  e porte)
    void setElementHidden(Spatial element) {
        Material hiddenMat;
        //inizializzo il materiale
        hiddenMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        hiddenMat.setTexture("ColorMap", assetManager.loadTexture("Models/Tiles040_1K_Color.jpg"));

        //setto il color con valore di opacity pari a -0.1
        ColorRGBA color = new ColorRGBA(1f, 1f, 1f, -0.1f);
        hiddenMat.setColor("Color", color);
        //attraverso la BlendMode Alpha rendo il colore "trasparente"
        hiddenMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        //setto il materiale dell'elemento
        element.setMaterial(hiddenMat);
    }

    //funzione per scalare la texture attraverso coordinate
    void scaleTexture(Spatial element, float x, float y) {
        //funzioni necessarie per ridimensionare le texture
        Geometry geom = (Geometry) element;
        geom.getMesh().scaleTextureCoordinates(new Vector2f(x, y));
    }

    //funzione che restituisce i nomi di tutti i figli di un nodo (modello o componente)
    void getNameOfAllChild(Node parentNode) {
        ArrayList<Spatial> nodesChild = new ArrayList(parentNode.getChildren());
        for (Spatial itemChild : nodesChild) {
            System.out.println(itemChild.getName());

        }
    }

    //funzione che carica una texture su un oggetto
    void LoadTexture(Spatial model, String textureUrl) {
        //creo il materiale
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");

        //carico la texture
        Texture t = assetManager.loadTexture(textureUrl);
        //faccio in modo che la texture si ripeti fino a riempire l'intero oggetto
        t.setWrap(Texture.WrapMode.Repeat);
        //setto la texture come diffuseMap
        material.setTexture("DiffuseMap", t);
        //dichiaro la lucentezza del materiale
        material.setFloat("Shininess", 20);
        //"attacco" la texture all'oggetto
        model.setMaterial(material);
    }

    //ascoltatore del client, che rimane in ascolto dei diversi messaggi che il server gli invia
    private class ClientLIstener implements MessageListener<Client> {

        @Override
        public void messageReceived(Client source, Message m) {
            if (m instanceof UtNetworking.StartGameMessage) {

                UtNetworking.StartGameMessage mess = (UtNetworking.StartGameMessage) m;
                System.out.println("Client " + source.getId() + " received: " + mess.getMess());

            } else if (m instanceof UtNetworking.CheckLogin) {

                UtNetworking.CheckLogin mess = (UtNetworking.CheckLogin) m;
                String response = mess.getResponse();
                System.out.println("Server reponse: " + response);
                if (response.equals("accepted")) {
                    //ottieni il tuo username e il tuo id
                    String id = mess.getId();
                    String username = mess.getUserName();
                    System.out.println("Id: " + id + ", Username: " + username);

                    //passa alla schermata start
                    nifty.gotoScreen("home");

                    //salva le tue informazioni
                    gui.cInfo.setId(Integer.parseInt(id));
                    gui.cInfo.setUserName(username);

                    cInfo = new ClientInformation(Integer.parseInt(id), username);

                } else {
                    Element elementPopUp = nifty.createPopup("registrationFailedPopUp");
                    elementPopUp.setWidth(100);
                    elementPopUp.setHeight(100);
                    nifty.showPopup(Objects.requireNonNull(nifty.getCurrentScreen()), Objects.requireNonNull(elementPopUp.getId()), null);
                }


            } else if (m instanceof UtNetworking.LobbyInformation) {

                //variabile di appoggio
                List<ClientInformation> app = new ArrayList<ClientInformation>();

                //ottengo dal server l'id della lobby in cui sono entrato e i nomi degli utenti che sono nella mia lobby
                UtNetworking.LobbyInformation mess = (UtNetworking.LobbyInformation) m;
                String id = mess.getLobbyId();
                List<String> usrNames = mess.getNames();

                //mi salvo l'id della mia lobby
                cInfo.setMyLobbyId(id);
                gui.cInfo.setMyLobbyId(id);

                System.out.println("ID LOBBY: " + id + "   UTENTI: " + usrNames);

                //mi salvo i nomi degli utenti nella mia lobby
                for (String name : usrNames) {
                    app.add(new ClientInformation(name));
                }
                usersInMyLobby = app;

            }else if (m instanceof UtNetworking.YouAreTheHost) {

                cInfo.setHost(true);

            }else if (m instanceof UtNetworking.InitForStartingGame) {

                UtNetworking.InitForStartingGame mess = (UtNetworking.InitForStartingGame) m;
                //i client prendono le informazioni che gli servono per iniziare a giocare dal server
                logic.setCarteInMano(mess.getCarteInMano());
                //i client passano alla schermata di gioco
                nifty.gotoScreen("Game");
                //setto il numero del player
                logic.setNgiocatore(mess.getNGiocatore());

                //inizializzo a livello grafico l'interfaccia Game


                System.out.println(mess.printMess());


            }else if (m instanceof UtNetworking.setGameForStart) {

                UtNetworking.setGameForStart mess = (UtNetworking.setGameForStart) m;

                //mi salvo le carte visibili
                logic.setCarteViste(mess.getCarteVisibili());

                //mi salvo la mia posizione e le altre posizioni dei giocatori
                HashMap<Integer, Coord> posizioni = mess.getPosizioniAltriGiocatori();



                //mi salvo la mia posizione e quella degli altri giocatori
                for(Map.Entry<Integer, Coord> pos : posizioni.entrySet()){
                    if(pos.getKey() == logic.getNgiocatore())
                        logic.setMiaPosizione(pos.getValue());
                    else
                        logic.addPosizioniAltriGiocatori(pos.getKey(), pos.getValue());
                }




                //il client controlla se ?? il primo utente a dover giocare

                if(mess.getFirstUser().getUsername().equals(cInfo.getUsername()))
                    logic.setMyTurn(true);
                else
                    logic.setMyTurn(false);

                System.out.println(mess.printMess());
            }else if (m instanceof UtNetworking.sendMove) {

                UtNetworking.sendMove mess = (UtNetworking.sendMove) m;
                Coord newPosition = mess.getNewPosition();
                System.out.println(mess.getClient().getUsername()+" si ?? spostato alle coordinate "+newPosition.x+" "+newPosition.y);

                //aggiorno la posizione del giocatore che si ?? mosso
                logic.getPosizioniAltriGiocatori().put(mess.getnGiocatore(), newPosition);

            }

        }
    }

}
