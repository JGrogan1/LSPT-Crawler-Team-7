import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class RestClient
{

    public static RestClient i = null;

    private static final String URI = "http://teamz.cs.rpi.edu:8080/document";

    RestClient()
    {
        if(i != null)
        {
            return;
        }
        i = this;
    }

    /**
     * Send a JSON object of data to link analysis
     *
     * @param  dbi  java object representing a database object
     * @return      string representing whether the post request succeeded
     */
    public String LinkAnalysisPost(DatabaseInfo dbi)
    {
        List<DatabaseInfo> pages = new LinkedList<DatabaseInfo>();
        pages.add(dbi);
        JSONArray array = new JSONArray(pages);
        List<String> dead_links = new LinkedList<>();
        JSONObject json = new JSONObject();
        json.put("webpages",array);
        json.put("failedWebpages",dead_links);
        json.put("failedWebpages",dead_links);
        try
        {
            HttpResponse<String> httpResponse = Unirest.post(URI)
                    .header("Content-Type", "application/json")
                    .body(json.toString()).asString();
            return "ack";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "failure";
        }
    }

    /**
     * Send a JSON object of data to text transformation
     *
     * @param  dbi  java object representing a database object
     * @return      string representing whether the post request succeeded
     */
    public String sendTextTransformationPost(DatabaseInfo dbi)
    {
        JSONObject json = new JSONObject();
        json.put("html",dbi.getHtml());
        json.put("docs",dbi.getDocuments());
        json.put("outlinks",dbi.getDocuments());
        json.put("url",dbi.getDocuments());
        //json.put("timestamp",);

        System.out.println("Sending to Text Transformation: " + json.toString());
        try
        {
            HttpResponse<String> httpResponse = Unirest.post(URI)
                    .header("Content-Type", "application/json")
                    .body(json.toString()).asString();
            return "ack";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "failure";
        }
    }
}