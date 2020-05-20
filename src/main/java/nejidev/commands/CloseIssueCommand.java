package nejidev.commands;

import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import nejidev.api.commands.miscs.Category;
import nejidev.api.commands.miscs.CommandCategory;
import nejidev.api.emotes.EmoteServerType;
import nejidev.banners.BannerType;
import nejidev.tags.issues.OpenIssueTagEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageReaction;

import java.awt.*;
import java.time.Duration;
import java.util.Objects;

@CommandCategory(category = Category.SERVER)
public class CloseIssueCommand extends CommandBase {

    public static final MessageEmbed.Field USAGE = new MessageEmbed.Field("Use:", "!close <issue id>", false);


    public CloseIssueCommand() {
        super("close");
    }

    public CloseIssueCommand(String command) {
        super(command);
    }

    public boolean execute(ReceivedInfo ri) {

        if(checkArgs(ri.getArguments(), 1)){

            String id = ri.getArguments()[0].trim();
            Message issueMessage = NejiAPI.findIssue(id);

            if(issueMessage == null){
                sendSimple(ri, "Essa issue não existe.");
                return false;
            }

            if(issueMessage.getReactions().stream().noneMatch(reaction -> reaction.getReactionEmote().getName().equalsIgnoreCase("open"))){

                NejiAPI.sendTemporaryMessage(
                        new EmbedBuilder()
                                .setColor(Color.green)
                                .setTitle("Aviso")
                                .setDescription("Essa issue já esta fechada!")
                                .setThumbnail(OpenIssueTagEvent.issueIconURL)
                                .build(),
                        ri.getReceiverEvent().getTextChannel(),
                        Duration.ofSeconds(10L)
                );

                return true;
            }

            issueMessage.removeReaction(NejiAPI.getEmote(EmoteServerType.OPENED)).queue();
            issueMessage.addReaction(NejiAPI.getEmote(EmoteServerType.CLOSED)).queue();

            return true;
        }
        else{
            sendError(ri, USAGE);
            return false;
        }

    }
}