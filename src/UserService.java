import java.util.LinkedList;
import java.util.List;

public class UserService {
    // returns a list of all users
    public String getAllUsers() { return DatabaseHandler.i.GetAll(); }
    // returns a single user by id
    public String getUser(String id) { return DatabaseHandler.i.GetSession(id); }
}