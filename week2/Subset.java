import java.util.Iterator;

public class Subset {
    /**
    * @param args
    */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
            q.enqueue(StdIn.readString());
        for (Iterator<String> s = q.iterator(); k > 0 && s.hasNext(); k--)
            System.out.println(s.next());
    }
}
