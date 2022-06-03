import com.jme3.network.Client;
import com.jme3.opencl.Image;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.Menu;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.tools.Color;
import de.lessvoid.nifty.tools.SizeValue;

import static de.lessvoid.nifty.render.RenderStateType.position;

//classe grafica della graphical user interface
public class GUI {
    Nifty nifty;
    Client client;
    ClientInformation cInfo;
    Logica clientLogic;

    public GUI(Nifty nifty, Client client, ClientInformation cInfo, Logica logic){
        this.nifty = nifty;
        this.client = client;
        this.cInfo = cInfo;
        this.clientLogic = logic;
        initScreen();

    }

    public void initScreen(){
        loadControlDefinition();
        registrationScreen();
        loginScreen();
        home();
        startGameScreen();
        lobbyScreen();
        gameScreen();
        predictionScreen();
        registrationFailedPopUp();
    }

    public void predictionScreen() {
        nifty.addScreen("predictionScreen", new ScreenBuilder("predictionScreen") {{
            controller(new GameController(client, nifty, cInfo, clientLogic));

            layer(new LayerBuilder("background") {{
                childLayoutVertical();
                backgroundColor("#0000");
                backgroundImage("Interface/predizione/background.jpeg");
            }});

            layer(new LayerBuilder("foreground") {{
                childLayoutVertical();
                backgroundColor("#0000");

                //persone
                panel(new PanelBuilder("Persone"){{
                    childLayoutHorizontal();
                    backgroundColor("#0000");
                    height("25%");
                    width("80%");

                    text(new TextBuilder("tpersone"){{
                        marginLeft("3%");
                        valignCenter();
                        text("Persone");
                        font("Interface/Fonts/Default.fnt");
                        backgroundColor("#0000");
                        color("#ffff");
                        height("30%");
                        width("15%");
                        onActiveEffect(new EffectBuilder("textSize"){{
                            effectParameter("endSize", "2");
                        }});
                    }});

                    control(new ButtonBuilder("Scarlett",""){{
                        name("button");
                        alignCenter();
                        valignCenter();
                        width("6%");
                        height("70%");
                        interactOnClick("addPersona(\"Scarlett\")");
                    }});

                    panel(new PanelBuilder("separator1"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("5%");
                        width("5%");
                    }});

                    control(new ButtonBuilder("Green",""){{
                        name("button");
                        alignCenter();
                        valignCenter();
                        width("6%");
                        height("70%");
                        interactOnClick("addPersona(\"Green\")");
                    }});

                    panel(new PanelBuilder("separator2"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("5%");
                        width("5%");
                    }});

                    control(new ButtonBuilder("Mustard",""){{
                        name("button");
                        alignCenter();
                        valignCenter();
                        width("6%");
                        height("70%");
                        interactOnClick("addPersona(\"Mustard\")");
                    }});

                    panel(new PanelBuilder("separator3"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("5%");
                        width("5%");
                    }});

                    control(new ButtonBuilder("White",""){{
                        name("button");
                        alignCenter();
                        valignCenter();
                        width("6%");
                        height("70%");
                        interactOnClick("addPersona(\"White\")");
                    }});

                    panel(new PanelBuilder("separator4"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("5%");
                        width("5%");
                    }});

                    control(new ButtonBuilder("Peacock",""){{
                        name("button");
                        alignCenter();
                        valignCenter();
                        width("6%");
                        height("70%");
                        interactOnClick("addPersona(\"Peacock\")");
                    }});

                    panel(new PanelBuilder("separator5"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("5%");
                        width("5%");
                    }});

                    control(new ButtonBuilder("Plum",""){{
                        name("button");
                        alignCenter();
                        valignCenter();
                        width("6%");
                        height("70%");
                        interactOnClick("addPersona(\"Plum\")");
                    }});




                }});


                //parte centrale
                panel(new PanelBuilder("ArmiEAzioni"){{
                    childLayoutHorizontal();
                    backgroundColor("#0000");
                    height("25%");
                    width("100%");

                    panel(new PanelBuilder("Armi"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("100%");
                        width("80%");

                        text(new TextBuilder("tarmi"){{
                            marginLeft("3%");
                            valignCenter();
                            text("Armi");
                            font("Interface/Fonts/Default.fnt");
                            backgroundColor("#0000");
                            color("#ffff");
                            height("30%");
                            width("15%");
                            onActiveEffect(new EffectBuilder("textSize"){{
                                effectParameter("endSize", "2");
                            }});
                        }});

                        control(new ButtonBuilder("Candeliere",""){{
                            name("button");
                            alignCenter();
                            valignCenter();
                            width("6%");
                            height("70%");
                            interactOnClick("addArma(\"Candeliere\")");
                        }});

                        panel(new PanelBuilder("separator7"){{
                            childLayoutHorizontal();
                            backgroundColor("#0000");
                            height("5%");
                            width("5%");
                        }});

                        control(new ButtonBuilder("Pugnale",""){{
                            name("button");
                            alignCenter();
                            valignCenter();
                            width("6%");
                            height("70%");
                            interactOnClick("addArma(\"Pugnale\")");
                        }});

                        panel(new PanelBuilder("separator8"){{
                            childLayoutHorizontal();
                            backgroundColor("#0000");
                            height("5%");
                            width("5%");
                        }});

                        control(new ButtonBuilder("Tubo di piombo",""){{
                            name("button");
                            alignCenter();
                            valignCenter();
                            width("6%");
                            height("70%");
                            interactOnClick("addArma(\"Tubo di piombo\")");

                        }});

                        panel(new PanelBuilder("separator9"){{
                            childLayoutHorizontal();
                            backgroundColor("#0000");
                            height("5%");
                            width("5%");
                        }});

                        control(new ButtonBuilder("Pistola",""){{
                            name("button");
                            alignCenter();
                            valignCenter();
                            width("6%");
                            height("70%");
                            interactOnClick("addArma(\"Pistola\")");
                        }});

                        panel(new PanelBuilder("separator10"){{
                            childLayoutHorizontal();
                            backgroundColor("#0000");
                            height("5%");
                            width("5%");
                        }});

                        control(new ButtonBuilder("Corda",""){{
                            name("button");
                            alignCenter();
                            valignCenter();
                            width("6%");
                            height("70%");
                            interactOnClick("addArma(\"Corda\")");
                        }});

                        panel(new PanelBuilder("separator11"){{
                            childLayoutHorizontal();
                            backgroundColor("#0000");
                            height("5%");
                            width("5%");
                        }});

                        control(new ButtonBuilder("Chiave inglese",""){{
                            name("button");
                            alignCenter();
                            valignCenter();
                            width("6%");
                            height("70%");
                            interactOnClick("addArma(\"Chiave inglese\")");
                        }});





                    }});


                    panel(new PanelBuilder("Azioni"){{
                        paddingLeft("10%");
                        childLayoutVertical();
                        backgroundColor("#0000");
                        height("100%");
                        width("20%");

                        control(new ButtonBuilder("effettuaPredizione","Invia predizione"){{
                            name("button");
                            alignCenter();
                            valignCenter();
                            width("85%");
                            height("30%");
                            interactOnClick("effettuaPredizione()");

                        }});

                        control(new ButtonBuilder("annullaPredizione","Annulla predizione"){{
                            name("button");
                            alignCenter();
                            valignCenter();
                            width("85%");
                            height("30%");

                        }});

                    }});

                }});


                panel(new PanelBuilder("separator12"){{
                    childLayoutHorizontal();
                    backgroundColor("#0000");
                    height("3%");
                    width("0.1%");
                }});

                //luoghi
                panel(new PanelBuilder("Luoghi"){{
                    childLayoutHorizontal();
                    backgroundColor("#0000");
                    height("40%");
                    width("100%");

                    text(new TextBuilder("tluoghi"){{
                        valignCenter();
                        text("Luoghi");
                        font("Interface/Fonts/Default.fnt");
                        backgroundColor("#0000");
                        color("#ffff");
                        height("30%");
                        width("15%");
                        onActiveEffect(new EffectBuilder("textSize"){{
                            effectParameter("endSize", "2");
                        }});
                    }});

                    control(new ButtonBuilder("Garage",""){{
                        name("button");
                        alignCenter();
                        valignTop();
                        width("4.8%");
                        height("44%");
                        interactOnClick("addLuogo(\"Garage\")");

                    }});

                    panel(new PanelBuilder("separator12"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("5%");
                        width("0.1%");
                    }});

                    control(new ButtonBuilder("Sala del biliardo",""){{
                        name("button");
                        alignCenter();
                        valignBottom();
                        width("4.8%");
                        height("44%");
                        interactOnClick("addLuogo(\"Sala del biliardo\")");

                    }});

                    panel(new PanelBuilder("separator13"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("5%");
                        width("0.1%");
                    }});

                    control(new ButtonBuilder("Camera da letto",""){{
                        name("button");
                        alignCenter();
                        valignTop();
                        width("5%");
                        height("44%");
                        interactOnClick("addLuogo(\"Camera da letto\")");

                    }});

                    panel(new PanelBuilder("separator14"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("5%");
                        width("0.1%");
                    }});

                    control(new ButtonBuilder("Sala da pranzo",""){{
                        name("button");
                        alignCenter();
                        valignBottom();
                        width("4.8%");
                        height("44%");
                        interactOnClick("addLuogo(\"Sala da pranzo\")");

                    }});

                    panel(new PanelBuilder("separator15"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("5%");
                        width("0.1%");
                    }});

