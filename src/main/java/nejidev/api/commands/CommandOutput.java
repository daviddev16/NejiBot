package nejidev.api.commands;

import nejidev.api.NejiAPI;
import nejidev.api.emotes.EmoteServerType;
import nejidev.api.listeners.IOutput;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.time.Duration;

public class CommandOutput implements IOutput<ReceivedInfo> {

    private MessageAction send(ReceivedInfo info, EmbedBuilder builder) {
        return info.getReceiverEvent().getTextChannel().sendMessage(builder.build());
    }

    @Override
    public void sendSuccess(ReceivedInfo ri, String title, String hexadecimalColor, String message) {
        send(ri, NejiAPI.buildMsg(ri, title, hexadecimalColor, message))
                .queue(msg -> NejiAPI.quickReact(msg, NejiAPI.getEmote(EmoteServerType.OK)));
    }

    @Override
    public MessageAction sendEmbed(ReceivedInfo info, EmbedBuilder embed) {
        return send(info, embed);
    }

    @Override
    public MessageAction sendSimple(ReceivedInfo info, String simpleMessage) {
        return info.getReceiverEvent().getTextChannel().sendMessage(simpleMessage);
    }

    @Override
    public void sendError(ReceivedInfo info, MessageEmbed.Field usage) {
        NejiAPI.sendTemporaryMessage(NejiAPI.buildMsg(info, "Você inseriu o comando errado.", usage).build(), info.getReceiverEvent().getTextChannel(), Duration.ofSeconds(10L), NejiAPI.getEmote(EmoteServerType.DENIED));
    }
}
