import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class LinkAnalysisInfo
{
    private String link;
    private String dateLastUpdated;
    private String frequency;
    private List<String> outlinks;
    private List<String> failedWebpages;

    public LinkAnalysisInfo(String link, String dateLastUpdated, String frequency, List<String> outlinks, List<String> failedWebpages)
    {
        this.link = link;
        this.dateLastUpdated = dateLastUpdated;
        this.frequency = frequency;
        this.outlinks = new ArrayList<String>(outlinks);
        this.failedWebpages = new ArrayList<String>(failedWebpages);
    }

    public LinkAnalysisInfo(Document doc)
    {
        this.link = (String)doc.get("link");
        this.dateLastUpdated = (String)doc.get("dateLastUpdated");
        this.frequency = (String)doc.get("frequency");
        this.outlinks = (ArrayList<String>)doc.get("outlinks");
        this.failedWebpages = (ArrayList<String>)doc.get("failedWebpages");
    }

    public String getLink() {
        return link;
    }

    public String getDateLastUpdated() {
        return dateLastUpdated;
    }

    public String getFrequency() {
        return frequency;
    }

    public List<String> getOutlinks() {
        return outlinks;
    }

    public List<String> getFailedWebpages() {
        return failedWebpages;
    }

    public void setDateLastUpdated(String dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }
}
