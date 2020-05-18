package nejidev.main;

import nejidev.api.Bot;
import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandManager;
import nejidev.banners.GameEngineBanner;
import nejidev.banners.ProgrammingLanguageBanner;
import nejidev.commands.*;
import nejidev.events.WelcomeListener;

public class NejiBot extends Bot {

    public static final String BOT_TOKEN = "token";

    private static CommandManager commandManager;

    public ProgrammingLanguageBanner programmingLanguageBanner;

    public GameEngineBanner gameEngineBanner;

    public NejiBot(){
        super(BOT_TOKEN);
    }

    private NejiBot(String token) {
        super(token);
    }

    public void onConnected() {

        System.out.println("connected.");

        getJavaDiscordAPI().addEventListener(new WelcomeListener());

        commandManager = new CommandManager();
        commandManager.attachListener(this);

        programmingLanguageBanner = new ProgrammingLanguageBanner();
        addBanner(programmingLanguageBanner);

        gameEngineBanner = new GameEngineBanner();
        addBanner(gameEngineBanner);

        NejiAPI.registerCommand(new ClearTagCommand());
        NejiAPI.registerCommand(new HelpCommand());
        NejiAPI.registerCommand(new CountCommand());
        NejiAPI.registerCommand(new AvatarCommand());
        NejiAPI.registerCommand(new MuteCommand());
        NejiAPI.registerCommand(new DesmuteCommand());

        NejiAPI.setupActivityUpdater();
    }

    public String getName(){
        return "Ajudante Nejizin";
    }

    public static CommandManager getCommandManager(){
        return commandManager;
    }


}
