package nejidev.commands;

import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import nejidev.api.commands.miscs.Category;
import nejidev.api.commands.miscs.CommandCategory;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

@CommandCategory(category = Category.SERVER)
public class VersionCommand extends CommandBase {

    public VersionCommand() {
        super("version");
    }

    public VersionCommand(String command) {
        super(command);
    }

    public boolean execute(ReceivedInfo receivedInfo) {

        EmbedBuilder builder = new EmbedBuilder().setFooter("Minha versão atual é \"" +NejiAPI.VERSION+"\".", NejiAPI.getServerGuild().getIconUrl()).setColor(Color.magenta);
        sendEmbed(receivedInfo, builder).queue();

        return true;
    }
}