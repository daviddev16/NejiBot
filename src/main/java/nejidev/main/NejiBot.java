package nejidev.main;

import nejidev.api.Bot;
import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandManager;
import nejidev.banners.GameEngineBanner;
import nejidev.banners.ProgrammingLanguageBanner;
import nejidev.commands.AvatarCommand;
import nejidev.commands.ClearTagCommand;
import nejidev.commands.CountCommand;
import nejidev.commands.HelpCommand;
import nejidev.events.WelcomeListener;
import net.dv8tion.jda.api.entities.Activity;

import java.util.TimerTask;

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

        NejiAPI.setupActivityUpdater();
    }

    public String getName(){
        return "Ajudante Nejizin";
    }

    public static CommandManager getCommandManager(){
        return commandManager;
    }


}
