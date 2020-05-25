package nejidev.api.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import nejidev.api.commands.CommandBase;
import net.dv8tion.jda.api.entities.Member;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class NejiDatabase {

    public static String ISSUES_KEY = "issues";
    public static String RANK_KEY = "rank";
    public static String DISCORD_ID_KEY = "discordId";

    public static String ISSUES_COLLECTION_NAME = "issues";
    public static String RANK_COLLECTION_NAME = "rank";

    public static String SUCCESSFULLY_ISSUE_UPDATE_LOG = "[DB INFO]: \"%s\" [%s] foi atualizado no campo de issues com successo.";
    public static String SUCCESSFULLY_RANK_UPDATE_LOG = "[DB INFO]: \"%s\" [%s] foi atualizado no campo de rank com successo.";

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

        MongoCollection<Document> collection;
        Document oldMemberDocument;

        switch (updateType){

            case ISSUE:

                collection = getSingleton().getDatabase().getCollection(ISSUES_COLLECTION_NAME);

                oldMemberDocument = queryMember(collection, member);

                if(oldMemberDocument == null){
                    collection.insertOne(createDefaultIssueDocument(member));
                    oldMemberDocument = queryMember(collection, member);
                }

                Objects.requireNonNull(oldMemberDocument);

                int countIssues = oldMemberDocument.getInteger(ISSUES_KEY);

                updateMemberData(member, collection, ISSUES_KEY, (countIssues+1), updateResult -> {
                    if(updateResult.wasAcknowledged() && updateResult.getModifiedCount() > 0){
                        System.out.println(String.format(SUCCESSFULLY_ISSUE_UPDATE_LOG, member.getIdLong(), member.getUser().getName()));
                    }
                });

                break;

            case RANK:

                if(!CommandBase.checkArgs(params, 1)){
                    break;
                }

                collection = getSingleton().getDatabase().getCollection(RANK_COLLECTION_NAME);

                oldMemberDocument = queryMember(collection, member);

                if(oldMemberDocument == null){
                    collection.insertOne(createDefaultRankDocument(member));
                    oldMemberDocument = queryMember(collection, member);
                }

                Objects.requireNonNull(oldMemberDocument);

                int currentAmount = oldMemberDocument.getInteger(RANK_KEY);
                int addAmount = Integer.parseInt(params[0].toString());

                updateMemberData(member, collection, RANK_KEY, (currentAmount+addAmount), updateResult -> {
                    if(updateResult.wasAcknowledged() && updateResult.getModifiedCount() > 0){
                        System.out.println(String.format(SUCCESSFULLY_RANK_UPDATE_LOG, member.getIdLong(), member.getUser().getName()));
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

        MongoCollection<Document> collection;
        Document memberDocument;

        switch (type) {

            case ISSUE:

                collection = getSingleton().getDatabase().getCollection(ISSUES_COLLECTION_NAME);
                memberDocument = queryMember(collection, member);

                if(memberDocument == null){
                    objectConsumer.accept(null);
                    break;
                }

                objectConsumer.accept(memberDocument.get(key));
                break;

            case RANK:
                collection = getSingleton().getDatabase().getCollection(RANK_COLLECTION_NAME);
                memberDocument = queryMember(collection, member);

                if(memberDocument == null){
                    objectConsumer.accept(null);
                    break;
                }

                objectConsumer.accept(memberDocument.get(key));
                break;
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

    private static Document createDefaultRankDocument(@NotNull Member member){
        return createDefaultDocument(member).append(RANK_KEY, 0);
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