                    control(new ButtonBuilder("Ingresso",""){{
                        name("button");
                        alignCenter();
                        valignTop();
                        width("4.8%");
                        height("44%");
                        interactOnClick("addLuogo(\"Ingresso\")");

                    }});

                    panel(new PanelBuilder("separator16"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("5%");
                        width("0.1%");
                    }});

                    control(new ButtonBuilder("Cucina",""){{
                        name("button");
                        alignCenter();
                        valignBottom();
                        width("4.8%");
                        height("44%");
                        interactOnClick("addLuogo(\"Cucina\")");
                        paddingLeft("3%");

                    }});

                    panel(new PanelBuilder("separator17"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("5%");
                        width("0.1%");
                    }});

                    control(new ButtonBuilder("Bagno",""){{
                        name("button");
                        alignCenter();
                        valignTop();
                        width("4.8%");
                        height("44%");
                        interactOnClick("addLuogo(\"Bagno\")");

                    }});

                    panel(new PanelBuilder("separator18"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("5%");
                        width("0.1%");
                    }});

                    control(new ButtonBuilder("Salotto",""){{
                        name("button");
                        alignCenter();
                        valignBottom();
                        width("4.8%");
                        height("44%");
                        interactOnClick("addLuogo(\"Salotto\")");

                    }});

                    panel(new PanelBuilder("separator19"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("5%");
                        width("0.1%");
                    }});

                    control(new ButtonBuilder("Studio",""){{
                        name("button");
                        alignCenter();
                        valignTop();
                        width("4.8%");
                        height("44%");
                        interactOnClick("addLuogo(\"Studio\")");

                    }});



                }});

            }});

            }}.build(nifty));
    }

    public void registrationScreen(){
        nifty.addScreen("registrationScreen", new ScreenBuilder("registrationScreen") {{
            controller(new GameController(client, nifty, cInfo, clientLogic));


            layer(new LayerBuilder("background") {{
                childLayoutVertical();
                backgroundColor("#0000");
                backgroundImage("Interface/Cluedo.png");
            }});

            layer(new LayerBuilder("foreground") {{
                childLayoutVertical();
                backgroundColor("#0000");

                panel(new PanelBuilder("panel_top"){{
                    alignRight();
                    childLayoutHorizontal();
                    backgroundColor("#0000");
                    height("15%");
                    width("12%");

                    control(new ButtonBuilder("LogButton","Login"){{
                        name("button");
                        alignLeft();
                        valignCenter();
                        width("70%");
                        height("50%");
                        interactOnClick("goToLogin()");
                    }});

                }});

                panel(new PanelBuilder("panel_top_bot"){{
                    childLayoutHorizontal();
                    backgroundColor("#0000");
                    height("30%");
                    width("20%");
                }});

                panel(new PanelBuilder("panel_body"){{
                    childLayoutVertical();
                    backgroundColor("#0000");
                    alignCenter();
                    height("45%");
                    width("35%");

                    panel(new PanelBuilder("panel_username"){{
                        childLayoutVertical();
                        backgroundColor("#0000");
                        alignCenter();
                        height("20%");
                        width("60%");

                        control(new TextFieldBuilder("usernameTextField", "") {{
                            maxLength(25);
                            width("100%");
                        }});

                    }});

                    panel(new PanelBuilder("panel_separator2"){{
                        childLayoutVertical();
                        backgroundColor("#0000");
                        alignCenter();
                        height("10%");
                        width("60%");
                    }});

                    panel(new PanelBuilder("panel_password"){{
                        childLayoutVertical();
                        backgroundColor("#0000");
                        alignCenter();
                        height("20%");
                        width("60%");

                        control(new TextFieldBuilder("passwordTextField", "") {{
                            maxLength(25);
                            width("100%");
                            passwordChar('*');

                        }});

                    }});

                    panel(new PanelBuilder("panel_separator2"){{
                        childLayoutVertical();
                        backgroundColor("#0000");
                        alignCenter();
                        height("15%");
                        width("35%");
                    }});

                    control(new ButtonBuilder("registrati","Registrati"){{
                        name("button");
                        alignCenter();
                        valignCenter();
                        width("50%");
                        height("20%");
                        interactOnClick("insertNewAccount()");
                    }});
                }});


                panel(new PanelBuilder("panel_bottom"){{
                    alignRight();
                    childLayoutVertical();
                    backgroundColor("#0000");
                    height("15%");
                    width("10%");

                    control(new ButtonBuilder("ExitButton","Esci"){{
                        name("button");
                        alignLeft();
                        valignCenter();
                        width("65%");
                        height("45%");
                        interactOnClick("exitGame()");
                    }});

                }});



            }});

        }}.build(nifty));
    }

    public void loginScreen(){

        //schermo di login
        nifty.addScreen("loginScreen", new ScreenBuilder("loginScreen") {{
            controller(new GameController(client, nifty, cInfo, clientLogic));

            layer(new LayerBuilder("background") {{
                childLayoutVertical();
                backgroundColor("#0000");
                backgroundImage("Interface/Cluedo.png");
            }});

            layer(new LayerBuilder("foreground") {{
                childLayoutVertical();
                backgroundColor("#0000");

                panel(new PanelBuilder("panel_top"){{
                    alignRight();
                    childLayoutHorizontal();
                    backgroundColor("#0000");
                    height("15%");
                    width("12%");

                    control(new ButtonBuilder("RegButton","Registrati"){{
                        name("button");
                        alignLeft();
                        valignCenter();
                        width("70%");
                        height("50%");
                        interactOnClick("goToRegistration()");
                    }});

                }});

                panel(new PanelBuilder("panel_top_bot"){{
                    childLayoutHorizontal();
                    backgroundColor("#0000");
                    height("30%");
                    width("20%");
                }});

                panel(new PanelBuilder("panel_body"){{
                    childLayoutVertical();
                    backgroundColor("#0000");
                    alignCenter();
                    height("45%");
                    width("35%");

                    panel(new PanelBuilder("panel_username"){{
                        childLayoutVertical();
                        backgroundColor("#0000");
                        alignCenter();
                        height("20%");
                        width("60%");

                        control(new TextFieldBuilder("usernameTextField", "") {{
                            maxLength(25);
                            width("100%");
                        }});

                    }});

                    panel(new PanelBuilder("panel_separator2"){{
                        childLayoutVertical();
                        backgroundColor("#0000");
                        alignCenter();
                        height("10%");
                        width("60%");
                    }});

                    panel(new PanelBuilder("panel_password"){{
                        childLayoutVertical();
                        backgroundColor("#0000");
                        alignCenter();
                        height("20%");
                        width("60%");

                        control(new TextFieldBuilder("passwordTextField", "") {{
                            maxLength(25);
                            width("100%");
                            passwordChar('*');

                        }});

                    }});

                    panel(new PanelBuilder("panel_separator2"){{
                        childLayoutVertical();
                        backgroundColor("#0000");
                        alignCenter();
                        height("15%");
                        width("35%");
                    }});

                    control(new ButtonBuilder("login","Login"){{
                        name("button");
                        alignCenter();
                        valignCenter();
                        width("50%");
                        height("20%");
                        interactOnClick("logToGame()");

                    }});
                }});


                panel(new PanelBuilder("panel_bottom"){{
                    alignRight();
                    childLayoutVertical();
                    backgroundColor("#0000");
                    height("15%");
                    width("10%");

                    control(new ButtonBuilder("ExitButton","Esci"){{
                        name("button");
                        alignLeft();
                        valignCenter();
                        width("65%");
                        height("45%");
                        interactOnClick("exitGame()");
                    }});

                }});



            }});

        }}.build(nifty));


    }

    public void home(){
        //schermo di start
        nifty.addScreen("home", new ScreenBuilder("home") {{
            controller(new GameController(client, nifty, cInfo, clientLogic)); //dichiaro il gamecontroller per l'interazione tra la gui e la logica
            //LAYER DI BACKGROUND
            layer(new LayerBuilder("background") {{
                childLayoutCenter();
                backgroundColor("#000f");
                image(new ImageBuilder(){{
                    filename("Interface/Cluedo.png");
                }});
            }});

            //LAYER DI FOREGROUND
            layer(new LayerBuilder("foreground") {{
                childLayoutVertical();
                backgroundColor("#0000");

                panel(new PanelBuilder("panel_top"){{
                    childLayoutVertical();
                    alignRight();
                    backgroundColor("#0000");
                    height("30%");
                    width("25%");

                    text(new TextBuilder("userName"){{
                        childLayoutHorizontal();
                        alignRight();
                        text("");
                        font("Interface/Fonts/Default.fnt");
                        backgroundColor("#0000");
                        color("#ffff");
                        height("50%");
                        width("70%");
                        onActiveEffect(new EffectBuilder("textSize"){{
                            effectParameter("endSize", "2");
                        }});

                    }});



                }});


                //PANNELLO DEI BOTTONI
                panel(new PanelBuilder("panel_button") {{
                    childLayoutVertical();
                    alignLeft();
                    backgroundColor("#0000");
                    height("40%");
                    width("55%");


                    //PANNELLO 1
                    panel(new PanelBuilder("panel_button_1") {{
                        childLayoutHorizontal();
                        alignRight();
                        backgroundColor("#0000");
                        height("30%");
                        width("70%");


                        //BOTTONE SETTINGS
                        control(new ButtonBuilder("StartButton", "Gioca online") {{
                            name("button");
                            alignLeft();
                            valignCenter();
                            width("70%");
                            height("100%");
                            interactOnClick("startScreen()");

                        }});



                    }});

                    panel(new PanelBuilder("separator") {{
                        backgroundColor("#0000");
                        height("20%");
                        width("20%");
                    }});


                    //PANNELLO 2
                    panel(new PanelBuilder("panel_button_2") {{
                        childLayoutHorizontal();
                        alignRight();
                        backgroundColor("#0000");
                        height("30%");
                        width("70%");

                        //BOTTONE SETTINGS
                        control(new ButtonBuilder("SettingButton", "Impostazioni") {{
                            name("button");
                            alignLeft();
                            valignCenter();
                            width("70%");
                            height("100%");

                        }});




                    }});

                    panel(new PanelBuilder("separator2") {{
                        backgroundColor("#0000");
                        height("20%");
                        width("20%");
                    }});

                    //PANNELLO 3
                    panel(new PanelBuilder("panel_button_3") {{
                        childLayoutHorizontal();
                        alignRight();
                        backgroundColor("#0000");
                        height("30%");
                        width("70%");

                        //BOTTONE EXIT
                        control(new ButtonBuilder("QuitButton", "Esci dal gioco") {{
                            name("button");
                            alignLeft();
                            valignCenter();
                            width("40%");
                            height("100%");
                            interactOnClick("exitGame()");

                        }});

                    }});


                }}); // panel added
            }});
            // layer added

        }}.build(nifty));

    }

    public void startGameScreen(){
        //HUD
        nifty.addScreen("startGameScreen", new ScreenBuilder("startGameScreen") {{
            controller(new GameController(client, nifty, cInfo, clientLogic));

            layer(new LayerBuilder("background") {{
                childLayoutCenter();
                backgroundColor("#0000");
                backgroundImage("Interface/CreateOrEnterGame.png");
            }});

            layer(new LayerBuilder("foreground") {{
                childLayoutVertical();
                backgroundColor("#0000");

                panel(new PanelBuilder("panel_title") {{
                    childLayoutVertical();
                    alignRight();
                    backgroundColor("#0000");
                    height("5%");
                    width("20%");
                }});


                panel(new PanelBuilder("panel_top") {{
                    childLayoutVertical();
                    alignRight();
                    backgroundColor("#0000");
                    height("35%");
                    width("20%");

                    control(new ButtonBuilder("menuButton","Menu"){{
                        name("button");
                        alignLeft();
                        valignCenter();
                        width("90%");
                        height("40%");
                        interactOnClick("returnToMenu()");

                    }});

                }});


                panel(new PanelBuilder("panel_body"){{
                    childLayoutHorizontal();
                    alignCenter();
                    backgroundColor("#0000");
                    height("40%");
                    width("80%");

                    // panel added
                    panel(new PanelBuilder("panel_left") {{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("100%");
                        width("40%");

                        //BOTTONE PER CREARE UNA LOBBY
                        control(new ButtonBuilder("CreateRoomButton", "Crea una stanza") {{
                            name("button");
                            alignLeft();
                            valignCenter();
                            width("100%");
                            height("100%");
                            interactOnClick("CreateLobby()");

                        }});


                    }});

                    panel(new PanelBuilder("panel_separator") {{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("100%");
                        width("20%");
                    }});

                    panel(new PanelBuilder("panel_right") {{
                        childLayoutHorizontal();
                        backgroundColor("#2222");
                        height("100%");
                        width("40%");


                        //BOTTONE PER ENTRARE IN UNA LOBBY
                        control(new ButtonBuilder("EnterRoomButton", "Entra in una stanza") {{
                            name("button");
                            alignRight();
                            valignCenter();
                            width("100%");
                            height("100%");
                            interactOnClick("EnterLobby()");

                        }});

                    }});

                }});

            }});
        }}.build(nifty));

    }

    public void lobbyScreen(){
        //schermo di lobby
        nifty.addScreen("lobbyscreen", new ScreenBuilder("lobbyscreen") {{
            controller(new GameController(client, nifty, cInfo, clientLogic));

            layer(new LayerBuilder("background") {{
                childLayoutCenter();
                backgroundColor("#0000");
                backgroundImage("Interface/lobby.png");
            }});

            layer(new LayerBuilder("foreground") {{
                childLayoutCenter();
                backgroundColor("#0000");

                panel(new PanelBuilder("panel_top"){{
                    childLayoutHorizontal();
                    backgroundColor("#0000");
                    height("20%");
                    width("20%");
                }});

                panel(new PanelBuilder("panel_body"){{
                    childLayoutVertical();
                    backgroundColor("#0000");
                    backgroundImage("Interface/Stanza.png");
                    height("60%");
                    width("70%");

                    panel(new PanelBuilder("body_top_1_line"){{
                        childLayoutVertical();
                        backgroundColor("#0000");
                        height("23%");
                        width("70%");
                    }});

                    //prima riga pannello utenti
                    panel(new PanelBuilder("body_1_line"){{
                            childLayoutHorizontal();
                            backgroundColor("#0000");
                            height("13%");
                            width("100%");

                        panel(new PanelBuilder("margin_left"){{
                            width("5%");
                        }});

                        text(new TextBuilder("user1"){{
                            font("Interface/Fonts/build.ttf");
                            width("36%");
                            height("40%");
                            color("#000f");
                            text("");
                            onActiveEffect(new EffectBuilder("textSize"){{
                                effectParameter("endSize", "2");
                            }});

                        }});

                        panel(new PanelBuilder("center"){{
                            width("18%");
                        }});

                        text(new TextBuilder("user2"){{
                            font("Interface/Fonts/build.ttf");
                            width("36%");
                            height("40%");
                            color("#000f");
                            text("");
                            onActiveEffect(new EffectBuilder("textSize"){{
                                effectParameter("endSize", "2");
                            }});
                        }});


                    }});

                    panel(new PanelBuilder("body_top_2_line"){{
                        childLayoutVertical();
                        backgroundColor("#0000");
                        height("13%");
                        width("70%");
                    }});

                    //prima riga pannello utenti
                    panel(new PanelBuilder("body_2_line"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("13%");
                        width("100%");

                        panel(new PanelBuilder("margin_left"){{
                            width("5%");
                        }});

                        text(new TextBuilder("user3"){{
                            font("Interface/Fonts/build.ttf");
                            width("36%");
                            height("40%");
                            color("#000f");
                            text("");
                            onActiveEffect(new EffectBuilder("textSize"){{
                                effectParameter("endSize", "2");
                            }});

                        }});

                        panel(new PanelBuilder("center"){{
                            width("18%");
                        }});

                        text(new TextBuilder("user4"){{
                            font("Interface/Fonts/build.ttf");
                            width("36%");
                            height("40%");
                            color("#000f");
                            text("");
                            onActiveEffect(new EffectBuilder("textSize"){{
                                effectParameter("endSize", "2");
                            }});
                        }});


                    }});

                    panel(new PanelBuilder("body_top_3_line"){{
                        childLayoutVertical();
                        backgroundColor("#0000");
                        height("14%");
                        width("70%");
                    }});

                    //prima riga pannello utenti
                    panel(new PanelBuilder("body_3_line"){{
                        childLayoutHorizontal();
                        backgroundColor("#0000");
                        height("13%");
                        width("100%");

                        panel(new PanelBuilder("margin_left"){{
                            width("5%");
                        }});

                        text(new TextBuilder("user5"){{
                            font("Interface/Fonts/build.ttf");
                            width("36%");
                            height("40%");
                            color("#000f");
                            text("");
                            onActiveEffect(new EffectBuilder("textSize"){{
                                effectParameter("endSize", "2");
                            }});

                        }});

                        panel(new PanelBuilder("center"){{
                            width("18%");
                        }});

                        text(new TextBuilder("user6"){{
                            font("Interface/Fonts/build.ttf");
                            width("36%");
                            height("40%");
                            color("#000f");
                            text("");
                            onActiveEffect(new EffectBuilder("textSize"){{
                                effectParameter("endSize", "2");
                            }});
                        }});


                    }});


                }});

                panel(new PanelBuilder("buttonSection"){{
                    height("100%");
                    width("60%");
                    childLayoutVertical();

                    panel(new PanelBuilder("spaceBorder"){{
                        height("78%");
                    }});

                    panel(new PanelBuilder("buttonBox"){{
                        valignCenter();
                        childLayoutHorizontal();
                        height("20%");
                        width("50%");

                        panel(new PanelBuilder("marginLeftPanel"){{
                            width("65%");
                        }});
                        //BOTTONE STARTGAME
                        control(new ButtonBuilder("StartGameButton", "Start Game") {{
                            valignCenter();
                            name("button");
                            alignLeft();
                            width("30%");
                            height("50%");
                            visible(false);
                            interactOnClick("startGame()");

                        }});

                        panel(new PanelBuilder("spaceBetweenButtons"){{
                            width("10%");
                        }});

                        //BOTTONE EXIT LOBBY
                        control(new ButtonBuilder("ExitLobbyButton", "Exit Lobby") {{
                            valignCenter();
                            name("button");
                            width("30%");
                            height("50%");

                        }});
                    }});


                }});






            }});

        }}.build(nifty));
    }


    public void gameScreen() {
        //schermo di lobby
        nifty.addScreen("Game", new ScreenBuilder("Game") {{
            controller(new GameController(client, nifty, cInfo, clientLogic));

            layer(new LayerBuilder("background") {{
                childLayoutCenter();
                backgroundColor("#0000");
            }});

            layer(new LayerBuilder("foreground") {{
                childLayoutHorizontal();
                backgroundColor("#0000");

                panel(new PanelBuilder("MainPanel"){{
                    childLayoutVertical();
                    width("65%");
                    height("100%");


                    panel(new PanelBuilder("TopIconPanel"){{
                        childLayoutHorizontal();
                        width("100%");
                        height("12%");

                        backgroundImage("Interface/Bar.png");

                        panel(new PanelBuilder("StartIcon"){{
                            childLayoutVertical();
                            width("7%");
                            height("100%");


                        }});

                        panel(new PanelBuilder("IconUser1"){{
                            childLayoutVertical();
                            width("8%");
                            height("80%");
                            backgroundImage("Interface/Characters/Green.png");
                            alignCenter();
                            valignCenter();
                        }});

                        panel(new PanelBuilder("SeparateIcon1"){{
                            childLayoutVertical();
                            width("7%");
                            height("100%");

                        }});

                        panel(new PanelBuilder("IconUser2"){{
                            childLayoutVertical();
                            width("8%");
                            height("80%");
                            backgroundImage("Interface/Characters/Mustard.png");
                            alignCenter();
                            valignCenter();
                        }});

                        panel(new PanelBuilder("SeparateIcon2"){{
                            childLayoutVertical();
                            width("7%");
                            height("100%");


                        }});

                        panel(new PanelBuilder("IconUser3"){{
                            childLayoutVertical();
                            width("8%");
                            height("80%");
                            backgroundImage("Interface/Characters/Peacock.png");
                            alignCenter();
                            valignCenter();
                        }});

                        panel(new PanelBuilder("SeparateIcon3"){{
                            childLayoutVertical();
                            width("7%");
                            height("90%");


                        }});

                        panel(new PanelBuilder("IconUser4"){{
                            childLayoutVertical();
                            width("8%");
                            height("80%");
                            backgroundImage("Interface/Characters/Plum.png");
                            alignCenter();
                            valignCenter();
                        }});

                        panel(new PanelBuilder("SeparateIcon4"){{
                            childLayoutVertical();
                            width("7%");
                            height("100%");


                        }});

                        panel(new PanelBuilder("IconUser5"){{
                            childLayoutVertical();
                            width("8%");
                            height("80%");
                            backgroundImage("Interface/Characters/Scarlett.png");
                            alignCenter();
                            valignCenter();
                        }});

                        panel(new PanelBuilder("SeparateIcon5"){{
                            childLayoutVertical();
                            width("7%");
                            height("100%");


                        }});

                        panel(new PanelBuilder("IconUser6"){{
                            childLayoutVertical();
                            width("8%");
                            height("80%");
                            backgroundImage("Interface/Characters/White.png");
                            alignCenter();
                            valignCenter();
                        }});

                        panel(new PanelBuilder("EndIcon"){{
                            childLayoutVertical();
                            width("7%");
                            height("100%");


                        }});


                    }});



                    panel(new PanelBuilder("MiddleMainPanel"){{
                        childLayoutHorizontal();
                        width("100%");
                        height("68%");
                        backgroundColor("#0000");

                        panel(new PanelBuilder("MiddleMainPanelSeparator1"){{
                            childLayoutVertical();
                            width("16%");
                            height("100%");
                            backgroundColor("#0000");

                        }});

                        panel(new PanelBuilder("Map"){{
                            childLayoutAbsoluteInside();
                            width("68%");
                            height("100%");
                            backgroundColor("#0000");
                            backgroundImage("Interface/mapp.jpg");


                            image(new ImageBuilder("p1"){{
                                width("4.8%");
                                height("4.8%");
                                x("32%");
                                y("71.25%");
                                filename("Interface/download.jpg");
                            }});

                            image(new ImageBuilder("p2"){{
                                width("4.8%");
                                height("4.8%");
                                x("32%");
                                y("71.25%");
                                filename("Interface/download.jpg");
                            }});

                            image(new ImageBuilder("p3"){{
                                width("4.8%");
                                height("4.8%");
                                x("32%");
                                y("71.25%");
                                filename("Interface/download.jpg");
                            }});

                            image(new ImageBuilder("p4"){{
                                width("4.8%");
                                height("4.8%");
                                x("32%");
                                y("71.25%");
                                filename("Interface/download.jpg");
                            }});

                            image(new ImageBuilder("p5"){{
                                width("4.8%");
                                height("4.8%");
                                x("32%");
                                y("71.25%");
                                filename("Interface/download.jpg");
                            }});

                            image(new ImageBuilder("p6"){{
                                width("4.8%");
                                height("4.8%");
                                x("32%");
                                y("71.25%");
                                filename("Interface/download.jpg");
                            }});



                        }});

                    }});



                    panel(new PanelBuilder("BottomActionPanel"){{
                        childLayoutHorizontal();
                        width("100%");
                        height("20%");
                        backgroundImage("Interface/Bar.png");

                        panel(new PanelBuilder("BottomActionMarginLeft"){{
                                childLayoutHorizontal();
                                width("5%");
                                height("100%");
                        }});

                        //BOTTONE TIRA DADI
                        control(new ButtonBuilder("LanciaDadi", "Lancia dadi") {{
                            valignCenter();
                            name("button");
                            width("20%");
                            height("50%");
                            interactOnClick("lanciaDadi()");
                        }});


                        panel(new PanelBuilder("CardsPanel"){{
                            childLayoutHorizontal();
                            width("50%");
                            height("100%");
                        }});


                        panel(new PanelBuilder("Actions"){{
                            childLayoutVertical();
                            width("20%");
                            height("100%");

                            panel(new PanelBuilder("MarginTopSeparator"){{
                                childLayoutHorizontal();
                                width("50%");
                                height("10%");
                            }});

                            //BOTTONE ESEGUI IPOTESI
                            control(new ButtonBuilder("Ipotesi", "Ipotesi") {{
                                valignCenter();
                                name("button");
                                width("100%");
                                height("30%");
                                interactOnClick("predizione()");
                            }});

                            panel(new PanelBuilder("SeparatorButton"){{
                                childLayoutHorizontal();
                                width("50%");
                                height("20%");
                            }});

                            //BOTTONE SOLUZIONE FINALE
                            control(new ButtonBuilder("Soluzione", "Soluzione") {{
                                valignCenter();
                                name("button");
                                width("100%");
                                height("30%");
                                interactOnClick("soluzione()");

                            }});

                        }});




                    }});


                }});

                panel(new PanelBuilder("NotePanel"){{
                    width("35%");
                    height("100%");
                    backgroundColor("#fff0");
                    backgroundImage("Interface/cluedo_notes.jpeg");

                }});



            }});


        }}.build(nifty));
    }


    private void loadControlDefinition() {
        new ControlDefinitionBuilder("button") {{
            controller("de.lessvoid.nifty.controls.button.ButtonControl");
            inputMapping("de.lessvoid.nifty.input.mapping.MenuInputMapping");
            backgroundColor(Color.BLACK);
            childLayoutCenter();
            visibleToMouse(true);

            onHoverEffect(new HoverEffectBuilder("border"){{
                color("#4242");
                post(true);
            }});

            panel(new PanelBuilder() {{
                focusable(true);
                text(new TextBuilder("#text") {{
                    color(Color.WHITE);
                    font("Interface/Fonts/Default.fnt");
                    onActiveEffect(new EffectBuilder("textSize"){{
                        effectParameter("endSize", "2");
                    }});
                    text(controlParameter("label"));
                }});
            }});
        }}.registerControlDefintion(nifty);



        //textField style
        new StyleBuilder() {{
            id("nifty-textfield#panel");
            childLayoutOverlay();
            height("80px");

            onHoverEffect(new HoverEffectBuilder("border"){{
                color("#000f");
                post(true);
            }});

            backgroundColor(Color.WHITE);
        }}.build(nifty);

        new StyleBuilder() {{
            id("nifty-textfield#field");
            childLayoutCenter();

            childClip(true);
            backgroundColor("#ffff");
            paddingLeft("5px");
            paddingTop("2px");
            visibleToMouse(false);


        }}.build(nifty);

        new StyleBuilder() {{
            id("nifty-textfield#text");
            visibleToMouse(false);
            color("#666f");
            selectionColor("#666f");
            alignCenter();
            valignCenter();
            textHAlignCenter();
            font("Interface/Fonts/Default.fnt");
            onActiveEffect(new EffectBuilder("textSize"){{
                effectValue("50");
                effectParameter("factor", "10");
                effectParameter("startSize", "1");
                effectParameter("endSize", "1");
                effectParameter("textSize", "1");
            }});

            onFocusEffect(new HoverEffectBuilder("textColor"){{
                color("#000f");
                post(true);
            }});

        }}.build(nifty);

    }

    private void registrationFailedPopUp(){
        new PopupBuilder("registrationFailedPopUp") {{
            childLayoutCenter();
            alignCenter();
            backgroundColor("#000a");
            // add the actual popup content here (panels, images, controls)
            panel(new PanelBuilder("panel"){{
                childLayoutVertical();
                backgroundColor("#0000");
                height("100%");
                width("100%");

                text(new TextBuilder("warning"){{
                    childLayoutHorizontal();
                    alignCenter();
                    text("Registrazione fallita!");
                    font("Interface/Fonts/Default.fnt");
                    backgroundColor("#0000");
                    color("#ffff");
                    height("50%");
                    width("70%");
                    onActiveEffect(new EffectBuilder("textSize"){{
                        effectParameter("endSize", "1");
                    }});

                }});

                control(new ButtonBuilder("closePopUpButton","Ok"){{
                    name("button");
                    alignCenter();
                    valignCenter();
                    width("70%");
                    height("50%");
                    interactOnClick("closePopUp(\"registrationFailedPopUp\")");
                }});

            }});


        }}.registerPopup(nifty);

    }

}
