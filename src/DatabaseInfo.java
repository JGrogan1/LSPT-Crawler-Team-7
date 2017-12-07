import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DatabaseInfo
{
    private String link;
    private String dateLastUpdated;
    private String frequency;
    private List<String> outlinks;
    private List<String> failedWebpages;
    private String html;
    private List<List<String> > docs;

    public DatabaseInfo(String link, String dateLastUpdated, String frequency, List<String> outlinks, List<String> failedWebpages, String html, List<List<String> > documents)
    {
        this.link = link;
        this.dateLastUpdated = dateLastUpdated;
        this.frequency = frequency;
        this.outlinks = new ArrayList<String>(outlinks);
        this.failedWebpages = new ArrayList<String>(failedWebpages);
        this.html = html;
        docs = new ArrayList<List<String> >();
        for(List<String> list : documents)
            docs.add(new ArrayList<String>(list));
    }

    public DatabaseInfo(Document doc)
    {
        this.link = (String)doc.get("link");
        this.dateLastUpdated = (String)doc.get("dateLastUpdated");
        this.frequency = (String)doc.get("frequency");
        this.outlinks = (ArrayList<String>)doc.get("outlinks");
        this.failedWebpages = (ArrayList<String>)doc.get("failedWebpages");
        this.html = (String)doc.get("html");
        this.docs = (List<List<String> >)doc.get("docs");
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

    public String getHtml() {
        return html;
    }

    public List<List<String> > getDocuments() {
        return docs;
    }

    public void setDateLastUpdated(String dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }
}
