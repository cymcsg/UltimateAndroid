package com.marshalchen.common.uimodule.materialtabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.marshalchen.ultimateandroiduilollipop.R;

import java.util.LinkedList;
import java.util.List;


/**
 * A Toolbar that contains multiple tabs
 * @author neokree
 *
 */
@SuppressLint("InflateParams")
public class MaterialTabHost extends HorizontalScrollView {
	
	private int primaryColor;
	private int accentColor;
	private int textColor;
	private int iconColor;
	private List<MaterialTab> tabs;
    private List<Integer> tabsWidth;
	private boolean hasIcons;
    private boolean isTablet;
    private float density;
    private boolean scrollable;

    private LinearLayout layout;
	
	public MaterialTabHost(Context context) {
		this(context, null);
	}
	
	public MaterialTabHost(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public MaterialTabHost(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
        super.setOverScrollMode(this.OVER_SCROLL_NEVER);

        layout = new LinearLayout(context);
        this.addView(layout);

		// get primary and accent color from AppCompat theme
		Theme theme = context.getTheme();
		TypedValue typedValue = new TypedValue();
		theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
		primaryColor = typedValue.data;
		theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
		accentColor = typedValue.data;
		iconColor = Color.WHITE;
		textColor = Color.WHITE;
		
		// get attributes
		if(attrs != null) {
			TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialTabHost, 0, 0);
			
			try {
				hasIcons = a.getBoolean(R.styleable.MaterialTabHost_hasIcons, false);
			} finally {
				a.recycle();
			}
		}
		else {
			hasIcons = false;
		}

        this.isInEditMode();
        density = this.getResources().getDisplayMetrics().density;
        scrollable = false;
        isTablet = this.getResources().getBoolean(R.bool.isTablet);

		// initialize tabs list
		tabs = new LinkedList<MaterialTab>();
        tabsWidth = new LinkedList<Integer>();

        // set background color
        super.setBackgroundColor(primaryColor);
	}
	
	public void setPrimaryColor(int color) {
		this.primaryColor = color;
		
		for(MaterialTab tab : tabs) {
			tab.setPrimaryColor(color);
		}
	}
	
	public void setAccentColor(int color) {
		this.accentColor = color;
		
		for(MaterialTab tab : tabs) {
			tab.setAccentColor(color);
		}
	}
	
	public void setTextColor(int color) {
		this.textColor = color;
		
		for(MaterialTab tab : tabs) {
			tab.setTextColor(color);
		}
	}
	
	public void setIconColor(int color) {
		this.iconColor = color;
		
		for(MaterialTab tab : tabs) {
			tab.setIconColor(color);
		}
	}
	
	public void addTab(MaterialTab tab) {
        // add properties to tab
        tab.setAccentColor(accentColor);
        tab.setPrimaryColor(primaryColor);
        tab.setTextColor(textColor);
        tab.setIconColor(iconColor);
        tab.setPosition(tabs.size());

        // insert new tab in list
        tabs.add(tab);

        if(tabs.size() == 4) {
            // switch tabs to scrollable before its draw
            scrollable = true;

            if(isTablet)
                throw new RuntimeException("Tablet scrollable tabs are currently not supported");
        }
	}
	
	public MaterialTab newTab() {
		return new MaterialTab(this.getContext(),hasIcons);
	}
	
	public void setSelectedNavigationItem(int position) {
		if(position < 0 || position > tabs.size()) {
			throw new RuntimeException("Index overflow");
		} else {
			// tab at position will select, other will deselect
			for(int i = 0; i < tabs.size(); i++) {
				MaterialTab tab = tabs.get(i);
				
				if(i == position && !tab.isSelected()) {
					tab.activateTab();
				}
				else {
					tabs.get(i).disableTab();
				}
			}

            // move the tab if it is slidable
            if(scrollable) {
                int totalWidth = 0;//(int) ( 60 * density);
                for (int i = 0; i < position + 1; i++) {
                    totalWidth += tabsWidth.get(i);
                }
                totalWidth -= (int) (60 * density);
                this.smoothScrollTo(totalWidth, 0);
            }
		}
		
	}
	
	@Override
	public void removeAllViews() {
		for(int i = 0; i<tabs.size();i++) {
			tabs.remove(i);
		}
		layout.removeAllViews();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		layout.removeAllViews();
		
		if(!tabs.isEmpty()) {

            if(!scrollable) { // not scrollable tabs
                int tabWidth = this.getWidth() / tabs.size();

                // set params for resizing tabs width
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(tabWidth, LayoutParams.MATCH_PARENT);
                for (MaterialTab t : tabs) {
                    layout.addView(t.getView(), params);
                }

            }
            else { //scrollable tabs

                for(int i = 0;i< tabs.size();i++) {
                    LinearLayout.LayoutParams params;
                    MaterialTab tab = tabs.get(i);

                    int tabWidth = (int) (tab.getTabMinWidth() + (24 * density)); // 12dp + text/icon width + 12dp
                    params = new LinearLayout.LayoutParams(tabWidth, LayoutParams.MATCH_PARENT);

                    if(i == 0) {
                        // first tab
                        View view = new View(layout.getContext());
                        view.setMinimumWidth((int) (60 * density));
                        layout.addView(view);
                        tabsWidth.add((int) (60 * density));
                    }

                    params = new LinearLayout.LayoutParams(tabWidth, LayoutParams.MATCH_PARENT);
                    layout.addView(tab.getView(),params);
                    tabsWidth.add(tabWidth);

                    if(i == tabs.size() - 1) {
                        // last tab
                        View view = new View(layout.getContext());
                        view.setMinimumWidth((int) (60 * density));
                        layout.addView(view);
                        tabsWidth.add((int) (60 * density));
                    }
                }

            }
			this.setSelectedNavigationItem(0);
		}
	}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // setting measured width + 48 dp height
        this.setMeasuredDimension(this.getMeasuredWidth(), (int) (48 * density));

    }
}
