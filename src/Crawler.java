import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;

public class Crawler {

    static  private  PriorityQueue<QueueObject> pageQueue;

    public static PriorityQueue<QueueObject> GeneratePriorityQueue(File file){
        Comparator<QueueObject> comp = new QueueSort();
        PriorityQueue<QueueObject> pQ = new PriorityQueue<QueueObject>(comp);

        Scanner inputFile;

        try {
            inputFile = new Scanner(file);
        } catch (FileNotFoundException e){
            return null;
        }

        while(inputFile.hasNext()){
            String[] page = inputFile.nextLine().split(" ");
            QueueObject q = new QueueObject(page[0], Integer.parseInt(page[1]));
            pQ.add(q);
        }
        return pQ;
    }

    public static void main(String[] args){
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        UserController uc = new UserController(new UserService()); //Receives Post requests
        RestClient rc = new RestClient(); //Handles Sending Post requests
        //DatabaseHandler db = new DatabaseHandler(); //Handles Database Queries

        File file = new File(System.getProperty("user.dir") + "/Files/" + args[0]);
        if (!file.exists())
        {
            System.out.println("Invalid file.");
            return;
        }

        pageQueue = GeneratePriorityQueue(file);

        for (QueueObject q : pageQueue) {
            DatabaseInfo dbObject = Spider.crawl(q.page);
            //if(dbObject != null)
            //   db.AddDocument(dbObject);
            //rc.LinkAnalysisPost(dbObject);
            //rc.sendTextTransformationPost(dbObject);
        }
    }

    public static int FillPriorityQueue(){
        int pagesAdded = 0;
        JSONObject json = UserController.i.GetQueueObject();
        if(json == null) return -2;
        if(!json.has("webpages")) return -1;
        JSONArray array = json.getJSONArray("webpages");
        for(int i = 0; i < array.length(); ++i){
            JSONObject tmp = array.getJSONObject(i);
            if(tmp.has("priority") && tmp.has("webpage")){
                int p = tmp.getInt("priority");
                String s = tmp.getString("webpage");
                pageQueue.add(new QueueObject(s,p));
                pagesAdded++;
            }
        }
        return pagesAdded;
    }

}

//Helper Classes
class QueueObject {
    public String page;
    public int priority;
    QueueObject(String n, int p){
        page = n;
        priority = p;
    }
}

class QueueSort implements Comparator<QueueObject> {
    @Override
    public int compare(QueueObject a, QueueObject b) {
        return a.priority - b.priority;
    }
}