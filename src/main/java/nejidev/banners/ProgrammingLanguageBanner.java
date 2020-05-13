package nejidev.banners;

import nejidev.api.Banner;
import nejidev.api.ReactionRole;

public class ProgrammingLanguageBanner extends Banner {

    public static final long REGISTER_CHANNEL_ID = 708748306049663067L;

    public ProgrammingLanguageBanner(){
        super(710027372354732044L );
    }

    private ProgrammingLanguageBanner(long messageId) {
        super(messageId);
    }

    public Banner displayBanner() {
        setBannerPath("/banner_register.png");
        super.displayBanner();
        return this;
    }

    public boolean onAwake() {

        setTextChannelId(REGISTER_CHANNEL_ID);
        addReactionRoles(new ReactionRole("CSharp", "707782080377126962"));
        addReactionRoles(new ReactionRole("CPP", "707782500012916776"));
        addReactionRoles(new ReactionRole("JavaScript", "707782421055275018"));
        addReactionRoles(new ReactionRole("Python", "707782275533897778"));
        addReactionRoles(new ReactionRole("Java", "707782228608024627"));
        addReactionRoles(new ReactionRole("Ruby", "707783759054897184"));

        return true;
    }

    public void onAdded() {}
}
