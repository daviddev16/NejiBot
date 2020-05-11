package Main;

import Events.RegisterListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class MainApplication {


    private static volatile JDA singleJDA;

    public static void main(String[] args) throws LoginException {

        JDABuilder builder = JDABuilder.createDefault(Manager.BOT_TOKEN);
        Manager.setup(builder);
        singleJDA = builder.build();
        singleJDA.addEventListener(new RegisterListener());

    }

    public static synchronized JDA getJDA(){
        return singleJDA;
    }

}
