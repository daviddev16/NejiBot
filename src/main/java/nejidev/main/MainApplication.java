package nejidev.main;

import nejidev.api.Bot;
import javax.security.auth.login.LoginException;
import java.io.IOException;

public class MainApplication {

    /*id do servidor*/
    public static long SERVER_ID = 707778877493215364L;

    private static Bot bot;

    public static void main(String[] args) throws LoginException, InterruptedException {
        bot = new NejiBot().load(SERVER_ID);
    }

    public static Bot getBot(){
        return bot;
    }

}
