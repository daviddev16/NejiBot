package nejidev.main;

import nejidev.api.Banner;
import nejidev.api.Bot;
import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandManager;
import nejidev.api.BannerType;
import nejidev.banners.GameEngineBanner;
import nejidev.banners.ProgrammingLanguageBanner;
import nejidev.commands.ClearTagCommand;
import nejidev.commands.WelcomeLogCommand;
import nejidev.events.WelcomeListener;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class NejiBot extends Bot {

    public static final String BOT_TOKEN = "<token do bot>";

    private static CommandManager commandManager;

    public ProgrammingLanguageBanner programmingLanguageBanner;

    public GameEngineBanner gameEngineBanner;

    public NejiBot(){
        super(BOT_TOKEN);
    }

    private NejiBot(String token) {
        super(token);
    }

    public void onLoad(JDABuilder builder) throws LoginException, InterruptedException  {

        commandManager = new CommandManager();
        commandManager.attachListener(builder);

        builder.addEventListeners(new WelcomeListener());
    }

    public void onConnected() {

        System.out.println("connected.");

        programmingLanguageBanner = new ProgrammingLanguageBanner();
        addBanner(programmingLanguageBanner);

        gameEngineBanner = new GameEngineBanner();
        addBanner(gameEngineBanner);

        NejiAPI.registerCommand(new ClearTagCommand());
        NejiAPI.registerCommand(new WelcomeLogCommand());
    }



    public String getName(){
        return "Ajudante Nejizin";
    }

    public static CommandManager getCommandManager(){
        return commandManager;
    }


}
