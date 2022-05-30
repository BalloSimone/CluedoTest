import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.network.Client;
import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;


import javax.annotation.Nonnull;

public class GameController extends BaseAppState implements ScreenController {
    Client clientHostConnected;
    Nifty niftyHostConnected;
    ClientInformation cInfo;
    Logica logic;

    public GameController(Client c, Nifty nifty, ClientInformation cInfo, Logica logic){
        clientHostConnected = c;
        niftyHostConnected = nifty;
        this.cInfo = cInfo;
        this.logic = logic;
    }

    @Override
    protected void initialize(Application app) {

    }

    @Override
    protected void cleanup(Application app) {
        System.exit(0);
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {

    }

    @Override
    public void onStartScreen() {

    }

    @Override
    public void onEndScreen() {

    }


    public void startGame(){
        System.out.println(cInfo.getMyLobbyId());
        clientHostConnected.send(new UtNetworking.StartNewGame(cInfo.getMyLobbyId()));
        niftyHostConnected.gotoScreen("Game");
    }

    public void startScreen(){
        niftyHostConnected.gotoScreen("startGameScreen");
    }

    public void exitGame(){
        System.exit(0);
    }

    public void CreateLobby(){
        clientHostConnected.send(new UtNetworking.EnterLobbyMessage("0", cInfo));
        niftyHostConnected.gotoScreen("lobbyscreen");
    }

    public void EnterLobby()
    {
        clientHostConnected.send(new UtNetworking.EnterLobbyMessage("1", cInfo));
        niftyHostConnected.gotoScreen("lobbyscreen");
    }

    public void returnToMenu(){
        niftyHostConnected.gotoScreen("home");
    }
    public void goToRegistration(){niftyHostConnected.gotoScreen("registrationScreen");}
    public void goToLogin(){ niftyHostConnected.gotoScreen("loginScreen"); }

    public void insertNewAccount(){
        Screen s = niftyHostConnected.getCurrentScreen();

        String username = s.findNiftyControl("usernameTextField", TextField.class).getRealText();
        String password = s.findNiftyControl("passwordTextField", TextField.class).getRealText();
        clientHostConnected.send(new UtNetworking.DBMess("registrazione", username, password));
    }

    public void logToGame(){
        Screen s = niftyHostConnected.getCurrentScreen();
        String username = s.findNiftyControl("usernameTextField", TextField.class).getRealText();
        String password = s.findNiftyControl("passwordTextField", TextField.class).getRealText();
        clientHostConnected.send(new UtNetworking.DBMess("login", username, password));

    }

    public void closePopUp(String id){
        niftyHostConnected.getCurrentScreen().closePopup(niftyHostConnected.getTopMostPopup(), new EndNotify() {
            @Override
            public void perform() {

            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////

    public void lanciaDadi(){
        //il giocatore lancia i dadi
        logic.lanciaDadi();
        System.out.println(logic.getNumeroMosse());
        //il giocatore passa alla fase successiva
        logic.setFaseTurno(1);
    }

    /////////////////////////////////////////////////////////////////////////////

    public void predizione(){
        //il giocatore passa alla schermata di predizione
        niftyHostConnected.gotoScreen("predictionScreen");
    }

    public void addPersona(String carta){
        logic.setPersonaDaMostrareAlPlayer(carta);
    }

    public void addArma(String carta){
        logic.setArmaDaMostrareAlPlayer(carta);
    }

    public void addLuogo(String carta){
        logic.setLuogoDaMostrareAlPlayer(carta);
    }

    public void effettuaPredizione(){
        clientHostConnected.send(new UtNetworking.sendCardRequest(logic.getCarteRichieste(), cInfo));
    }

    /////////////////////////////////////////////////////////////////////////////

    public void soluzione(){
        niftyHostConnected.gotoScreen("solutionScreen");
    }

}
