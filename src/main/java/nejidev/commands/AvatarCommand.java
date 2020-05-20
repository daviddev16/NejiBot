package nejidev.commands;

import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import nejidev.api.commands.miscs.Category;
import nejidev.api.commands.miscs.CommandCategory;
import nejidev.api.emotes.EmoteServerType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

@CommandCategory(category = Category.FUN)
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
            sendError(ri, USAGE);
            return false;
        }
        Member mentionedMember = ri.getMentions().get(0);

        EmbedBuilder builder = new EmbedBuilder()
        .setImage(mentionedMember.getUser().getAvatarUrl())
        .setTitle("Avatar de " + mentionedMember.getUser().getName())
                .setColor(Color.magenta);

        sendEmbed(ri, builder).queue(msg -> NejiAPI.quickReact(msg, NejiAPI.getEmote(EmoteServerType.OK)));
        return true;
    }
}