package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ArrayDequetest {

    public static void main(String[] args) {
        ArrayDeque<Integer> M = new ArrayDeque<>();
        for (int i = 0; i < 200; i += 1) {
            M.addLast(i);
        }
        System.out.println(M.removeFirst());
        System.out.println(M.removeLast());
        for(int i = 0;i < 8;i++) {
            int operationNumber;
            if (M.size() > 0) {
                operationNumber = StdRandom.uniform(0, 5);
            } else {
                operationNumber = 0;
            }
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                M.addLast(randVal);
            } else if (operationNumber == 1) {
                System.out.println(M.size());
            } else if (operationNumber == 2) {
                //removeLast
                int m = M.removeLast();
            } else if (operationNumber == 3) {
                System.out.println(M.get(M.size() - 1));
            } else if (operationNumber == 4) {
                M.removeFirst();
            }
        }
    }
}