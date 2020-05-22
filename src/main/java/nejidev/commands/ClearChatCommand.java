package nejidev.commands;

import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import nejidev.api.commands.miscs.Category;
import nejidev.api.commands.miscs.CommandCategory;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandCategory(category = Category.ADMIN)
public class ClearChatCommand extends CommandBase {

    public ClearChatCommand(){
        super("cc");
    }

    public ClearChatCommand(String name){
        super(name);
    }

    @Override
    public boolean execute(ReceivedInfo ri) {

        if (!NejiAPI.Permissions.checkMasterPermissions(ri.getSender())) return false;

        MessageReceivedEvent event = ri.getReceiverEvent();
        event.getChannel().getHistory().retrievePast(100).queue(messages -> {
            messages.forEach(messageFromBot -> {
                event.getChannel().deleteMessageById(messageFromBot.getIdLong()).queue();
            });
            sendSimple(ri, "Chat Limpo!").queue();
        });

        return true;
    }
}
