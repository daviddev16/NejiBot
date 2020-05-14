package nejidev.api.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public abstract class CommandBase {

    /*Prefixo usado pelo usuario para chamar o comando*/
    public static final String COMMAND_PREFIX = "!";

    private final String command;

    public CommandBase(String command){
        this.command = command;
    }

    /*verificar se o comando bate com as informações recebidas*/
    public boolean accept(ReceivedInfo receivedInfo) {
        return getCommand().equalsIgnoreCase(receivedInfo.getCommand());
    }

    /*executor do comando*/
    public abstract boolean execute(ReceivedInfo receivedInfo);

    public String getCommand(){
        return this.command;
    }

    public MessageAction send(ReceivedInfo info, Message msg){
        return info.getReceiverEvent().getTextChannel().sendMessage(msg);
    }

    public MessageAction send(ReceivedInfo info, EmbedBuilder builder){
        return info.getReceiverEvent().getTextChannel().sendMessage(builder.build());
    }

    public void react(Message message, Emote emote){
        message.addReaction(emote).queue();
    }

}
