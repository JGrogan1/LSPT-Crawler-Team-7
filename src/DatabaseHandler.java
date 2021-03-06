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

class DatabaseHandler
{

    public static DatabaseHandler i = null;

    public MongoClient client;
    MongoDatabase db;
    MongoCollection collection;
    /**
     * Attempt to create a new Singleton Database Handler, opening the Pages Database
     */
    DatabaseHandler()
    {
        if(i != null)
        {
            client = i.client;
            db = i.db;
            collection = i.collection;
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
    boolean AddDocument(DatabaseInfo lai)
    {
        if(lai == null) return false;
        JSONObject json = new JSONObject(lai);
        if(collection.find(eq("_id",json.get("link"))).first() != null)
        {
            return false;
        }
        Document doc = Document.parse( json.toString() );

        collection.insertOne(doc);

        return true;
    }

}
