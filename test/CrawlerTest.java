import org.junit.jupiter.api.Test;

//TODO: Compare outputs -- hard to create outlink list by hand, hard to compare documents
public class CrawlerTest {

    // Crawls Google
    @Test
    public void testSmall() {
        String args[] = {"testSmall.txt"};
        Crawler.main(args);
    }

    // Crawls three different websites
    @Test
    public void testMedium() {
        String args[] = {"testMedium.txt"};
        Crawler.main(args);
    }

    // Crawls seven different websites
    @Test
    public void testLarge() {
        String args[] = {"testLarge.txt"};
        Crawler.main(args);
    }

    // Crawls a websites that links to a lot of documents -- pdfs, also checks how we handle .ppt extensions
    @Test
    public void testDocs() {
        String args[] = {"testDocs.txt"};
        Crawler.main(args);
    }

    // Crawls a news site -- with a lot of outlinks
    @Test
    public void testNews() {
        String args[] = {"testNews.txt"};
        Crawler.main(args);
    }

    // Feeds crawler urls that don't follow valid url structure
    @Test
    public void testInvalid() {
        String args[] = {"testInvalid.txt"};
        Crawler.main(args);
    }

    // Attemps to crawl a website that redirects
    @Test
    public void testRedirect() {
        String args[] = {"testRedirect.txt"};
        Crawler.main(args);
    }

    // Attempts to crawl a non-existent website
    @Test
    public void testNotFound() {
        String args[] = {"testNotFound.txt"};
        Crawler.main(args);
    }
}