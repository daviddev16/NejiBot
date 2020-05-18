package nejidev.commands;

import nejidev.api.EmoteServerType;
import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;

public class CountCommand extends CommandBase {

    public CountCommand() {
        super("count");
    }

    public CountCommand(String command) {
        super(command);
    }

    public boolean execute(ReceivedInfo receivedInfo) {

        int countOfMembers = receivedInfo.getSender().getGuild().getMemberCount();
        send(receivedInfo, NejiAPI.buildMsg(receivedInfo, "Contador", "#884EA0", "O servidor possui atualmente " +
                "" + countOfMembers + ". Aumente o numero de membros chamando seus amigos para o servidor!")).queue(msg -> react(msg, NejiAPI.getEmote(EmoteServerType.OK)));

        return true;
    }
}