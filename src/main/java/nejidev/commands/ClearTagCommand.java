package nejidev.commands;

import nejidev.api.commands.miscs.Category;
import nejidev.api.commands.miscs.CommandCategory;
import nejidev.api.emotes.EmoteServerType;
import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import nejidev.banners.BannerType;
import net.dv8tion.jda.api.entities.MessageEmbed;

@CommandCategory(category = Category.FUNDAMENTAL)
public class ClearTagCommand extends CommandBase {

    public static final MessageEmbed.Field USAGE = new MessageEmbed.Field("Use:", "!clear <PL ou GE>", false);

    public ClearTagCommand(){
        super("clear");
    }

    public ClearTagCommand(String command) {
        super(command);
    }

    public boolean execute(ReceivedInfo ri) {

        if(checkArgs(ri.getArguments(), 1)){

            if(ri.getArguments()[0].equalsIgnoreCase("pl")) {

                NejiAPI.getServerBanner(BannerType.PROGRAMMING_LANGUAGE).clearMember(ri.getSender());
                send(ri, NejiAPI.buildMsg(ri, "Linguagem de Programação", "#33CC66", "Você resetou suas linguagens.")).queue(msg -> react(msg, NejiAPI.getEmote(EmoteServerType.OK)));

            }
            else if(ri.getArguments()[0].equalsIgnoreCase("ge")){

                NejiAPI.getServerBanner(BannerType.GAME_ENGINE).clearMember(ri.getSender());
                send(ri, NejiAPI.buildMsg(ri, "Linguagem de Programação", "#1abc9c", "Você resetou suas linguagens.")).queue(msg -> react(msg, NejiAPI.getEmote(EmoteServerType.OK)));

            }
            return true;
        }
        else{
            send(ri, NejiAPI.buildMsg(ri, "Você inseriu o comando errado.", USAGE)).queue(msg -> react(msg, NejiAPI.getEmote(EmoteServerType.DENIED)));
            return false;
        }
    }

}
