import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

public class DBTester {

    public static void main(String[] args){
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        UserController uc = new UserController(new UserService()); //Receives Post requests
        RestClient rc = new RestClient(); //Handles Sending Post requests
        DatabaseHandler db = new DatabaseHandler(); //Handles Database Queries

        db.collection.drop(); //Reset the table so we can have a fresh test

        List<String> dedLinks = new ArrayList<>();
        List<String> outlinks1 = new ArrayList<>();
        outlinks1.add("a1");
        outlinks1.add("b1");
        outlinks1.add("c1");
        outlinks1.add("d1");

        List<String> outlinks2 = new ArrayList<>();
        outlinks2.add("a2");
        outlinks2.add("b2");
        outlinks2.add("c2");
        outlinks2.add("d2");

        List<String> doc1 = new ArrayList<>();
        doc1.add("test.pdf");
        doc1.add("pdf");
        doc1.add("This is a test");

        List<String> doc2 = new ArrayList<>();
        doc2.add("anotherTest.txt");
        doc2.add("txt");
        doc2.add("This is another test");

        List<List<String> > docs = new ArrayList<List<String> >();
        docs.add(doc1);
        docs.add(doc2);

        DatabaseInfo lai1 = new DatabaseInfo("www.google.com","1/1/2001","Allday", outlinks1, dedLinks, "test", docs);
        DatabaseInfo lai2 = new DatabaseInfo("www.google2.com","2/2/2002","Never", outlinks2, dedLinks, "test2", docs);
        db.AddDocument(lai1);
        db.AddDocument(lai2);

        rc.sendLinkAnalysisPost();
    }
}
