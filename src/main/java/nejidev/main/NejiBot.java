package nejidev.main;

import nejidev.api.app.Bot;
import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandManager;
import nejidev.api.tag.Tag;
import nejidev.api.tag.TagManager;
import nejidev.tags.FBReactionsTagEvent;
import nejidev.tags.FeedbackTagEvent;
import nejidev.banners.GameEngineBanner;
import nejidev.banners.ProgrammingLanguageBanner;
import nejidev.commands.*;
import nejidev.events.WelcomeListener;
import nejidev.tags.HouseTagEvent;
import nejidev.tags.ModeTagEvent;

import javax.annotation.Nullable;

public class NejiBot extends Bot {

    @Nullable
    public static final String BOT_TOKEN = "token";

    private static CommandManager commandManager;

    private static TagManager tagManager;

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

        tagManager = new TagManager();
        tagManager.attachListener(this);

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

        NejiAPI.registerTag(Tag.createTag("feedback", FeedbackTagEvent::new));
        NejiAPI.registerTag(Tag.createTag("houses", HouseTagEvent::new));
        NejiAPI.registerTag(Tag.createTag("reactions", FBReactionsTagEvent::new));
        NejiAPI.registerTag(Tag.createTag("pass", ModeTagEvent::new));

        NejiAPI.setupActivityUpdater();
    }

    public String getName(){
        return "Ajudante Nejizin";
    }

    public static CommandManager getCommandManager(){
        return commandManager;
    }

    public static TagManager getTagManager() { return tagManager; }


}
