package deque;


import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private Comparator cmp;

    public MaxArrayDeque(Comparator<T> c) {
        this.cmp = c;
    }
    public T max() {
        if (isEmpty()) {
            return null;
        }
        T m = get(0);
        for (int i = 1; i < this.size(); i++) {
            T temp = this.get(i);
            if (cmp.compare(temp,m) > 0) {
                m = temp;
            }
        }
        return m;
    }
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T m = this.get(0);
        for (int i = 1; i < this.size(); i++) {
            T temp = this.get(i);
            if (c.compare(m, temp) < 0) {
                m = temp;
            }
        }
        return m;
    }
}
