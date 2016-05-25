package com.marshalchen.common.demoofui.pagerSlidingTabStrip;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.MaterialRippleLayout;

import static com.astuetz.PagerSlidingTabStrip.CustomTabProvider;

public class QuickContactFragment extends DialogFragment {

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private ContactPagerAdapter adapter;

    public static QuickContactFragment newInstance() {
        QuickContactFragment f = new QuickContactFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        View root = inflater.inflate(R.layout.pager_sliding_tab_fragment_quick_contact, container, false);

        tabs = (PagerSlidingTabStrip) root.findViewById(R.id.tabs);
        pager = (ViewPager) root.findViewById(R.id.pager);
        adapter = new ContactPagerAdapter();

        pager.setAdapter(adapter);

        tabs.setViewPager(pager);

        return root;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart() {
        super.onStart();

        // change dialog width
        if (getDialog() != null) {

            int fullWidth = getDialog().getWindow().getAttributes().width;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                fullWidth = size.x;
            } else {
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                fullWidth = display.getWidth();
            }

            final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                    .getDisplayMetrics());

            int w = fullWidth - padding;
            int h = getDialog().getWindow().getAttributes().height;

            getDialog().getWindow().setLayout(w, h);
        }
    }

    public class ContactPagerAdapter extends PagerAdapter implements CustomTabProvider {

        private final int[] ICONS = {R.drawable.test, R.drawable.test,
                R.drawable.test_back, R.drawable.test_back};

        public ContactPagerAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return ICONS.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // looks a little bit messy here
            TextView v = new TextView(getActivity());
            v.setBackgroundResource(R.color.background_floating_material_dark);
            v.setText("PAGE " + (position + 1));
            final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources()
                    .getDisplayMetrics());
            v.setPadding(padding, padding, padding, padding);
            v.setGravity(Gravity.CENTER);
            container.addView(v, 0);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            container.removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View v, Object o) {
            return v == ((View) o);
        }

        @Override
        public View getCustomTabView(ViewGroup parent, int position) {
            MaterialRippleLayout materialRippleLayout = (MaterialRippleLayout) LayoutInflater.from(getActivity()).inflate(R.layout.pager_sliding_tab_custom_tab, parent, false);
            ((ImageView)materialRippleLayout.findViewById(R.id.image)).setImageResource(ICONS[position]);
            return materialRippleLayout;
        }
    }

}
