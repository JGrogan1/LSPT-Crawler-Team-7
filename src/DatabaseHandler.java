import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.DBCursor;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class DatabaseHandler {

    public static DatabaseHandler i = null;

    public MongoClient client;
    MongoDatabase db;
    MongoCollection collection;
    /**
     * Attempt to create a new Singleton Database Handler, opening the Pages Database
     */
    DatabaseHandler() {
        if(i != null){
            return;
        }
        i = this;
        client = new MongoClient("localhost", 27017);
        db = client.getDatabase("WebPages");
        collection = db.getCollection("Pages");
    }
    /**
     *  Attempts to add the specified page to the Database, returning whether the page
     *  was added succesfully
     *
     * @param  page TO BE DETERMINED
     * @return      True if the entry was added, False if the entry exists previously
     */
    boolean AddDocument(String page){
        if(collection.find(eq("_id",page)).first() != null){
            return false;
        }

        //Perform all necessary
        Document doc = new Document("_id", page);
        doc.append("session", "123");
        collection.insertOne(doc);

        return true;
    }

    String GetSession(String sessionGUID){
        MongoCursor c = collection.find(eq("session", sessionGUID)).iterator();
        List<String> jsonData = new LinkedList<>();
        while(c.hasNext()){
            jsonData.add(c.next().toString());
        }
        System.out.println(jsonData.toString());
        return jsonData.toString();
    }

}
