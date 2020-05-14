package nejidev.api;

import nejidev.main.MainApplication;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

import java.util.Objects;

public class ReactionRole {

    private final String emoteName;
    private final String roleId;

    public ReactionRole(String emoteName, String roleId){
        this.roleId = roleId;
        this.emoteName = emoteName;
    }
    public String getEmoteName(){
        return this.emoteName;
    }

    public String getRoleId() {
        return roleId;
    }

    public boolean hasTag(Member member){
        for(Role roles : member.getRoles()) {
            if (roleId.equals(roles.getId())) {
                return true;
            }
        }
        return false;
    }

    public synchronized AuditableRestAction<Void> addMember(Member member){
        if(!hasTag(member)){
            Guild guild = MainApplication.getBot().getOfficialGuild();
            return guild.addRoleToMember(member, Objects.requireNonNull(guild.getRoleById(getRoleId())));
        }
        return null;
    }

    public synchronized AuditableRestAction<Void> removeMember(Member member){
        if(hasTag(member)) {
            Guild guild = MainApplication.getBot().getOfficialGuild();
            return guild.removeRoleFromMember(member, Objects.requireNonNull(guild.getRoleById(getRoleId())));
        }
        return null;
    }
}
