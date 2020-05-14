package nejidev.commands;

import nejidev.api.Banner;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import nejidev.banners.BannerType;
import nejidev.main.MainApplication;
import nejidev.main.NejiBot;
import nejidev.utils.CMDMessages;
import nejidev.utils.Reaction;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class ClearTagCommand extends CommandBase {

    public static final MessageEmbed.Field USAGE = new MessageEmbed.Field("Use:", "!clear <PL ou GE>", false);

    public ClearTagCommand(){
        super("clear");
    }

    public ClearTagCommand(String command) {
        super(command);
    }
    public boolean execute(ReceivedInfo receivedInfo) {
        if(receivedInfo.getArguments().length == 1){
            String tag = receivedInfo.getArguments()[0];
            if(tag.equalsIgnoreCase("pl")) {
                Banner plBanner = ((NejiBot)MainApplication.getBot()).getServerBanner(BannerType.PROGRAMMING_LANGUAGE);
                plBanner.clearMember(receivedInfo.getSender());
                send(receivedInfo, CMDMessages.DCM(
                        receivedInfo, "Linguagem de Programação", "#33CC66", "Você resetou suas linguagens.")).queue(msg -> react(msg, Reaction.ok()));
            }
            else if(tag.equalsIgnoreCase("ge")){
                Banner geBanner = ((NejiBot)MainApplication.getBot()).getServerBanner(BannerType.GAME_ENGINE);
                geBanner.clearMember(receivedInfo.getSender());
                send(receivedInfo, CMDMessages.DCM(receivedInfo, "Linguagem de Programação", "#1abc9c", "Você resetou suas linguagens.")).queue(msg -> react(msg, Reaction.ok()));
            }
        }
        else{
            send(receivedInfo, CMDMessages.DCM(receivedInfo, "Você inseriu o comando errado.", USAGE)).queue(msg -> react(msg, Reaction.denied()));
        }
        return true;
    }

}
