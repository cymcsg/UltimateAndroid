package com.marshalchen.common.demoofui;


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
import android.widget.*;
import com.marshalchen.common.demoofui.androidanimationsdemo.AndroidAnimationsDemoActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.marshalchen.common.commonUtils.basicUtils.BasicUtils;
import com.marshalchen.common.demoofui.ResideMenuDemo.ResideMenuActivity;
import com.marshalchen.common.demoofui.artbook.FreeFlowArtbookActivity;
import com.marshalchen.common.demoofui.circularProgressButton.CircularProgressButtonActivity;
import com.marshalchen.common.demoofui.dragSortListview.MultipleChoiceListView;
import com.marshalchen.common.demoofui.dragSortListview.SingleChoiceListView;
import com.marshalchen.common.demoofui.easingDemo.EasingActivity;
import com.marshalchen.common.demoofui.easyandroidanimations.EasyAnimationListActivity;
import com.marshalchen.common.demoofui.edgeeffectoverride.EdgeEffectActivity;
import com.marshalchen.common.demoofui.fadingactionbar.HomeActivity;
import com.marshalchen.common.demoofui.fancyCoverFlow.FancyCoverFlowActivity;
import com.marshalchen.common.demoofui.imageprocessingexample.ImageProcessingActivity;
import com.marshalchen.common.demoofui.imageprocessingexample.ImageProcessingVideotoImageActivity;
import com.marshalchen.common.demoofui.recyclerviewitemanimator.RecyclerViewItemAnimatorActivity;
import com.marshalchen.common.demoofui.sampleModules.GoogleProgressBarActivity;
import com.marshalchen.common.demoofui.quickreturnlistview.QuickReturnListViewActivity;
import com.marshalchen.common.demoofui.shapeimageview.ShapeImageViewActivity;
import com.marshalchen.common.demoofui.slider.SliderActivity;
import com.marshalchen.common.demoofui.staggeredgridview.StaggeredGridViewActivity;
import com.marshalchen.common.demoofui.standUpTimer.ConfigureStandupTimer;
import com.marshalchen.common.demoofui.superlistview.SuperListViewActivity;
import com.marshalchen.common.demoofui.swipelayoutdemo.SwipeLayoutActivity;
import com.marshalchen.common.ui.Typefaces;
import com.marshalchen.common.uimodule.arcmenu.ArcMenu;
import com.marshalchen.common.uimodule.enhanceListView.EnhancedListView;
import com.marshalchen.common.uimodule.passcodelock.PasscodePreferencesActivity;
import com.marshalchen.common.uimodule.titanic.Titanic;
import com.marshalchen.common.uimodule.titanic.TitanicTextView;
import com.marshalchen.common.uimodule.viewpagerindicator.CirclePageIndicator;
import com.marshalchen.common.demoofui.activityanimation.ActivityAnimationsActivity;
import com.marshalchen.common.demoofui.activitytransition.ActivityTransitionActivity;
import com.marshalchen.common.demoofui.cooldraganddrop.CoolDragAndDropActivity;
import com.marshalchen.common.demoofui.discrollview.DiscrollActivity;
import com.marshalchen.common.demoofui.dynamicgrid.example.DynamicGridActivity;
import com.marshalchen.common.demoofui.foldableLayout.activities.FoldableListActivity;
import com.marshalchen.common.demoofui.foldableLayout.activities.UnfoldableDetailsActivity;
import com.marshalchen.common.demoofui.foldingDrawer.FoldingActivitys;
import com.marshalchen.common.demoofui.listbuddies.ListBuddiesActivity;
import com.marshalchen.common.demoofui.listviewanimations.ListAnimationActivity;
import com.marshalchen.common.demoofui.listviewfilter.ui.ListViewFilterActivity;
import com.marshalchen.common.demoofui.parallaxscrollexample.ParallaxScrollActivity;
import com.marshalchen.common.demoofui.pullscrollview.PullScrollViewActivity;
import com.marshalchen.common.demoofui.rebound.ReboundActivity;
import com.marshalchen.common.demoofui.rebound.ReboundActivitySimple;
import com.marshalchen.common.demoofui.sampleModules.*;
import com.marshalchen.common.demoofui.showcaseview.ShowCaseSampleActivity;
import com.marshalchen.common.demoofui.stickygridheadersexample.StickGridItemListActivity;
import com.marshalchen.common.demoofui.jsoup.UtilsDemoActivity;
import com.marshalchen.common.demoofui.viewpagerSlidingTab.ViewpagerSlidingTabsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LandingFragment extends Fragment {
    View mainView;
    @InjectView(R.id.landingEnhanceListView)
    EnhancedListView landingEnhanceListView;
    List<HashMap<String, String>> enhanceList = new ArrayList<>();
    EnhancedListAdapter enhancedListAdapter;
    @InjectView(R.id.landingMallViewpager)
    ViewPager landingMallViewpager;
    CirclePageIndicator landingMallViewPagerIndicator;
    private List<View> viewpagerList = new ArrayList<View>();
    Titanic titanic;
    @InjectView(R.id.titanicTextView)
    TitanicTextView titanicTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.landing_fragment, container, false);
        ButterKnife.inject(this, mainView);
        initTitanicView();
        initEnhanceList();
        initViewPager();
        //CryptoUtils.testCrypto(getActivity());
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
        titanicTextView.setTypeface(Typefaces.get(getActivity(), "Satisfy-Regular.ttf"));
        //  HandlerUtils.sendMessageHandlerDelay(titanicHandler, 0, 3000);
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
        HashMap<String, String> map = new HashMap<>();

        map.put("name", "SmoothBar");
        map.put("detail", "try");
        map.put("subname", "to ProgressBar");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "DynamicGrid");
        map.put("detail", "try");
        map.put("subname", "to DynamicGrid");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Cool Drag Drop ");
        map.put("detail", "try");
        map.put("subname", "to DragDrop");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Stickyheader");
        map.put("detail", "try");
        map.put("subname", "to StickyHeader");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ListViewFilter");
        map.put("detail", "try");
        map.put("subname", "to ListViewFilter");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FlipView");
        map.put("detail", "try");
        map.put("subname", "to FlipView");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Photoview");
        map.put("detail", "try");
        map.put("subname", "to Photoview");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FoldingActivity");
        map.put("detail", "try");
        map.put("subname", "to Folding");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "CircularBar");
        map.put("detail", "try");
        map.put("subname", "to CircularBar");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FoldableList");
        map.put("detail", "try");
        map.put("subname", "to FoldableList");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "UnfoldableList");
        map.put("detail", "try");
        map.put("subname", "to UnfoldableList");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "TimeSquare");
        map.put("detail", "try");
        map.put("subname", "to TimeSquare");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FaceCrop");
        map.put("detail", "try");
        map.put("subname", "to FaceCrop");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "KenBurns");
        map.put("detail", "try");
        map.put("subname", "to KenBurns");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Discroll");
        map.put("detail", "try");
        map.put("subname", "to Discroll");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Panning");
        map.put("detail", "try");
        map.put("subname", "to Panning");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ParScroll");
        map.put("detail", "try");
        map.put("subname", "to ParScroll");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ProgressWheel");
        map.put("detail", "try");
        map.put("subname", "to Wheel");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ViewTabs");
        map.put("detail", "try");
        map.put("subname", "to ViewTabs");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Rebound");
        map.put("detail", "try");
        map.put("subname", "Rebound");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ReboundSimple");
        map.put("detail", "try");
        map.put("subname", "Rebound");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ListAnim");
        map.put("detail", "try");
        map.put("subname", "ListAnim");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "PullSplash");
        map.put("detail", "try");
        map.put("subname", "PullSplash");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Jsoup");
        map.put("detail", "try");
        map.put("subname", "Jsoup");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ViewServer");
        map.put("detail", "try");
        map.put("subname", "ViewServer");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "PassCode");
        map.put("detail", "try");
        map.put("subname", "PassCode");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "GestureTouch");
        map.put("detail", "try");
        map.put("subname", "GestureTouch");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "act_anim");
        map.put("detail", "try");
        map.put("subname", "act_anim");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "pullscrollview");
        map.put("detail", "try");
        map.put("subname", "pull");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "card swiped");
        map.put("detail", "try");
        map.put("subname", "card swiped");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ShowCase");
        map.put("detail", "try");
        map.put("subname", "ShowCase");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ListBuddies");
        map.put("detail", "try");
        map.put("subname", "ListBuddies");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ActivityTransition");
        map.put("detail", "try");
        map.put("subname", "act");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "AutoFit");
        map.put("detail", "try");
        map.put("subname", "AutoFit");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Draggable");
        map.put("detail", "try");
        map.put("subname", "Draggable");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FloatLayout");
        map.put("detail", "try");
        map.put("subname", "FloatLayout");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "WireFrame");
        map.put("detail", "try");
        map.put("subname", "WireFrame");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Trigle");
        map.put("detail", "try");
        map.put("subname", "Frame");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "RiseNumber");
        map.put("detail", "try");
        map.put("subname", "RiseNumber");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Nifty");
        map.put("detail", "try");
        map.put("subname", "Nifty");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Icons");
        map.put("detail", "try");
        map.put("subname", "Icons");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "EdgeEffect");
        map.put("detail", "try");
        map.put("subname", "EdgeEffect");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ViewPager");
        map.put("detail", "try");
        map.put("subname", "Transform");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "StandUpTimer");
        map.put("detail", "try");
        map.put("subname", "Timer");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "CircularProgress");
        map.put("detail", "try");
        map.put("subname", "Button");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "JazzyViewPager");
        map.put("detail", "try");
        map.put("subname", "Jazzy");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "PulltoZoom");
        map.put("detail", "try");
        map.put("subname", "ListView");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "QuickReturn");
        map.put("detail", "try");
        map.put("subname", "ListView");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "SuperListview");
        map.put("detail", "try");
        map.put("subname", "ListView");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Staggered");
        map.put("detail", "try");
        map.put("subname", "GridView");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "DropDownListview");
        map.put("detail", "try");
        map.put("subname", "ListView");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FlowTextView");
        map.put("detail", "try");
        map.put("subname", "TextView");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Google");
        map.put("detail", "try");
        map.put("subname", "ProgressBar");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Number");
        map.put("detail", "try");
        map.put("subname", "ProgressBar");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FancyCoverFlow");
        map.put("detail", "try");
        map.put("subname", "FancyCoverFlow");
        enhanceList.add(map);

        map = new HashMap<>();
        map.put("name", "DragSort");
        map.put("detail", "try");
        map.put("subname", "Listview");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "DragSort");
        map.put("detail", "try");
        map.put("subname", "Listview");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FadingActionbar");
        map.put("detail", "try");
        map.put("subname", "FadingActionbar");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "SwipeFreshLayout");
        map.put("detail", "try");
        map.put("subname", "Layout");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "UltimateListview");
        map.put("detail", "try");
        map.put("subname", "Listview");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ResideMenu");
        map.put("detail", "try");
        map.put("subname", "ResideMenu");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Swipe");
        map.put("detail", "try");
        map.put("subname", "SwipeLayout");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Animations");
        map.put("detail", "try");
        map.put("subname", "Animations");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Easing");
        map.put("detail", "try");
        map.put("subname", "Activity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "JumpingBeans");
        map.put("detail", "try");
        map.put("subname", "Activity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "AndroidViewHover");
        map.put("detail", "try");
        map.put("subname", "Hover");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Slider");
        map.put("detail", "try");
        map.put("subname", "Slider");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "CalendarListView");
        map.put("detail", "try");
        map.put("subname", "CalendarListView");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "EasyAnimation");
        map.put("detail", "try");
        map.put("subname", "EasyAnimation");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ImageProcessing");
        map.put("detail", "try");
        map.put("subname", "ImageProcessing");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ImageProcessing");
        map.put("detail", "try");
        map.put("subname", "VideotoImage");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "TestHtml5Video");
        map.put("detail", "try");
        map.put("subname", "TestHtml5Video");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "CropImage");
        map.put("detail", "try");
        map.put("subname", "CropImage");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "CropperImage");
        map.put("detail", "try");
        map.put("subname", "CropperImage");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ImageFilter");
        map.put("detail", "try");
        map.put("subname", "ImageFilter");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "MotionSample");
        map.put("detail", "try");
        map.put("subname", "MotionSample");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ShapeImage");
        map.put("detail", "try");
        map.put("subname", "ShapeImage");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "CustomShapeImage");
        map.put("detail", "try");
        map.put("subname", "CustomShapeImage");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "MarqueeView");
        map.put("detail", "try");
        map.put("subname", "MarqueeView");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "SignaturePad");
        map.put("detail", "try");
        map.put("subname", "SignaturePad");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "DrawView");
        map.put("detail", "try");
        map.put("subname", "DrawviewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "AndroidWeekView");
        map.put("detail", "try");
        map.put("subname", "AndroidWeekView");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "AnimationFilcker");
        map.put("detail", "try");
        map.put("subname", "AnimationFilcker");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ArcMenu");
        map.put("detail", "try");
        map.put("subname", "ArcMenu");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ExpandCircle");
        map.put("detail", "try");
        map.put("subname", "ExpandCircle");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FreeFlow");
        map.put("detail", "try");
        map.put("subname", "ArtBook");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FreeFlow");
        map.put("detail", "try");
        map.put("subname", "PhotoGrid");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "RecylerView");
        map.put("detail", "try");
        map.put("subname", "RecylerViewSample");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "RecylerView");
        map.put("detail", "try");
        map.put("subname", "RecylerViewItem");
        enhanceList.add(map);


        enhancedListAdapter = new EnhancedListAdapter(enhanceList);
        landingEnhanceListView.setAdapter(enhancedListAdapter);
        landingEnhanceListView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView listView, int position) {
                enhancedListAdapter.remove(position);
                return null;
            }
        });
        landingEnhanceListView.setSwipingLayout(R.id.swiping_layout);
        landingEnhanceListView.setUndoStyle(null);

        landingEnhanceListView.enableSwipeToDismiss();
        landingEnhanceListView.setSwipeDirection(EnhancedListView.SwipeDirection.START);
        landingEnhanceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        BasicUtils.sendIntent(getActivity(), SmoothProgressBarActivity.class);
                        break;
                    case 1:
                        BasicUtils.sendIntent(getActivity(), DynamicGridActivity.class);
                        break;
                    case 2:
                        BasicUtils.sendIntent(getActivity(), CoolDragAndDropActivity.class);
                        break;
                    case 3:
                        BasicUtils.sendIntent(getActivity(), StickGridItemListActivity.class);
                        break;
                    case 4:
                        BasicUtils.sendIntent(getActivity(), ListViewFilterActivity.class);
                        break;
                    case 5:
                        BasicUtils.sendIntent(getActivity(), FlipViewActivity.class);
                        break;
                    case 6:
                        BasicUtils.sendIntent(getActivity(), PhotoViewActivity.class);
                        break;
                    case 7:
                        BasicUtils.sendIntent(getActivity(), FoldingActivitys.class);
                        break;
                    case 8:
                        BasicUtils.sendIntent(getActivity(), CircularBarActivity.class);
                        break;
                    case 9:
                        BasicUtils.sendIntent(getActivity(), FoldableListActivity.class);
                        break;
                    case 10:
                        BasicUtils.sendIntent(getActivity(), UnfoldableDetailsActivity.class);
                        break;
                    case 11:
                        BasicUtils.sendIntent(getActivity(), CalendarSquareActivity.class);
                        break;
                    case 12:
                        BasicUtils.sendIntent(getActivity(), FaceCropActivity.class);
                        break;
                    case 13:
                        BasicUtils.sendIntent(getActivity(), KenBurnsViewActivity.class);
                        break;
                    case 14:
                        BasicUtils.sendIntent(getActivity(), DiscrollActivity.class);
                        break;
                    case 15:
                        BasicUtils.sendIntent(getActivity(), PanningViewActivity.class);
                        break;
                    case 16:
                        BasicUtils.sendIntent(getActivity(), ParallaxScrollActivity.class);
                        break;
                    case 17:
                        BasicUtils.sendIntent(getActivity(), ProgressbarWheelActivity.class);
                        break;
                    case 18:
                        BasicUtils.sendIntent(getActivity(), ViewpagerSlidingTabsActivity.class);
                        break;
                    case 19:
                        BasicUtils.sendIntent(getActivity(), ReboundActivity.class);
                        break;
                    case 20:
                        BasicUtils.sendIntent(getActivity(), ReboundActivitySimple.class);
                        break;
                    case 21:
                        BasicUtils.sendIntent(getActivity(), ListAnimationActivity.class);
                        break;
                    case 22:
                        BasicUtils.sendIntent(getActivity(), PullSplashActivity.class);
                        break;
                    case 23:
                        BasicUtils.sendIntent(getActivity(), UtilsDemoActivity.class);
                        break;
                    case 24:
                        BasicUtils.sendIntent(getActivity(), ViewServerActivity.class);
                        break;
                    case 25:
                        BasicUtils.sendIntent(getActivity(), PasscodePreferencesActivity.class);
                        break;
                    case 26:
                        BasicUtils.sendIntent(getActivity(), GestureTouchActivity.class);
                        break;
                    case 27:
                        BasicUtils.sendIntent(getActivity(), ActivityAnimationsActivity.class);
                        break;
                    case 28:
                        BasicUtils.sendIntent(getActivity(), PullScrollViewActivity.class);
                        break;
                    case 29:
                        BasicUtils.sendIntent(getActivity(), CardsSwipedActivity.class);
                        break;
                    case 30:
                        BasicUtils.sendIntent(getActivity(), ShowCaseSampleActivity.class);
                        break;
                    case 31:
                        BasicUtils.sendIntent(getActivity(), ListBuddiesActivity.class);
                        break;
