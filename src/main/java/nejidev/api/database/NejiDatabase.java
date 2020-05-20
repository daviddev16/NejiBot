package nejidev.api.database;

import com.mongodb.client.*;
import net.dv8tion.jda.api.entities.Member;
import org.bson.Document;
import org.bson.conversions.Bson;
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

    public static void checkAndAddMember(@NotNull Member member){

        if(hasMember(member)) return;

        Document document = new Document();
        document.put("discordId", member.getUser().getIdLong());
        document.put("issues", 0);
        insertDocument(document);
    }

    public static void addIssue(@NotNull Member member){
        checkAndAddMember(member);
        Document oldMemberDocument = queryMember(member);

        assert oldMemberDocument != null;

        Document newMemberDocument = new Document();

        newMemberDocument.put("discordId", oldMemberDocument.getLong("discordId"));
        newMemberDocument.put("issues", oldMemberDocument.getInteger("issues")+1);

        getSingleton().getUsersCollection().updateOne(filter(member), newSet(newMemberDocument));
    }

    private static Bson filter(@NotNull  Member member){
        return new Document("discordId", member.getUser().getIdLong());
    }

    private static Bson newSet(Document newValues){
        Document setDocument = new Document();
        setDocument.put("$set", newValues);
        return setDocument;
    }

    public static Integer getIssuesFrom(Member member){
        checkAndAddMember(member);
        Document memberDocument = queryMember(member);
        assert memberDocument != null;
        return memberDocument.getInteger("issues");
    }

    private static Document queryMember(@NotNull Member member){
        try (MongoCursor<Document> cursor = getSingleton().getUsersCollection().find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                if (document.containsKey("discordId") &&
                        document.getLong("discordId") == member.getUser().getIdLong()) {
                    return document;
                }
            }
        }
        return null;
    }

    private static boolean hasMember(@NotNull Member member){
        return queryMember(member) != null;
    }

    private static void insertDocument(Document document){
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
