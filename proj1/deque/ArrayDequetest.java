package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ArrayDequetest {

    @Test
    public void test() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        LinkedListDeque<Integer> M = new LinkedListDeque<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber;
            if (M.size() > 0) {
                operationNumber = StdRandom.uniform(0, 4);
            } else {
                operationNumber = StdRandom.uniform(0, 2);
            }
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                M.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size0 = M.size();
                assertEquals(size, size0);
            } else if (operationNumber == 2) {
                //removeLast
                int n = L.removeLast();
                int m = M.removeLast();
                assertEquals(n, m);
            }
        }
    }
}
