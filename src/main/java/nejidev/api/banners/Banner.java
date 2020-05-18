package nejidev.api.banners;

import nejidev.api.NejiAPI;
import nejidev.api.utils.Utils;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.*;

public abstract class Banner extends ListenerAdapter {

    /*id da mensagem do banner*/
    private long messageId;

    /*id do canal que o banner vai ficar*/
    private long textChannelId;

    private List<ReactionRole> reactionRoles;

    /*caminho do InputStream da foto do banner*/
    private String bannerPath;

    /*Guild do servidor*/
    private Guild guild;

    /**
     * Esta classe permite você criar banners personalizados
     * em forma de mensagem que exibirá reações, e essas reações
     * irão servir para dar algum cargo a pessoa que reagiu.
    * */
    public Banner(long messageId){
        this.setMessageId(messageId);
        this.setReactionRoles(new ArrayList<>());
    }

    /*Esta função será chamada quando o banner for adicionado ao bot*/
    public abstract boolean onAwake();

    /*Esta função será chamada quando o banner já estiver adicionado ao bot sem erros.*/
    public abstract void onAdded();

    /*Caso ainda não exista a mensagem, use esta função para criar a mensagem personalizadwa*/
    @Deprecated
    public Banner displayBanner(){

        TextChannel channel = NejiAPI.getServerTextChannel(textChannelId);

        assert channel != null;
        channel.sendFile(requireNonNull(Utils.getInputStream(bannerPath)), "banner.png").queue(message -> {

            for(ReactionRole reactionRoles : getReactionRoles())
                message.addReaction(NejiAPI.getServerEmoteByRole(reactionRoles)).queue();

        });

        return this;
    }

    /*limpar cargos do membro*/
    public void clearMember(Member member){

        TextChannel channel = NejiAPI.getServerTextChannel(textChannelId);

        assert channel != null;

        for(ReactionRole roles : getReactionRoles()){

            channel.removeReactionById(messageId, NejiAPI.getServerEmote(roles.getEmoteName()), member.getUser()).queue();
            AuditableRestAction<Void> action = roles.removeMember(member);
            if(action != null) {
                action.queue();
            }

        }
    }

    private ReactionRole findReactionRole(Emote emote){
        for(ReactionRole roles : getReactionRoles()){
            if(emote.getName().equalsIgnoreCase(roles.getEmoteName())){
                return roles;
            }
        }
        throw new NullPointerException("Não foi possivel achar o ReactionRole.");
    }

    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {

        if (requireNonNull(event.getUser()).isBot()) {
            return;
        }
        if ((event.getChannel().getIdLong() == getTextChannelId()) &&
                event.getMessageId().equalsIgnoreCase(Long.toString(getMessageId()))) {

            ReactionRole foundRole = findReactionRole(event.getReactionEmote().getEmote());
            requireNonNull(foundRole);
            addMemberToReactionRole(foundRole, event.getMember());
        }
    }

    /*adicionar role ao jogador*/
    private void addMemberToReactionRole(ReactionRole role, Member member){
        AuditableRestAction<Void>  action = role.addMember(member);
        if(action != null) {
            action.queue();
        }
    }

    /*adicionar role por reação*/
    public void addReactionRoles(ReactionRole reactionRole){
        getReactionRoles().add(reactionRole);
    }

    public List<ReactionRole> getReactionRoles(){
        return this.reactionRoles;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getTextChannelId() {
        return textChannelId;
    }

    public void setTextChannelId(long textChannelId) {
        this.textChannelId = textChannelId;
    }

    public void setReactionRoles(List<ReactionRole> reactionRoles) {
        this.reactionRoles = reactionRoles;
    }

    public void setBannerPath(String bannerPath){
        this.bannerPath = bannerPath;
    }


}
