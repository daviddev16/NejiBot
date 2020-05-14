package nejidev.banners;

import nejidev.api.Banner;
import nejidev.api.NejiAPI;
import nejidev.api.ReactionRole;
import nejidev.main.NejiBot;

public class GameEngineBanner extends Banner {

    /*amanha faço esse banner*/
    public GameEngineBanner(){ super(710057046417997874L);}

    public GameEngineBanner(long messageId) {
        super(messageId);
    }

    public Banner displayBanner() {
        setBannerPath("/banner_register1.png");
        super.displayBanner();
        return this;
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
