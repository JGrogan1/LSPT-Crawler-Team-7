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

    UserController()
    {
            if(i != null)
            {
                return;
            }
            i = this;
    }

    /**
     * Returns the JSONObject at the front of the received Posts queue
     *
     * @return JSONObject containing the front item, or NULL if the queue is empty
     */
    public JSONObject GetQueueObject()
    {
        return post_queue.poll();
    }


    /**
     * Receive posts at the specified URL, all posts are converted to JSON and added to the Database
     * returns an acknowledgment to the User
     */
    public UserController(final UserService userService)
    {
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