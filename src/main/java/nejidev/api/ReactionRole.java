package nejidev.api;

import nejidev.main.MainApplication;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

import java.util.Objects;

public class ReactionRole {

    private String emoteName;
    private String roleId;

    public ReactionRole(String emoteName, String roleId){
        this.setRoleId(roleId);
        this.setEmoteName(emoteName);
    }
    public String getEmoteName(){
        return this.emoteName;
    }

    public void setEmoteName(String emoteName){
        this.emoteName = emoteName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public synchronized boolean isMine(Emote emote){
        return emote.getName().equals(getEmoteName());
    }

    public synchronized boolean hasTag(Member member){
        for(Role roles : member.getRoles()) {
            if (roleId.equals(roles.getId())) {
                return true;
            }
        }
        return false;

    }

    public synchronized AuditableRestAction<Void> addMember(Member member){
        if(!hasTag(member)){
            return MainApplication.getBot().getOfficialGuild().addRoleToMember(member, Objects.requireNonNull(MainApplication.getBot().getOfficialGuild().getRoleById(getRoleId())));
        }
        return null;
    }

    public synchronized AuditableRestAction<Void> removeMember(Member member){
        if(hasTag(member)) {
            return MainApplication.getBot().getOfficialGuild().removeRoleFromMember(member, Objects.requireNonNull(MainApplication.getBot().getOfficialGuild().getRoleById(roleId)));
        }
        return null;
    }
}
