import org.junit.jupiter.api.Test;

public class CrawlerTest {

    @Test
    public void testCrawler1() {
        String args[] = {"test1.txt"};
        Crawler.main(args);
    }

    @Test
    public void testCrawler2() {
        String args[] = {"testNotFound.txt"};
        Crawler.main(args);
    }
}