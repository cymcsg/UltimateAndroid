package com.marshalchen.common.demoofui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.common.demoofui.cooldraganddrop.CoolDragAndDropActivity;
import com.marshalchen.common.demoofui.materialAnimations.MaterialAnimationActivity;
import com.marshalchen.common.demoofui.materialdesigndemo.MaterialDesignActivity;
import com.marshalchen.common.demoofui.materialmenu.MaterialMenuToolbarActivity;
import com.marshalchen.common.demoofui.sampleModules.FloatingActionButtonDemo;
import com.marshalchen.common.demoofui.sampleModules.GestureTouchActivity;
import com.marshalchen.common.demoofui.sampleModules.KenBurnsViewActivity;
import com.marshalchen.common.demoofui.sampleModules.MaterialListViewActivity;
import com.marshalchen.common.demoofui.sampleModules.MotionSampleActivity;
import com.marshalchen.common.demoofui.sampleModules.NumberProgressBarActivity;
import com.marshalchen.common.demoofui.sampleModules.RippleEffectActivity;
import com.marshalchen.common.demoofui.sampleModules.SearchDrawableActivity;
import com.marshalchen.common.demoofui.sampleModules.SignaturePadActivity;
import com.marshalchen.common.demoofui.ultimaterecyclerview.UltimateRecyclerViewActivity;
import com.marshalchen.common.ui.Typefaces;
import com.marshalchen.common.uimodule.enhanceListView.EnhancedListView;
import com.marshalchen.common.uimodule.titanic.Titanic;
import com.marshalchen.common.uimodule.titanic.TitanicTextView;
import com.marshalchen.common.uimodule.viewpagerindicator.CirclePageIndicator;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LandingFragment extends Fragment {
    View mainView;
    @InjectView(R.id.landingEnhanceListView)
    EnhancedListView landingEnhanceListView;
    List<Map<String, ?>> enhanceList;
    EnhancedListAdapter enhancedListAdapter;
    @InjectView(R.id.landingMallViewpager)
    ViewPager landingMallViewpager;
    CirclePageIndicator landingMallViewPagerIndicator;
    private List<View> viewpagerList = new ArrayList<View>();
    Titanic titanic;
    @InjectView(R.id.titanicTextView)
    TitanicTextView titanicTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater
                .inflate(R.layout.landing_fragment, container, false);
        ButterKnife.inject(this, mainView);
        initTitanicView();
        initEnhanceList();
        initViewPager();
        // CryptoUtils.testCrypto(getActivity());
        return mainView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void initTitanicView() {
        titanic = new Titanic();
        titanic.start(titanicTextView);
        titanicTextView.setTypeface(Typefaces.get(getActivity(),
                "Satisfy-Regular.ttf"));
        // HandlerUtils.sendMessageHandlerDelay(titanicHandler, 0, 3000);
    }

    Handler titanicHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            titanic.cancel();
            landingEnhanceListView.setVisibility(View.VISIBLE);
        }
    };

    private void initEnhanceList() {

        enhanceList = getData("com.marshalchen.common.demoofui.sampleModules");
        enhancedListAdapter = new EnhancedListAdapter(enhanceList);

        landingEnhanceListView.setAdapter(enhancedListAdapter);
        landingEnhanceListView
                .setDismissCallback(new EnhancedListView.OnDismissCallback() {
                    @Override
                    public EnhancedListView.Undoable onDismiss(
                            EnhancedListView listView, int position) {
                        enhancedListAdapter.remove(position);
                        return null;
                    }
                });
        landingEnhanceListView.setSwipingLayout(R.id.swiping_layout);
        landingEnhanceListView.setUndoStyle(null);

        landingEnhanceListView.enableSwipeToDismiss();

        landingEnhanceListView
                .setSwipeDirection(EnhancedListView.SwipeDirection.START);
        landingEnhanceListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent intent = (Intent) enhanceList.get(position).get("intent");
                        startActivity(intent);
                        // TODO add OnItemClick event
                    }
                });
    }


    protected List<Map<String, ?>> getData(String prefix) {
        List<Map<String, ?>> myData = new ArrayList<Map<String, ?>>();

        Intent intent = new Intent("com.marshalchen.common.demoofui", null);

        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);

        if (null == list)
            return myData;

        int len = list.size();
        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            String activityName = info.activityInfo.name;
            String[] labelPath = activityName.split("\\.");
            String nextLabel = labelPath[labelPath.length - 1];
            addItem(myData,
                    nextLabel,
                    activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
        }
        Collections.sort(myData, sDisplayNameComparator);

        addItemToTop(myData,
                "SignaturePadActivity",
                new Intent(getActivity(), SignaturePadActivity.class));
        addItemToTop(myData,
                "SmoothProgressBarActivity",
                new Intent(getActivity(), SmoothProgressBarActivity.class));
        addItemToTop(myData,
                "RippleEffectActivity",
                new Intent(getActivity(), RippleEffectActivity.class));
        addItemToTop(myData,
                "NumberProgressBarActivity",
                new Intent(getActivity(), NumberProgressBarActivity.class));
        addItemToTop(myData,
                "MotionSampleActivity",
                new Intent(getActivity(), MotionSampleActivity.class));

        addItemToTop(myData,
                "MaterialMenuToolbarActivity",
                new Intent(getActivity(), MaterialMenuToolbarActivity.class));
