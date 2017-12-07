import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.post;
import java.util.*;

public class UserController {

    static UserController i;
    private static Queue<JSONObject> post_queue = new LinkedList<JSONObject>();

    UserController(){
            if(i != null){
                return;
            }
            i = this;
    }

    public JSONObject GetQueueObject(){
        return post_queue.remove();
    }

    public UserController(final UserService userService) {

        post("/post", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                JSONTokener tokener = new JSONTokener(request.body());
                JSONObject root = new JSONObject(tokener);
                System.out.println("Received Post: " + root.toString());
                post_queue.add(root);
                return "ack";
            }
        });
    }
}