import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;

public class Crawler
{
    static  private  PriorityQueue<QueueObject> pageQueue;

    /**
     * Reads in a file containing links formatted as ["link" priority_value]
     * i.e. google.com 10
     *
     * @param  file  java File object to read from
     * @return       a priority queue created from the links in the file
     */
    public static PriorityQueue<QueueObject> GeneratePriorityQueue(File file)
    {
        Comparator<QueueObject> comp = new QueueSort();
        PriorityQueue<QueueObject> pQ = new PriorityQueue<QueueObject>(comp);

        Scanner inputFile;

        try
        {
            inputFile = new Scanner(file);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Failed to read input file");
            return pQ;
        }

        while(inputFile.hasNext())
        {
            String[] page = inputFile.nextLine().split(" ");
            QueueObject q = new QueueObject(page[0], Integer.parseInt(page[1]));
            pQ.add(q);
        }
        return pQ;
    }

    public static void main(String[] args)
    {
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        UserController uc = new UserController(new UserService()); //Receives Post requests
        RestClient rc = new RestClient(); //Handles Sending Post requests
        //DatabaseHandler db = new DatabaseHandler(); //Handles Database Queries


        File file = new File(System.getProperty("user.dir") + "/Files/" + args[0]);

        pageQueue = GeneratePriorityQueue(file);

        while(!pageQueue.isEmpty())
        {
            QueueObject q = pageQueue.poll();
            DatabaseInfo dbObject = Spider.crawl(q.page);
            //if(dbObject != null)
            //   db.AddDocument(dbObject);
            //rc.LinkAnalysisPost(dbObject);
            //rc.sendTextTransformationPost(dbObject);
            FillPriorityQueue();
        }
    }

    /**
     * Gets queue sent to us from Link Analysis and adds it to our priority queue
     */
    public static int FillPriorityQueue()
    {
        int pagesAdded = 0;
        JSONObject json = UserController.i.GetQueueObject();
        if(json == null) return -2;
        if(!json.has("webpages")) return -1;
        JSONArray array = json.getJSONArray("webpages");
        for(int i = 0; i < array.length(); ++i)
        {
            JSONObject tmp = array.getJSONObject(i);
            if(tmp.has("priority") && tmp.has("webpage"))
            {
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
class QueueObject
{
    public String page;
    public int priority;
    QueueObject(String n, int p)
    {
        page = n;
        priority = p;
    }
}


class QueueSort implements Comparator<QueueObject>
{
    /**
     * Compares the priority value of two java QueueObjects
     *
     * @param  a  java QueueObject object
     * @param  b  java QueueObject object
     * @return    0 if a = b, a positive number if a > b, a negative number if a < b
     */
    public int compare(QueueObject a, QueueObject b)
    {
        return a.priority - b.priority;
    }
}