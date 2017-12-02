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
    private static Queue<JSONArray> post_queue = new LinkedList<JSONArray>();

    UserController(){
            if(i != null){
                return;
            }
            i = this;
    }


    public UserController(final UserService userService) {

        post("/post", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                JSONTokener tokener = new JSONTokener(request.body());
                JSONArray root = new JSONArray(tokener);
                System.out.println("Received Post: " + root.toString());
                post_queue.add(root);
                return "ack";
            }
        });
    }
}