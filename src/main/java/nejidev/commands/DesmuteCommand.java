package nejidev.commands;

import nejidev.api.commands.miscs.Category;
import nejidev.api.commands.miscs.CommandCategory;
import nejidev.api.emotes.EmoteServerType;
import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.util.Objects;

@CommandCategory(category = Category.ADMIN)
public class DesmuteCommand extends CommandBase {

    public static final MessageEmbed.Field USAGE = new MessageEmbed.Field("Use:", "!desmute @<member>", false);

    public DesmuteCommand(){
        super("desmute");
    }

    public DesmuteCommand(String name){
        super(name);
    }

    @Override
    public boolean execute(ReceivedInfo ri) {

        if(!checkPermission(ri, NejiAPI.MESTRE, NejiAPI.ADMIN)) return false;

        if(checkArgs(ri.getArguments(), 1)) {

            Role roleSilenciado = NejiAPI.getServerGuild().getRoleById(NejiAPI.SILENCIADO);
            Objects.requireNonNull(roleSilenciado);
            NejiAPI.getServerGuild().removeRoleFromMember(ri.getMentions().get(0), roleSilenciado).queue();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Usuario desmutado por " + ri.getSender().getUser().getName());
            builder.setDescription("O usuario " + ri.getMentions().get(0).getUser().getName() + " foi desmutado de falar no chat.");
            builder.setThumbnail(ri.getSender().getUser().getAvatarUrl());
            builder.setColor(Color.decode("#AA00AA"));
            builder.setFooter("Comando executado pelo "  + NejiAPI.getSelfName(), ri.getSender().getUser().getAvatarUrl());
            send(ri, builder).queue(msg -> react(msg, NejiAPI.getEmote(EmoteServerType.OK)));

            return true;

        }else{
            send(ri, NejiAPI.buildMsg(ri, "Você inseriu o comando errado.", USAGE)).queue(msg -> react(msg, NejiAPI.getEmote(EmoteServerType.DENIED)));
            return false;
        }
    }
}