//                    case 32:
//                        BasicUtils.sendIntent(getActivity(), ResideMenuActivity.class);
//                        break;
                    case 32:
                        BasicUtils.sendIntent(getActivity(), ActivityTransitionActivity.class);
                        break;
                    case 33:
                        BasicUtils.sendIntent(getActivity(), AutofitTextViewActivity.class);
                        break;
                    case 34:
                        BasicUtils.sendIntent(getActivity(), DraggableGridViewPagerTestActivity.class);
                        break;
                    case 35:
                        BasicUtils.sendIntent(getActivity(), FloatLabelActivity.class);
                        break;
                    case 36:
                        BasicUtils.sendIntent(getActivity(), WireFrameActivity.class);
                        break;
                    case 37:
                        BasicUtils.sendIntent(getActivity(), TriangleFrameActivity.class);
                        break;
                    case 38:
                        BasicUtils.sendIntent(getActivity(), RiseNumberActivity.class);
                        break;
                    case 39:
                        BasicUtils.sendIntent(getActivity(), NiftyDialogActivity.class);
                        break;
                    case 40:
                        BasicUtils.sendIntent(getActivity(), DifferentIconsActivity.class);
                        break;
                    case 41:
                        BasicUtils.sendIntent(getActivity(), EdgeEffectActivity.class);
                        break;
                    case 42:
                        BasicUtils.sendIntent(getActivity(), ViewPagerTransformerActivity.class);
                        break;
                    case 43:
                        BasicUtils.sendIntent(getActivity(), ConfigureStandupTimer.class);
                        break;
                    case 44:
                        BasicUtils.sendIntent(getActivity(), CircularProgressButtonActivity.class);
                        break;
                    case 45:
                        BasicUtils.sendIntent(getActivity(), JazzyViewActivity.class);
                        break;
                    case 46:
                        BasicUtils.sendIntent(getActivity(), PulltoZoomListViewActivity.class);
                        break;
                    case 47:
                        BasicUtils.sendIntent(getActivity(), QuickReturnListViewActivity.class);
                        break;
                    case 48:
                        BasicUtils.sendIntent(getActivity(), SuperListViewActivity.class);
                        break;
                    case 49:
                        BasicUtils.sendIntent(getActivity(), StaggeredGridViewActivity.class);
                        break;
                    case 50:
                        BasicUtils.sendIntent(getActivity(), DropDownListViewDemo.class);
                        break;
                    case 51:
                        BasicUtils.sendIntent(getActivity(), FlowTextViewActivity.class);
                        break;
                    case 52:
                        BasicUtils.sendIntent(getActivity(), GoogleProgressBarActivity.class);
                        break;
                    case 53:
                        BasicUtils.sendIntent(getActivity(), NumberProgressBarActivity.class);
                        break;
                    case 54:
                        BasicUtils.sendIntent(getActivity(), FancyCoverFlowActivity.class);
                        break;
                    case 55:
                        BasicUtils.sendIntent(getActivity(), SingleChoiceListView.class);
                        break;
                    case 56:
                        BasicUtils.sendIntent(getActivity(), MultipleChoiceListView.class);
                        break;
                    case 57:
                        BasicUtils.sendIntent(getActivity(), HomeActivity.class);
                        break;
                    case 58:
                        BasicUtils.sendIntent(getActivity(), SwipeRefreshLayoutDemo.class);
                        break;
                    case 59:
                        BasicUtils.sendIntent(getActivity(), UltimateListviewActivity.class);
                        break;
                    case 60:
                        BasicUtils.sendIntent(getActivity(), ResideMenuActivity.class);
                        break;
                    case 61:
                        BasicUtils.sendIntent(getActivity(), SwipeLayoutActivity.class);
                        break;
                    case 62:
                        BasicUtils.sendIntent(getActivity(), AndroidAnimationsDemoActivity.class);
                        break;
                    case 63:
                        BasicUtils.sendIntent(getActivity(), EasingActivity.class);
                        break;
                    case 64:
                        BasicUtils.sendIntent(getActivity(), JumpingBeansActivity.class);
                        break;
                    case 65:
                        BasicUtils.sendIntent(getActivity(), AndroidViewHoverActivity.class);
                        break;
                    case 66:
                        BasicUtils.sendIntent(getActivity(), SliderActivity.class);
                        break;
                    case 67:
                        BasicUtils.sendIntent(getActivity(), CalendarListViewActivity.class);
                        break;
                    case 68:
                        BasicUtils.sendIntent(getActivity(), EasyAnimationListActivity.class);
                        break;
                    case 69:
                        BasicUtils.sendIntent(getActivity(), ImageProcessingActivity.class);
                        break;
                    case 70:
                        BasicUtils.sendIntent(getActivity(), ImageProcessingVideotoImageActivity.class);
                        break;
                    case 71:
                        BasicUtils.sendIntent(getActivity(), TestHTML5WebView.class);
                        break;
                    case 72:
                        BasicUtils.sendIntent(getActivity(), CropExample.class);
                        break;
                    case 73:
                        BasicUtils.sendIntent(getActivity(), CropperSample.class);
                        break;
                    case 74:
                        BasicUtils.sendIntent(getActivity(), ImageFilterActivity.class);
                        break;
                    case 75:
                        BasicUtils.sendIntent(getActivity(), MotionSampleActivity.class);
                        break;
                    case 76:
                        BasicUtils.sendIntent(getActivity(), ShapeImageViewActivity.class);
                        break;
                    case 77:
                        BasicUtils.sendIntent(getActivity(), CustomShapeImageViewActivity.class);
                        break;
                    case 78:
                        BasicUtils.sendIntent(getActivity(), MarqueeViewSample.class);
                        break;
                    case 79:
                        BasicUtils.sendIntent(getActivity(), SignaturePadActivity.class);
                        break;
                    case 80:
                        BasicUtils.sendIntent(getActivity(), DrawableActivity.class);
                        break;
                    case 81:
                        BasicUtils.sendIntent(getActivity(), AndroidWeekViewActivity.class);
                        break;
                    case 82:
                        BasicUtils.sendIntent(getActivity(), FilckerAnimationListActivity.class);
                        break;
                    case 83:
                        BasicUtils.sendIntent(getActivity(), ArcMenuActivity.class);
                        break;
                    case 84:
                        BasicUtils.sendIntent(getActivity(), ExpandCircleProgressExampleActivity.class);
                        break;
                    case 85:
                        BasicUtils.sendIntent(getActivity(), FreeFlowArtbookActivity.class);
                        break;
                    case 86:
                        BasicUtils.sendIntent(getActivity(), FreeFlowPhotoGridActivity.class);
                        break;
                    case 87:
                        BasicUtils.sendIntent(getActivity(), RecyclerViewSample.class);
                        break;
                    case 88:
                        BasicUtils.sendIntent(getActivity(), RecyclerViewItemAnimatorActivity.class);
                        break;


                }
            }
        });
    }


    private void initViewPager() {
        addLandingViewPager();
        landingMallViewpager.setAdapter(new CustomViewPagerAdapter(viewpagerList));
        landingMallViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                //setCurrentDot(i);

            }
        });
        landingMallViewPagerIndicator = (CirclePageIndicator) mainView.findViewById(R.id.landingMallViewPagerIndicator);
        landingMallViewPagerIndicator.setViewPager(landingMallViewpager);
