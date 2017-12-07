import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import jdk.nashorn.internal.objects.NativeUint8Array;
import org.apache.http.HttpHost;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RestClient {

    public static RestClient i = null;

    private static final String URI = "http://localhost:4567/post";

    RestClient(){
        if(i != null){
            return;
        }
        i = this;
    }

    public String sendLinkAnalysisPost() {
        JSONArray array = new JSONArray(DatabaseHandler.i.GetAll());
        List<String> dead_links = new LinkedList<>();
        JSONObject json = new JSONObject();
        json.put("webpages",array);
        json.put("failedWebpages",dead_links);
        System.out.println("Sending to LinkAnalysis: " + json.toString());
        //DatabaseHandler.i.db.drop(); //Remove all old data from the table
        try {
            HttpResponse<String> httpResponse = Unirest.post(URI)
                    .header("Content-Type", "application/json")
                    .body(json.toString()).asString();
            return "ack";
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
    }
}