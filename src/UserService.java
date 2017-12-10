public class UserService
{
    /**
     * Returns all user submitted documents from the database
     * @return a string representing the JSON of the database
     */
    public String getAllUsers() { return DatabaseHandler.i.GetAll(); }
}