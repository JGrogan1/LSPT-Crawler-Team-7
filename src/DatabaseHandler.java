import com.mongodb.*;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.print.Doc;
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
     * @param  lai  Link Analysis Info class to be stored in the database
     * @return      True if the entry was added, False if the entry exists previously
     */
    boolean AddDocument(LinkAnalysisInfo lai){
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
        List<LinkAnalysisInfo> lais = new LinkedList<>();
        LinkAnalysisInfo lai;
        while(c.hasNext()){
            lai = new LinkAnalysisInfo((Document)c.next());
            lais.add(lai);
        }
        JSONArray jsonData = new JSONArray(lais);
        System.out.println("GetAll: " + jsonData.toString());
        return jsonData.toString();
    }

}
