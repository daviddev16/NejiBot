package nejidev.commands;

import nejidev.api.commands.miscs.Category;
import nejidev.api.commands.miscs.CommandCategory;
import nejidev.api.emotes.EmoteServerType;
import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.util.Objects;

@CommandCategory(category = Category.ADMIN)
public class MuteCommand extends CommandBase {

    public static final MessageEmbed.Field USAGE = new MessageEmbed.Field("Use:", "!mute @<member>", false);

    public MuteCommand(){
        super("mute");
    }

    public MuteCommand(String name){
        super(name);
    }

    @Override
    public boolean execute(ReceivedInfo ri) {

        if (!NejiAPI.Permissions.checkMasterPermissions(ri.getSender())) return false;

        if (ri.getMentions().isEmpty()) {
            sendError(ri, USAGE);
            return false;
        }
        Member mentionedMember = ri.getMentions().get(0);

        Role roleSilenciado = NejiAPI.getServerGuild().getRoleById(NejiAPI.Permissions.SILENCIADO);
        Objects.requireNonNull(roleSilenciado);
        NejiAPI.getServerGuild().addRoleToMember(mentionedMember, roleSilenciado).queue();


        /*message*/
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Usuario Punido por " + ri.getSender().getUser().getName())
                .setDescription("O usuario " + mentionedMember.getUser().getName() + " foi silenciado de falar no chat.")
                .setThumbnail(ri.getSender().getUser().getAvatarUrl())
                .setColor(Color.decode("#FFAA00"))
                .setFooter("Comando executado pelo " + NejiAPI.getSelfName(), ri.getSender().getUser().getAvatarUrl());

        sendEmbed(ri, builder).queue(msg -> NejiAPI.quickReact(msg, NejiAPI.getEmote(EmoteServerType.OK)));
        return true;
    }
}
