package nejidev.api.database;

import com.mongodb.client.*;
import org.bson.Document;

public class Connection {

    public static void connect(){

        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://nejibotuser:TAlWuH5HcwyJ9Pch@nejidevcluster-utwuw.gcp.mongodb.net/test?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("nejibotdb");
        MongoCollection collection = database.getCollection("users");

        Document dc = new Document();

        dc.put("aaoao","bbb");

        collection.insertOne(dc);

        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

    }

}
