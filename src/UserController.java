import org.json.JSONObject;
import org.json.JSONTokener;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.LinkedList;
import java.util.Queue;

import static spark.Spark.post;

public class UserController
{

    static UserController i;
    private static Queue<JSONObject> post_queue = new LinkedList<JSONObject>();

    public JSONObject GetQueueObject()
    {
        return post_queue.poll();
    }

    public UserController(final UserService userService)
    {
        if(i != null)
        {
            return;
        }
        i = this;

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