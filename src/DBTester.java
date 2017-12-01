import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import java.util.logging.Level;
import org.bson.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DBTester {

    public static void main(String[] args){
        UserController uc = new UserController(new UserService());

        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        DatabaseHandler db = new DatabaseHandler();
        db.collection.drop(); //Reset the table so we can have a fresh test
        db.AddDocument("HelloPage");
        db.AddDocument("HelpPage");

        System.out.println(db.collection.count());
    }
}
