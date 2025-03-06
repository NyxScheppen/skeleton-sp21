package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> m = new AListNoResizing<>();
        BuggyAList<Integer> n = new BuggyAList<>();
        m.addLast(1);
        m.addLast(2);
        m.addLast(3);
        n.addLast(1);
        n.addLast(2);
        n.addLast(3);
        for(int i = 0;i < 3;i++){
            int expected = m.removeLast();
            int actual = n.removeLast();
            assertEquals(actual,expected);
        }
    }
    @Test
    public void randomizedTest(){
        BuggyAList<Integer> L = new BuggyAList<>();
        AListNoResizing<Integer> M = new AListNoResizing<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber;
            if(M.size() > 0){
                operationNumber = StdRandom.uniform(0, 4);
            } else{
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
                assertEquals(size,size0);
            }else if (operationNumber == 2) {
                //removeLast
                int n = L.removeLast();
                int m = M.removeLast();
                assertEquals(n,m);
            }else if (operationNumber == 3){
                int n = L.getLast();
                int m = M.getLast();
                assertEquals(n,m);
            }
        }
    }

}
