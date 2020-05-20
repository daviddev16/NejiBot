package nejidev.api.database;

import com.mongodb.client.*;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class NejiDatabase {

    private final DatabaseUser databaseUser;

    private final MongoDatabase database;

    private final MongoCollection<Document> usersCollection;

    private static NejiDatabase singleton;

    public NejiDatabase(DatabaseUser databaseUser, String name){
        this.databaseUser = databaseUser;

        MongoClient mongoClient = null;

        try {
            mongoClient = MongoClients.create(
                    "mongodb+srv://" + databaseUser.getUsername() + ":" + databaseUser.getPassword() + "@nejidevcluster-utwuw.gcp.mongodb.net/test?retryWrites=true&w=majority");

            Runtime.getRuntime().addShutdownHook(new Thread(mongoClient::close));

        } catch (Exception e){
            e.printStackTrace();

        }finally {

            Objects.requireNonNull(mongoClient);
            database = mongoClient.getDatabase(name);
            usersCollection = database.getCollection("users");
        }
        singleton = this;
    }

    public static void addMember(@NotNull Member member){

        if(hasMember(member)) return;

        Document document = new Document();
        document.put("discordId", member.getUser().getIdLong());
        insertDocument(document);
    }

    public static boolean hasMember(@NotNull Member member){
        try (MongoCursor<Document> cursor = getSingleton().getUsersCollection().find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                if (document.containsKey("discordId") && document.getLong("discordId") == member.getUser().getIdLong()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void insertDocument(Document document){
        getSingleton().getUsersCollection().insertOne(document);
    }

    public MongoDatabase getDatabase(){
        return database;
    }

    public MongoCollection<Document> getUsersCollection(){
        return usersCollection;
    }

    public DatabaseUser getDatabaseUser(){
        return databaseUser;
    }

    public static NejiDatabase getSingleton(){
        return singleton;
    }


}
