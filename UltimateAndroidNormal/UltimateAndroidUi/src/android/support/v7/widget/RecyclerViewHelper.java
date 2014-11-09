package android.support.v7.widget;

/**
 * Created by aurel on 19/10/14.
 */
public class RecyclerViewHelper {

    public static int convertPreLayoutPositionToPostLayout(RecyclerView recyclerView, int position) {
        return recyclerView.mRecycler.convertPreLayoutPositionToPostLayout(position);
    }
}
