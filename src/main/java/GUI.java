import com.jme3.network.Client;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.Menu;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.tools.Color;
import de.lessvoid.nifty.tools.SizeValue;

//classe grafica della graphical user interface
public class GUI {
    Nifty nifty;
    Client client;
    ClientInformation cInfo;

    public GUI(Nifty nifty, Client client, ClientInformation cInfo){
        this.nifty = nifty;
        this.client = client;
        this.cInfo = cInfo;
        initScreen();

    }

    public void initScreen(){
        loadControlDefinition();
        registrationScreen();
        loginScreen();
        home();
        startGameScreen();
        lobbyScreen();
        registrationFailedPopUp();
    }

    public void registrationScreen(){
        nifty.addScreen("registrationScreen", new ScreenBuilder("registrationScreen") {{
            controller(new GameController(client, nifty, cInfo));


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
            controller(new GameController(client, nifty, cInfo));

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
            controller(new GameController(client, nifty, cInfo)); //dichiaro il gamecontroller per l'interazione tra la gui e la logica
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
                            interactOnClick("startGame()");

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
            controller(new GameController(client, nifty, cInfo));

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
            controller(new GameController(client, nifty, cInfo));

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
                            font("aurulent-sans-16.fnt");
                            width("36%");
                            height("40%");
                            color("#000f");
                            text("Hello World!");
                            onActiveEffect(new EffectBuilder("textSize"){{
                                effectParameter("endSize", "4");
                            }});

                        }});

                        panel(new PanelBuilder("center"){{
                            width("18%");
                        }});

                        text(new TextBuilder("user2"){{
                            font("aurulent-sans-16.fnt");
                            width("36%");
                            height("40%");
                            color("#000f");
                            text("Hello World!");
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
                            font("aurulent-sans-16.fnt");
                            width("36%");
                            height("40%");
                            color("#000f");
                            text("Hello World!");

                        }});

                        panel(new PanelBuilder("center"){{
                            width("18%");
                        }});

                        text(new TextBuilder("user4"){{
                            font("aurulent-sans-16.fnt");
                            width("36%");
                            height("40%");
                            color("#000f");
                            text("Hello World!");
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
                            font("aurulent-sans-16.fnt");
                            width("36%");
                            height("40%");
                            color("#000f");
                            text("Hello World!");

                        }});

                        panel(new PanelBuilder("center"){{
                            width("18%");
                        }});

                        text(new TextBuilder("user6"){{
                            font("aurulent-sans-16.fnt");
                            width("36%");
                            height("40%");
                            color("#000f");
                            text("Hello World!");
                        }});


                    }});
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
