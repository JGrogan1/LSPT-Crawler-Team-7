import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

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
     * @param  lai  Link Analysis Info class to be stored in the database
     * @return      True if the entry was added, False if the entry exists previously
     */
    boolean AddDocument(DatabaseInfo lai){
        JSONObject json = new JSONObject(lai);
        System.out.println("Added Document: " + json.toString());
        if(collection.find(eq("_id",json.get("link"))).first() != null){
            return false;
        }
        Document doc = Document.parse( json.toString() );

        collection.insertOne(doc);

        return true;
    }

    String GetAll(){
        MongoCursor c = collection.find().iterator();
        List<DatabaseInfo> lais = new LinkedList<>();
        DatabaseInfo lai;
        while(c.hasNext()){
            lai = new DatabaseInfo((Document)c.next());
            lais.add(lai);
        }
        JSONArray jsonData = new JSONArray(lais);
        System.out.println("GetAll: " + jsonData.toString());
        return jsonData.toString();
    }

}
