package nejidev.commands;

import nejidev.api.emotes.EmoteServerType;
import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class AvatarCommand extends CommandBase {

    public static final MessageEmbed.Field USAGE = new MessageEmbed.Field("Use:", "!avatar <@member>", false);

    public AvatarCommand() {
        super("avatar");
    }

    public AvatarCommand(String command) {
        super(command);
    }

    public boolean execute(ReceivedInfo ri) {

        if (ri.getMentions().isEmpty()) {
            send(ri, NejiAPI.buildMsg(ri, "Você inseriu o comando errado.", USAGE)).queue(msg -> react(msg, NejiAPI.getEmote(EmoteServerType.DENIED)));
            return false;
        }
        Member mentionedMember = ri.getMentions().get(0);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setImage(mentionedMember.getUser().getAvatarUrl());
        builder.setTitle("Avatar de " + mentionedMember.getUser().getName());
        send(ri, builder).queue(msg -> react(msg, NejiAPI.getEmote(EmoteServerType.OK)));
        return true;
    }
}