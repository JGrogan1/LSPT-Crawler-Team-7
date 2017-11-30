import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import org.bson.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DBTester {
    public static void main(String[] args){
        DatabaseHandler db = new DatabaseHandler();
        db.collection.drop(); //Reset the table so we can have a fresh test
        db.AddDocument("HelloPage");
        db.AddDocument("HelpPage");
        Document myDoc =  (Document)db.collection.find(eq("session", "123")).first();
        db.GetSession("123");
        System.out.println(db.collection.count());
    }
}
