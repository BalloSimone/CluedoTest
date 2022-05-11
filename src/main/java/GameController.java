import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.network.Client;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;


import javax.annotation.Nonnull;

public class GameController extends BaseAppState implements ScreenController {
    Client clientHostConnected;
    Nifty niftyHostConnected;
    ClientInformation cInfo;

    public GameController(Client c, Nifty nifty, ClientInformation cInfo){
        clientHostConnected = c;
        niftyHostConnected = nifty;
        this.cInfo = cInfo;
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
        niftyHostConnected.closePopup(id);
    }

}
