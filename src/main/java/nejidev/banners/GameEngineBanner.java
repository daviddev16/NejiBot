package nejidev.banners;

import nejidev.api.banners.Banner;
import nejidev.api.NejiAPI;
import nejidev.api.banners.ReactionRole;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

import javax.annotation.Nonnull;

public class GameEngineBanner extends Banner {

    public GameEngineBanner(){ super(710057046417997874L);}

    public GameEngineBanner(long messageId) {
        super(messageId);
    }

    public boolean onAwake() {

        setTextChannelId(NejiAPI.REGISTER_CHANNEL_ID);
        addReactionRoles(new ReactionRole("Godot", "707782962019565608"));
        addReactionRoles(new ReactionRole("Unity", "707782656162529340"));
        addReactionRoles(new ReactionRole("Unreal", "707782892750897172"));

        return true;
    }

    public void onAdded() { }
}
