package nejidev.tags.issues;

import nejidev.api.NejiAPI;
import nejidev.api.emotes.EmoteServerType;
import nejidev.api.listeners.ITagEvent;
import nejidev.api.tag.TagQueryResult;
import nejidev.api.utils.Logs;
import nejidev.api.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

public class OpenIssueTagEvent implements ITagEvent {

    public static String issueIconURL = "https://i.imgur.com/Vv1Q4Zl.png";

    @Override
    public void onTaggedMessageEvent(@Nullable Message message, @NotNull TagQueryResult tagQueryResult) {

        assert message != null;

        if(!NejiAPI.Permissions.checkIssuesPermission(message.getMember())) return;

        if(message.getTextChannel().getIdLong() != NejiAPI.ISSUE_CHANNEL_ID){
            NejiAPI.sendTemporaryMessage(
                    new EmbedBuilder()
                            .setColor(Color.red)
                            .setTitle("Aviso")
                            .setDescription("Você não pode marcar uma issue nesse canal")
                            .setThumbnail(issueIconURL)
                    .build(),
                    message.getTextChannel(),
                    Duration.ofSeconds(5L)
            );
            return;
        }

        NejiAPI.sendTemporaryMessage(
                new EmbedBuilder()
                        .setColor(Color.magenta)
                        .setTitle("Você criou uma issue!")
                        .appendDescription("Você receberá o codigo da sua issue no privado.")
                        .setThumbnail(issueIconURL)
                        .build(),
                message.getTextChannel(),
                Duration.ofSeconds(10L)
        );

        Objects.requireNonNull(message.getMember());
        message.getMember().getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(new EmbedBuilder()

                .setColor(Color.magenta)
                .setTitle("Issue Aberta")
                .setDescription("O Id da sua issue é" + '\n')
                .appendDescription("```"+message.getId()+"```"+ '\n')
                .appendDescription("Não apague a mensagem, isso resultará na perda da issue.")
                .setFooter("Esse ID será utilizado para fechar a issue.")
                .setThumbnail(issueIconURL)
                .build()

        ).queue(msg -> msg.addReaction(NejiAPI.getEmote(EmoteServerType.OK)).queue()));
        message.addReaction(NejiAPI.getEmote(EmoteServerType.OPENED)).queue();

        Objects.requireNonNull(NejiAPI.getServerGuild().getCategoryById(NejiAPI.ISSUES_CATEGORY_ID)).createTextChannel(message.getId()).queue(textChannel -> {
            textChannel.sendMessage(message).queue(msg -> NejiAPI.quickReact(msg, NejiAPI.getEmote(EmoteServerType.LINK)));

            message.getAttachments().forEach(attachment -> {

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {

                    ImageIO.write((RenderedImage) Utils.openImage(attachment.getUrl()), "png", bos);
                    bos.flush();
                    byte [] data = bos.toByteArray();
                    bos.close();
                    textChannel.sendFile(data, "attached.png").queue(msg -> NejiAPI.quickReact(msg, NejiAPI.getEmote(EmoteServerType.LINK)));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        Logs.send("A issue \"" + message.getIdLong() + "\" foi aberta por " + message.getAuthor().getName() + " !").queue();

    }
}