//        final float density = getResources().getDisplayMetrics().density;
//        landingMallViewPagerIndicator.setBackgroundColor(0xffffff);
//        landingMallViewPagerIndicator.setRadius(5 * density);
//        landingMallViewPagerIndicator.setPageColor(getResources().getColor(R.color.black));
//        landingMallViewPagerIndicator.setFillColor(getResources().getColor(R.color.white));
//        landingMallViewPagerIndicator.setStrokeColor(getResources().getColor(R.color.black));
//        landingMallViewPagerIndicator.setStrokeWidth(1 * 1.0f);
    }


    private class EnhancedListAdapter extends BaseAdapter {
        //private List<String> mItems = new ArrayList<String>();
        private List<HashMap<String, String>> mLists = new ArrayList<HashMap<String, String>>();

        //    private EnhancedListAdapter(List<String> mItems) {
//        this.mItems = mItems;
//    }
        private EnhancedListAdapter(List<HashMap<String, String>> mLists) {
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
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         *                 data set.
         * @return The data at the specified position.
         */
        @Override
        public Object getItem(int position) {
            return mLists.get(position);
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_enhance, parent, false);
                // Clicking the delete icon, will read the position of the item stored in
                // the tag and delete it from the list. So we don't need to generate a new
                // onClickListener every time the content of this view changes.
                final View origView = convertView;
                convertView.findViewById(R.id.action_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // listViewHistory.delete(((ViewHolder) origView.getTag()).position);
                    }
                });

                holder = new ViewHolder();
                assert convertView != null;
                holder.mTextView = (TextView) convertView.findViewById(R.id.reacolhistextview);
                holder.mTextViewDir = (TextView) convertView.findViewById(R.id.reacolhistextviewDir);
                holder.mTextViewDetail = (TextView) convertView.findViewById(R.id.reacolhistextviewDetail);
                //holder.reacolReaLyout = (RelativeLayout) convertView.findViewById(R.id.reacolReaLyout);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.position = position;
            holder.mTextView.setText(mLists.get(position).get("name"));
            holder.mTextViewDir.setText(mLists.get(position).get("detail"));
            holder.mTextViewDetail.setText(mLists.get(position).get("subname"));

//
            return convertView;
        }

        private class ViewHolder {
            TextView mTextView;
            TextView mTextViewDir;
            TextView mTextViewDetail;
            RelativeLayout reacolReaLyout;
            int position;
        }

    }

    private void addLandingViewPager() {
        if (viewpagerList == null)
            viewpagerList = new ArrayList<View>();
        else viewpagerList.clear();
        LayoutInflater li = LayoutInflater.from(getActivity());
        for (int i = 0; i < 3; i++) {
            View viewPagerItem = getActivity().getLayoutInflater().inflate(R.layout.landing_viewpager_item,
                    null, false);
            ViewPagerViewHolder viewHolder = new ViewPagerViewHolder(viewPagerItem);
            viewHolder.landViewPagerImage.setImageResource(R.drawable.test);
            //  viewHolder.landViewPagerInfo1.setText("hh"+i);
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

        public CustomViewPagerAdapter(List viewpagerList) {
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
//            super.destroyItem(container, position, object);
            if (viewpagerList != null && viewpagerList.size() > position && viewpagerList.get(position) != null)
                container.removeView(viewpagerList.get(position));

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewpagerList.get(position), 0);
            //   return super.instantiateItem(container, position);
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
