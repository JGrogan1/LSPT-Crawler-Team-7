public class UserService
{
    // returns a list of all users
    public String getAllUsers() { return DatabaseHandler.i.GetAll(); }
}