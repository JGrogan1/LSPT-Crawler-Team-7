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


}
