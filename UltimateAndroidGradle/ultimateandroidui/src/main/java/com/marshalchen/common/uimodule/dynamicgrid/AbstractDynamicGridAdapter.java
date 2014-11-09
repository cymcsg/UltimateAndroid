package com.marshalchen.common.uimodule.dynamicgrid;

import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Author: alex askerov
 * Date: 9/6/13
 * Time: 7:43 PM
 */
public abstract class AbstractDynamicGridAdapter extends BaseAdapter {
    public static final int INVALID_ID = -1;


    private HashMap<Object, Integer> mIdMap = new HashMap<Object, Integer>();

    /**
     * @return return columns number for GridView. Need for compatibility
     * (@link android.widget.GridView#getNumColumns() requires api 11)
     */
    public abstract int getColumnCount();

    /**
     * Determines how to reorder items dragged from <code>originalPosition</code> to <code>newPosition</code>
     *
     * @param originalPosition
     * @param newPosition
     */
    public abstract void reorderItems(int originalPosition, int newPosition);

    /**
     * Adapter must have stable id
     *
     * @return
     */
    @Override
    public final boolean hasStableIds() {
        return true;
    }

    /**
     * creates stable id for object
     *
     * @param item
     */
    protected void addStableId(Object item) {
        int newId = (int) getItemId(getCount() - 1);
        newId++;
        mIdMap.put(item, newId);
    }

    /**
     * create stable ids for list
     *
     * @param items
     */
    protected void addAllStableId(List<?> items) {
        int startId = (int) getItemId(getCount() - 1);
        startId++;
        for (int i = startId; i < items.size(); i++) {
            mIdMap.put(items.get(i), i);
        }
    }

    /**
     * get id for position
     *
     * @param position
     * @return
     */
    @Override
    public final long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        Object item = getItem(position);
        return mIdMap.get(item);
    }

    /**
     * clear stable id map
     * should called when clear adapter data;
     */
    protected void clearStableIdMap() {
        mIdMap.clear();
    }

    /**
     * remove stable id for <code>item</code>. Should called on remove data item from adapter
     *
     * @param item
     */
    protected void removeStableID(Object item) {
        mIdMap.remove(item);
    }

}
