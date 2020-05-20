package nejidev.api.database;

import nejidev.api.NejiAPI;
import org.json.JSONObject;

import java.io.IOException;

public class DatabaseUser {

    private final String username;
    private final String password;

    public DatabaseUser(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return password;
    }

    public static DatabaseUser createUser(String resourceFile) throws IOException {
        JSONObject o = new JSONObject(NejiAPI.read(resourceFile));
        return new DatabaseUser(o.getString("username"), o.getString("password"));
    }

}
