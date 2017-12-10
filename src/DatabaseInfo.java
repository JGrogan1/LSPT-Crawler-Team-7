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

    /**
     * DatabaseInfo object used to store all the appropriate info in the database
     * Values can be null
     *
     * @param  link             The link crawled
     * @param  dateLastUpdated  The date the website was last updated
     * @param  frequency        The frequency the page is crawled according to the sitemap
     * @param  outlinks         List of valid outlinks
     * @param  failedWebpages   All invalid links found
     * @param  html             Full website html
     * @param  documents        List of lists, each containing the document name, the document type, and the document text
     */
    public DatabaseInfo(String link, String dateLastUpdated, String frequency, List<String> outlinks, List<String> failedWebpages, String html, List<List<String> > documents)
    {
        this.link = link;
        this.dateLastUpdated = dateLastUpdated;
        this.frequency = frequency;
        this.outlinks = new ArrayList<String>(outlinks);

        if(failedWebpages != null)
        {
            this.failedWebpages = new ArrayList<String>(failedWebpages);
        }
        else
        {
            this.failedWebpages = null;
        }

        this.html = html;

        docs = new ArrayList<List<String> >();
        if(documents != null)
        {
            for (List<String> list : documents)
                docs.add(new ArrayList<String>(list));
        }
    }


    public String getLink()
    {
        return link;
    }

    public String getDateLastUpdated()
    {
        return dateLastUpdated;
    }

    public String getFrequency()
    {
        return frequency;
    }

    public List<String> getOutlinks()
    {
        return outlinks;
    }

    public List<String> getFailedWebpages()
    {
        return failedWebpages;
    }

    public String getHtml() {
        return html;
    }

    public List<List<String> > getDocuments()
    {
        return docs;
    }

    public void setDateLastUpdated(String dateLastUpdated)
    {
        this.dateLastUpdated = dateLastUpdated;
    }
}
