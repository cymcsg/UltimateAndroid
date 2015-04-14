package com.marshalchen.common.uimodule.slideExpand;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

/**
 * Implementation of a WrapperListAdapter interface
 * in which each method delegates to the wrapped adapter.
 *
 * Extend this class if you only want to change a
 * few methods of the wrapped adapter.
 *
 * The wrapped adapter is available to subclasses as the "wrapped" field.
 *
 * @author tjerk
 * @date 6/9/12 4:41 PM
 */
public abstract class WrapperListAdapterImpl extends BaseAdapter implements WrapperListAdapter {
	protected ListAdapter wrapped;

	public WrapperListAdapterImpl(ListAdapter wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ListAdapter getWrappedAdapter() {
		return wrapped;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return wrapped.areAllItemsEnabled();
	}

	@Override
	public boolean isEnabled(int i) {
		return wrapped.isEnabled(i);
	}

	@Override
	public void registerDataSetObserver(DataSetObserver dataSetObserver) {
		wrapped.registerDataSetObserver(dataSetObserver);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
		wrapped.unregisterDataSetObserver(dataSetObserver);
	}

	@Override
	public int getCount() {
		return wrapped.getCount();
	}

	@Override
	public Object getItem(int i) {
		return wrapped.getItem(i);
	}

	@Override
	public long getItemId(int i) {
		return wrapped.getItemId(i);
	}

	@Override
	public boolean hasStableIds() {
		return wrapped.hasStableIds();
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		return wrapped.getView(position, view, viewGroup);
	}

	@Override
	public int getItemViewType(int i) {
		return wrapped.getItemViewType(i);
	}

	@Override
	public int getViewTypeCount() {
		return wrapped.getViewTypeCount();
	}

	@Override
	public boolean isEmpty() {
		return wrapped.isEmpty();
	}
}
