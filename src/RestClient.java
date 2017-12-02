import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpHost;
import org.json.JSONArray;
import org.json.JSONObject;

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
        JSONArray json = new JSONArray(DatabaseHandler.i.GetAll());
        System.out.println("Sending to LinkAnalysis: " + json.toString());
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