//        addItemToTop(myData,
//                "MaterialListViewActivity",
//                new Intent(getActivity(), MaterialListViewActivity.class));
        addItemToTop(myData,
                "KenBurnsViewActivity",
                new Intent(getActivity(), KenBurnsViewActivity.class));
        addItemToTop(myData,
                "GestureTouchActivity",
                new Intent(getActivity(), GestureTouchActivity.class));
        addItemToTop(myData,
                "FloatingActionButtonDemo",
                new Intent(getActivity(), FloatingActionButtonDemo.class));
        addItemToTop(myData,
                "CoolDragAndDropActivity",
                new Intent(getActivity(), CoolDragAndDropActivity.class));
        addItemToTop(myData,
                "MaterialDesignActivity",
                new Intent(getActivity(), MaterialDesignActivity.class));
        addItemToTop(myData,
                "MaterialAnimationActivity",
                new Intent(getActivity(), MaterialAnimationActivity.class));
        addItemToTop(myData,
                "SearchDrawableActivity",
                new Intent(getActivity(), SearchDrawableActivity.class));
        addItemToTop(myData,
                "UltimateRecyclerViewActivity",
                new Intent(getActivity(), UltimateRecyclerViewActivity.class));
        return myData;
    }

    private final static Comparator<Map<String, ?>> sDisplayNameComparator = new Comparator<Map<String, ?>>() {
        private final Collator collator = Collator.getInstance();

        public int compare(Map<String, ?> map1, Map<String, ?> map2) {
            return collator.compare(map1.get("title"), map2.get("title"));
        }
    };

    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    protected void addItem(List<Map<String, ?>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

    protected void addItemToTop(List<Map<String, ?>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(0, temp);
    }

    private void initViewPager() {
        addLandingViewPager();
        landingMallViewpager.setAdapter(new CustomViewPagerAdapter(
                viewpagerList));
        landingMallViewpager
                .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i2) {

                    }

                    @Override
                    public void onPageSelected(int i) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {
                        // setCurrentDot(i);

                    }
                });
        landingMallViewPagerIndicator = (CirclePageIndicator) mainView
                .findViewById(R.id.landingMallViewPagerIndicator);
        landingMallViewPagerIndicator.setViewPager(landingMallViewpager);
        // final float density = getResources().getDisplayMetrics().density;
        // landingMallViewPagerIndicator.setBackgroundColor(0xffffff);
        // landingMallViewPagerIndicator.setRadius(5 * density);
        // landingMallViewPagerIndicator.setPageColor(getResources().getColor(R.color.black));
        // landingMallViewPagerIndicator.setFillColor(getResources().getColor(R.color.white));
        // landingMallViewPagerIndicator.setStrokeColor(getResources().getColor(R.color.black));
        // landingMallViewPagerIndicator.setStrokeWidth(1 * 1.0f);

    }

    private class EnhancedListAdapter extends BaseAdapter {
        // private List<String> mItems = new ArrayList<String>();
        private List<Map<String, ?>> mLists = new ArrayList<>();

        // private EnhancedListAdapter(List<String> mItems) {
        // this.mItems = mItems;
        // }
        private EnhancedListAdapter(List<Map<String, ?>> mLists) {
            this.mLists = mLists;
        }

        void resetItems() {
            mLists.clear();

        }

        public void remove(int position) {
            mLists.remove(position);
            notifyDataSetChanged();
        }

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        @Override
        public int getCount() {
            return mLists.size();
        }

        /**
         * Get the data item associated with the specified position in the data
         * set.
         *
         * @param position Position of the item whose data we want within the
         *                 adapter's data set.
         * @return The data at the specified position.
         */
        @Override
        public Object getItem(int position) {
            return mLists.get(position);
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set
         *                 whose row id we want.
         * @return The id of the item at the specified position.
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.list_item_enhance, parent, false);
                // Clicking the delete icon, will read the position of the item
                // stored in
                // the tag and delete it from the list. So we don't need to
                // generate a new
                // onClickListener every time the content of this view changes.
                final View origView = convertView;
                convertView.findViewById(R.id.action_delete)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // listViewHistory.delete(((ViewHolder)
                                // origView.getTag()).position);
                            }
                        });

                holder = new ViewHolder();
                assert convertView != null;
                holder.mTextView = (TextView) convertView
                        .findViewById(R.id.reacolhistextview);
                // holder.reacolReaLyout = (RelativeLayout)
                // convertView.findViewById(R.id.reacolReaLyout);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mTextView.setText(mLists.get(position).get("title")
                    .toString());
            return convertView;
        }

        private class ViewHolder {
            TextView mTextView;
        }

    }

    private void addLandingViewPager() {
        if (viewpagerList == null)
            viewpagerList = new ArrayList<View>();
        else
            viewpagerList.clear();
        for (int i = 0; i < 3; i++) {
            View viewPagerItem = getActivity().getLayoutInflater().inflate(
                    R.layout.landing_viewpager_item, null, false);
            ViewPagerViewHolder viewHolder = new ViewPagerViewHolder(
                    viewPagerItem);
            viewHolder.landViewPagerImage.setImageResource(R.drawable.test);
            // viewHolder.landViewPagerInfo1.setText("hh"+i);
            // viewPagerItem.setTag(viewHolder);
            viewpagerList.add(viewPagerItem);
        }
    }

    static class ViewPagerViewHolder {
        @InjectView(R.id.landViewPagerImage)
        ImageView landViewPagerImage;
        @InjectView(R.id.landViewPagerImage1)
        ImageView landViewPagerImage1;
        @InjectView(R.id.landViewPagerName)
        TextView landViewPagerName;
        @InjectView(R.id.landViewPagerName1)
        TextView landViewPagerName1;
        @InjectView(R.id.landViewPagerInfo)
        TextView landViewPagerInfo;
        @InjectView(R.id.landViewPagerInfo1)
        TextView landViewPagerInfo1;

        public ViewPagerViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }

    class CustomViewPagerAdapter extends PagerAdapter {
        List<View> viewpagerList;

        public CustomViewPagerAdapter(List<View> viewpagerList) {
            this.viewpagerList = viewpagerList;
        }

        @Override
        public int getCount() {
            return viewpagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == (o);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            if (viewpagerList != null && viewpagerList.size() > position
                    && viewpagerList.get(position) != null)
                container.removeView(viewpagerList.get(position));

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewpagerList.get(position), 0);
            viewpagerList.get(position).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(),
                                    MaterialActivity.class);
                            startActivity(intent);
                        }
                    });
            // return super.instantiateItem(container, position);
            return viewpagerList.get(position);
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
            super.restoreState(state, loader);
        }

        @Override
        public Parcelable saveState() {
            return super.saveState();
        }

    }
}
