package timingtest;
import edu.princeton.cs.algs4.Stopwatch;
import net.sf.saxon.expr.Component;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer> n = new AList<Integer>();
        for(int i = 1; i <= 6;i++){
            n.addLast(1000*(int)Math.pow((double)2,(double)i));
        }
        AList<Double> y = new AList<Double>();
        AList<Integer> x = new AList<Integer>();
        for(int i = 0;i < n.size();i++){
            Stopwatch sw = new Stopwatch();
            SLList<Integer> m = new SLList<Integer>();
            for(int j = 0;j < n.get(i);j++){
                m.addLast(1);
            }
            double timeInSeconds = sw.elapsedTime();
            y.addLast(timeInSeconds);
            x.addLast(10000);
        }
        printTimingTable(n,y,x);
    }

}
