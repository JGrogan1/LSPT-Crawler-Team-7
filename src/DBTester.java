import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

public class DBTester {

    public static void main(String[] args){
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        UserController uc = new UserController(new UserService()); //Receives Post requests
        RestClient rc = new RestClient(); //Handles Sending Post requests
        DatabaseHandler db = new DatabaseHandler();

        db.collection.drop(); //Reset the table so we can have a fresh test

        List<String> dedLinks = new LinkedList<>();
        List<String> outlinks1 = new LinkedList<>();
        outlinks1.add("a1");
        outlinks1.add("b1");
        outlinks1.add("c1");
        outlinks1.add("d1");

        List<String> outlinks2 = new LinkedList<>();
        outlinks2.add("a2");
        outlinks2.add("b2");
        outlinks2.add("c2");
        outlinks2.add("d2");

        LinkAnalysisInfo lai1 = new LinkAnalysisInfo("www.google.com","1/1/2001","Allday", outlinks1, dedLinks);
        LinkAnalysisInfo lai2 = new LinkAnalysisInfo("www.google2.com","2/2/2002","Never", outlinks2, dedLinks);
        db.AddDocument(lai1);
        db.AddDocument(lai2);

        rc.sendLinkAnalysisPost();
    }
}
