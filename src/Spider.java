import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Spider
{
    // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    /**
     * This performs all the work. It makes an HTTP request, checks the response, and then gathers
     * up all the links on the page. Perform a searchForWord after the successful crawl
     *
     * @param url
     *            - The URL to visit
     * @return whether or not the crawl was successful
     */
    public static List<String> crawl(String url)
    {
        List<String> links = new ArrayList<String>();
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
            // indicating that everything is great.
            {
                System.out.println("\n**Visiting** Received web page at " + url);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
                return filterLinks(links);
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                links.add(link.absUrl("href"));
            }
            return filterLinks(links);
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            return filterLinks(links);
        }
    }

    private static List<String> filterLinks(List<String> links)
    {
        for(int i = 0; i < links.size(); i++)
        {
            List<String> allowList = new ArrayList<String>();
            List<String> disallowList = new ArrayList<String>();
            URL url = null;
            try
            {
                url = new URL(links.get(i));
            }
            catch(MalformedURLException e)
            {
                System.out.println("Failed to connect to URL " + links.get(i));
                continue;
            }
            try(BufferedReader in = new BufferedReader(
                    new InputStreamReader(new URL(url.getProtocol()+"://"+url.getHost()+"/robots.txt").openStream()))) {
                String line;
                String allowString = "Allow:";
                String disallowString = "Disallow:";
                boolean startReading = false;
                while((line = in.readLine()) != null)
                {
                    if(line.startsWith("User-agent: *"))
                    {
                        startReading = true;
                    }
                    else if(line.startsWith("User-agent:" ))
                    {
                        if(startReading)
                            break;
                        startReading = false;
                    }
                    if(startReading) {
                        line = line.replaceAll("\\s+", "");
                        if (line.startsWith(allowString)) {
                            String regex = line.substring(allowString.length());
                            regex = regex.replace("?", "\\?");
                            regex = regex.replace("*", "\\\\*");
                            allowList.add(regex);
                        } else if (line.startsWith(disallowString)) {
                            String regex = line.substring(disallowString.length());
                            regex = regex.replace("?", "\\?");
                            regex = regex.replace("*", "\\\\*");
                            disallowList.add(regex);
                        }
                    }
                }
                in.close();
            }
            catch (IOException e)
            {
                System.out.println("Failed to get robots.txt file for " + links.get(i));
                continue;
            }

            String path = url.getPath();
            boolean allowed = false;
            for(int j = 0; j < allowList.size(); j++)
            {
                Pattern p = Pattern.compile(allowList.get(j));
                Matcher m = p.matcher(path);
                if(m.find() && m.start() == 0)
                {
                    allowed = true;
                    break;
                }
            }
            if(!allowed)
            {
                for (int j = 0; j < disallowList.size(); j++) {
                    Pattern p = Pattern.compile(disallowList.get(j));
                    Matcher m = p.matcher(path);
                    if(m.find() && m.start() == 0)
                    {
                        links.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }

        return links;
    }
}