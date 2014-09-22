package com.marshalchen.common.demoofui.cooldraganddrop;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import com.marshalchen.common.uimodule.cooldraganddrop.CoolDragAndDropGridView;
import com.marshalchen.common.uimodule.cooldraganddrop.SimpleScrollingStrategy;
import com.marshalchen.common.uimodule.cooldraganddrop.SpanVariableGridView;
import com.marshalchen.common.demoofui.R;

public class CoolDragAndDropActivity extends ActionBarActivity implements CoolDragAndDropGridView.DragAndDropListener, SpanVariableGridView.OnItemClickListener,
		SpanVariableGridView.OnItemLongClickListener {

	ItemAdapter mItemAdapter;
	CoolDragAndDropGridView mCoolDragAndDropGridView;
	List<Item> mItems = new LinkedList<Item>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cooldrag_drop_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
		mCoolDragAndDropGridView = (CoolDragAndDropGridView) findViewById(R.id.coolDragAndDropGridView);

		for (int r = 0; r < 2; r++) {
			mItems.add(new Item(R.drawable.cool_drag_drop_ic_local_search_airport_highlighted, 1, "Airport", "Heathrow"));
			mItems.add(new Item(R.drawable.cool_drag_drop_ic_local_search_bar_highlighted, 2, "Bar", "Connaught Bar"));
			mItems.add(new Item(R.drawable.cool_drag_drop_ic_local_search_drink_highlighted, 2, "Drink", "Tequila"));
			mItems.add(new Item(R.drawable.cool_drag_drop_ic_local_search_eat_highlighted, 2, "Eat", "Sliced Steaks"));
			mItems.add(new Item(R.drawable.cool_drag_drop_ic_local_search_florist_highlighted, 1, "Florist", "Roses"));
			mItems.add(new Item(R.drawable.cool_drag_drop_ic_local_search_gas_station_highlighted, 3, "Gas station", "QuickChek"));
			mItems.add(new Item(R.drawable.cool_drag_drop_ic_local_search_general_highlighted, 1, "General", "Service Station"));
			mItems.add(new Item(R.drawable.cool_drag_drop_ic_local_search_grocery_store_highlighted, 1, "Grocery", "E-Z-Mart"));
			mItems.add(new Item(R.drawable.cool_drag_drop_ic_local_search_pizza_highlighted, 1, "Pizza", "Pizza Hut"));
			mItems.add(new Item(R.drawable.cool_drag_drop_ic_local_search_post_office_highlighted, 2, "Post office", "USPS"));
			mItems.add(new Item(R.drawable.cool_drag_drop_ic_local_search_see_highlighted, 2, "See", "Tower Bridge"));
			mItems.add(new Item(R.drawable.cool_drag_drop_ic_local_search_shipping_service_highlighted, 3, "Shipping service", "Celio*"));
		}

		mItemAdapter = new ItemAdapter(this, mItems);
		mCoolDragAndDropGridView.setAdapter(mItemAdapter);
		mCoolDragAndDropGridView.setScrollingStrategy(new SimpleScrollingStrategy(scrollView));
		mCoolDragAndDropGridView.setDragAndDropListener(this);
		mCoolDragAndDropGridView.setOnItemLongClickListener(this);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		mItemAdapter.notifyDataSetChanged();
		super.onPostCreate(savedInstanceState);
	}
    @Override
    public Intent getSupportParentActivityIntent() {
        this.finish();
        return super.getSupportParentActivityIntent();
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
    }

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		//getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		mCoolDragAndDropGridView.startDragAndDrop();

		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	@Override
	public void onDragItem(int from) {

	}

	@Override
	public void onDraggingItem(int from, int to) {

	}

	@Override
	public void onDropItem(int from, int to) {
		if (from != to) {

			mItems.add(to, mItems.remove(from));
			mItemAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public boolean isDragAndDropEnabled(int position) {
		return true;
	}

}
