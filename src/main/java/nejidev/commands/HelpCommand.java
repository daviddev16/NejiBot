package nejidev.commands;

import nejidev.api.emotes.EmoteServerType;
import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;

public class HelpCommand extends CommandBase {

    public HelpCommand(){
        super("help");
    }

    public HelpCommand(String command){
        super(command);
    }

    public boolean execute(ReceivedInfo receivedInfo) {

        send(receivedInfo, NejiAPI.buildMsg(receivedInfo, "Ajuda", "#8DFF33", "Caso você esteja interessado em ver os comandos do bot, " +
                "entre em \"https://github.com/daviddev16/NejiBot/wiki/Help\" la você poderá ver os comandos com mais clareza.")).queue(msg -> react(msg, NejiAPI.getEmote(EmoteServerType.OK)));

        return true;
    }
}
