package nejidev.main;

import nejidev.api.Bot;

import javax.security.auth.login.LoginException;

public class MainApplication {


    public static final String BOT_TOKEN = "NzA3Nzg2MzUzOTQwNjI3NDY2.XrbuMw.Zo0GwJsQnzxKGrdD7bwR1lnd6_k";

    public static long SERVER_ID = 707778877493215364L;

    private static Bot bot;

    public static void main(String[] args) throws LoginException {
        bot = new NejiBot().load();
    }

    public static Bot getBot(){
        return bot;
    }


}
