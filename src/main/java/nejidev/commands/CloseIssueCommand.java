package nejidev.commands;

import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import nejidev.api.commands.miscs.CommandCategory;
import nejidev.api.database.MemberElementType;
import nejidev.api.database.NejiDatabase;
import nejidev.api.emotes.EmoteServerType;
import nejidev.tags.issues.OpenIssueTagEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Duration;

@CommandCategory(category = nejidev.api.commands.miscs.Category.SERVER)
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
            Message issueMessage = null;
            try {
                issueMessage = NejiAPI.findIssue(id);
            }catch (Exception ignored){
            }
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
            issueMessage.clearReactions(NejiAPI.getEmote(EmoteServerType.OPENED)).queue();
            issueMessage.addReaction(NejiAPI.getEmote(EmoteServerType.CLOSED)).queue();

            NejiDatabase.updateMember(ri.getSender(), MemberElementType.ISSUE);

            Category issueCategory = NejiAPI.getServerGuild().getCategoryById(NejiAPI.ISSUES_CATEGORY_ID);

            if(issueCategory != null) {
                String issueId = issueMessage.getId();
                issueCategory.getTextChannels().stream().filter(textChannel -> textChannel.getName().equalsIgnoreCase(issueId)).findFirst().ifPresent(textChannel -> {
                    textChannel.delete().queue();
                });
            }

            return true;
        }
        else{
            sendError(ri, USAGE);
            return false;
        }

    }
}