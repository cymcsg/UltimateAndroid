package com.marshalchen.common.uimodule.listbuddies.adapters;

import android.widget.BaseAdapter;

/**
 * Adapter that allows the list to loop over the same items again and again creating a loop.
 */
public abstract class CircularLoopAdapter extends BaseAdapter {
    private static final String TAG = CircularLoopAdapter.class.getSimpleName();

    /**
     * In getCount(), if we return Integer.MAX_VALUE, it will give you about 2 billion items,
     * which should be enough to look like infinite.
     * <p/>
     * We can see the answer to the question on here where Romain Guy confirm this solution:
     * <p/>
     * http://stackoverflow.com/questions/2332847/how-to-create-a-closed-circular-listview
     */
    @Override
    public int getCount() {
        //if you don't do that and the adapter has nothing yet,you will get exception.
        if (getCircularCount()==0) return 0;
        return Integer.MAX_VALUE;
    }

    protected abstract int getCircularCount();

    /**
     * Gets the position that correspond to the position in the amount of items we actually have
     *
     * @param position - position of the item in the list
     * @return - position that we actually wanna take from our list
     */
    public int getCircularPosition(int position) {
        //if you don't do that,you will get diver by zero exception.
        if (getCircularCount()==0) return 0;
        return position % getCircularCount();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
