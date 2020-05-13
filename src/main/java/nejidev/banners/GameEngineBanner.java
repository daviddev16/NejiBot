package nejidev.banners;

import nejidev.api.Banner;

public class GameEngineBanner extends Banner {

    /*amanha faço esse banner*/
    public GameEngineBanner(){ super(-1);}

    public GameEngineBanner(long messageId) {
        super(messageId);
    }

    public boolean onAwake() {
        return true;
    }

    @Override
    public void onAdded() {

    }
}
