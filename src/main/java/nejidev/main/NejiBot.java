package nejidev.main;

import nejidev.api.NejiAPI;
import nejidev.api.app.Bot;
import nejidev.api.commands.CommandManager;
import nejidev.api.tag.Tag;
import nejidev.api.tag.TagManager;
import nejidev.banners.GameEngineBanner;
import nejidev.banners.ProgrammingLanguageBanner;
import nejidev.commands.*;
import nejidev.events.MessageListener;
import nejidev.events.WelcomeListener;
import nejidev.tags.*;
import nejidev.tags.issues.OpenIssueTagEvent;

import java.io.IOException;

public class NejiBot extends Bot {

    private static CommandManager commandManager;

    private static TagManager tagManager;

    public ProgrammingLanguageBanner programmingLanguageBanner;

    public GameEngineBanner gameEngineBanner;

    public NejiBot() throws IOException {
        super(NejiAPI.read(".token").trim());
    }
    private NejiBot(String token) {
        super(token);
    }

    public void onConnected() {

        getJavaDiscordAPI().addEventListener(new WelcomeListener(), new MessageListener());

        tagManager = new TagManager();
        tagManager.attachListener(this);

        commandManager = new CommandManager();
        commandManager.attachListener(this);

        programmingLanguageBanner = new ProgrammingLanguageBanner();
        addBanner(programmingLanguageBanner);

        gameEngineBanner = new GameEngineBanner();
        addBanner(gameEngineBanner);

        registerCommands();
        registerTags();

        NejiAPI.setupActivityUpdater();

    }

    public void registerCommands(){
        NejiAPI.registerCommand(new ClearTagCommand());
        NejiAPI.registerCommand(new HelpCommand());
        NejiAPI.registerCommand(new CountCommand());
        NejiAPI.registerCommand(new AvatarCommand());
        NejiAPI.registerCommand(new MuteCommand());
        NejiAPI.registerCommand(new DesmuteCommand());
        NejiAPI.registerCommand(new CloseIssueCommand());
        NejiAPI.registerCommand(new RankCommand());
        NejiAPI.registerCommand(new ClearChatCommand());
    }

    public void registerTags(){
        NejiAPI.registerTag(Tag.createTag("feedback", FeedbackTagEvent::new));
        NejiAPI.registerTag(Tag.createTag("houses", HouseTagEvent::new));
        NejiAPI.registerTag(Tag.createTag("reactions", FacebookReactionsTagEvent::new));
        NejiAPI.registerTag(Tag.createTag("pass", ModeTagEvent::new));
        NejiAPI.registerTag(Tag.createTag("sicko", SickoTagEvent::new));
        NejiAPI.registerTag(Tag.createTag("issue", OpenIssueTagEvent::new));
        NejiAPI.registerTag(Tag.createTag("vote", VoteTagEvent::new));
        NejiAPI.registerTag(Tag.createTag("option", OptionTagEvent::new));

    }

    public String getName(){
        return "Ajudante Nejizin";
    }

    public static CommandManager getCommandManager(){
        return commandManager;
    }

    public static TagManager getTagManager() { return tagManager; }


}
