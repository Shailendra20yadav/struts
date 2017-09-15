package test;

import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public class ReverseList {
	 private static final int REVERSE_THRESHOLD  =   18;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	 @SuppressWarnings({"rawtypes", "unchecked"})
	public static void reverseList(List<?> list){
		int size = list.size();
        if (size < REVERSE_THRESHOLD || list instanceof RandomAccess) {
        	//int mid=size/2;
        	int mid=size>>1;
            for (int i=0, j=size-1; i<mid; i++, j--)
                swap(list, i, j);
        } else {
            // instead of using a raw type here, it's possible to capture
            // the wildcard but it will require a call to a supplementary
            // private method
            ListIterator fwd = list.listIterator();
            ListIterator rev = list.listIterator(size);
            for (int i=0, mid=list.size()>>1; i<mid; i++) {
                Object tmp = fwd.next();
                fwd.set(rev.previous());
                rev.set(tmp);
            }
        }
		
	}
 @SuppressWarnings({"rawtypes", "unchecked"})
    public static void swap(List<?> list, int i, int j) {
        // instead of using a raw type here, it's possible to capture
        // the wildcard but it will require a call to a supplementary
        // private method
        final List l = list;
        l.set(i, l.set(j, l.get(i)));
    }

}
