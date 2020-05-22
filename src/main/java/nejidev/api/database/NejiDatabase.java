package nejidev.api.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import net.dv8tion.jda.api.entities.Member;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class NejiDatabase {

    public static String ISSUES_KEY = "issues";
    public static String DISCORD_ID_KEY = "discordId";

    public static String ISSUES_COLLECTION_NAME = "issues";

    public static String SUCCESSFULLY_ISSUE_UPDATE_LOG = "[DB INFO]: \"%s\" [%s] foi atualizado no campo de issues com successo.";

    private final DatabaseUser databaseUser;
    private final MongoDatabase database;
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
        }
        singleton = this;
    }

    public static void updateMember(@NotNull Member member, MemberElementType updateType, Object... params){

        switch (updateType){

            case ISSUE:

                MongoCollection<Document> issuesCollection = getSingleton().getDatabase().getCollection(ISSUES_COLLECTION_NAME);

                Document oldMemberDocument = queryMember(issuesCollection, member);

                if(oldMemberDocument == null){
                    issuesCollection.insertOne(createDefaultIssueDocument(member));
                    oldMemberDocument = queryMember(issuesCollection, member);
                }

                Objects.requireNonNull(oldMemberDocument);

                int countIssues = oldMemberDocument.getInteger(ISSUES_KEY);

                updateMemberData(member, issuesCollection, ISSUES_KEY, (countIssues+1), updateResult -> {
                    if(updateResult.wasAcknowledged() && updateResult.getModifiedCount() > 0){
                        System.out.println(String.format(SUCCESSFULLY_ISSUE_UPDATE_LOG, member.getIdLong(), member.getUser().getName()));
                    }
                });

                break;
        }

    }

    public static Object commonGet(@NotNull Member member, MemberElementType type, String key){
        AtomicReference<Object> o = new AtomicReference<>();
        get(member, type, key, o::set);
        return o.get();
    }

    public static void get(@NotNull Member member, MemberElementType type, String key, Consumer<Object> objectConsumer) {
        switch (type) {

            case ISSUE:

                MongoCollection<Document> issuesCollection = getSingleton().getDatabase().getCollection(ISSUES_COLLECTION_NAME);
                Document memberDocument = queryMember(issuesCollection, member);

                if(memberDocument == null){
                    objectConsumer.accept(null);
                    break;
                }

                objectConsumer.accept(memberDocument.get(key));
        }
    }

    private static void updateMemberData(@NotNull Member member, MongoCollection<Document> collection, String key, Object updatedValue, Consumer<UpdateResult> updateResultConsumer){
        updateResultConsumer.accept(collection.updateOne(filterMember(member), newSet(update(key, updatedValue))));

    }

    public static Document queryMember(MongoCollection<Document> collection, @NotNull  Member member){
        return collection.find(filterMember(member)).first();
    }


    private static Document update(String key, Object newValue){
        return new Document(key, newValue);
    }

    private static Document newSet(Document updateDocument){
        return new Document("$set", updateDocument);
    }

    private static Document filterMember(Member member){
        return new Document(DISCORD_ID_KEY, member.getUser().getIdLong());
    }

    private static Document createDefaultIssueDocument(@NotNull Member member){
        return createDefaultDocument(member).append(ISSUES_KEY, 0);
    }

    private static Document createDefaultDocument(@NotNull Member member){
        return new Document().append(DISCORD_ID_KEY, member.getIdLong());
    }

    public MongoDatabase getDatabase(){
        return database;
    }

    public DatabaseUser getDatabaseUser(){
        return databaseUser;
    }

    public static NejiDatabase getSingleton(){
        return singleton;
    }


}
