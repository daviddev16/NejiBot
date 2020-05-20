package nejidev.commands;

import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import nejidev.api.commands.miscs.Category;
import nejidev.api.commands.miscs.CommandCategory;
import nejidev.api.database.NejiDatabase;
import nejidev.api.emotes.EmoteServerType;
import nejidev.utils.Ranks;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;

@CommandCategory(category = Category.FUN)
public class RankCommand extends CommandBase {

    public RankCommand() {
        super("rank");
    }

    public RankCommand(String command) {
        super(command);
    }

    public boolean execute(ReceivedInfo ri) {

        Member rankMember = ri.getSender();

        if (!ri.getMentions().isEmpty()) {
            rankMember = ri.getMentions().get(0);
        }

        int issues = NejiDatabase.getIssuesFrom(rankMember);

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Rank de " + rankMember.getUser().getName())
                .setDescription("Rank mostra a contribuição que os desenvolvedores tem no servidor!")
                .setColor(Color.decode("#77dd91"))
                .setThumbnail("https://i.imgur.com/ORJyHHl.png")
                .addField("Issues fechadas: ",Integer.toString(issues),true)
                .addField("Experiencia: ", Ranks.get(issues), true)
                .setFooter("Placar baseado nas ultimas atividades do usuario");

        sendEmbed(ri, builder).queue(msg -> NejiAPI.quickReact(msg, NejiAPI.getEmote(EmoteServerType.UP_VOTE)));
        return true;
    }
}