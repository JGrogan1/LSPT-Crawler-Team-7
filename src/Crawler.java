import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
import java.io.IOException;

public class Crawler {

    static  private  PriorityQueue<QueueObject> pageQueue;

    public static PriorityQueue<QueueObject> GeneratePriorityQueue(File file){
        Comparator<QueueObject> comp = new QueueSort();
        PriorityQueue<QueueObject> pQ = new PriorityQueue<QueueObject>(comp);

        Scanner inputFile;

        try {
            inputFile = new Scanner(file);
        } catch (FileNotFoundException e){
            return null;
        }

        while(inputFile.hasNext()){
            String[] page = inputFile.nextLine().split(" ");
            QueueObject q = new QueueObject(page[0], Integer.parseInt(page[1]));
            pQ.add(q);
        }
        return pQ;
    }

    public static void main(String[] args){
        File file = new File(System.getProperty("user.dir") + "/Files/input.txt");
        pageQueue = GeneratePriorityQueue(file);

        List<String> outlinks = new LinkedList<String>();
        for (QueueObject q : pageQueue) {
            outlinks.addAll(Spider.crawl(q.page));
        }
        System.out.println("Number of total outlinks: " + outlinks.size());

    }

}

//Helper Classes
class QueueObject {
    public String page;
    public int priority;
    QueueObject(String n, int p){
        page = n;
        priority = p;
    }
}

class QueueSort implements Comparator<QueueObject> {
    @Override
    public int compare(QueueObject a, QueueObject b) {
        return a.priority - b.priority;
    }
}