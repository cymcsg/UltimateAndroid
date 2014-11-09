package com.marshalchen.common.demoofui;


import android.content.Intent;
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
import com.marshalchen.common.demoofui.BlurNavigationDrawer.BlurNavigationDrawerActivity;
import com.marshalchen.common.demoofui.androidanimationsdemo.AndroidAnimationsDemoActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.marshalchen.common.commonUtils.basicUtils.BasicUtils;
import com.marshalchen.common.demoofui.ResideMenuDemo.ResideMenuActivity;
import com.marshalchen.common.demoofui.artbook.FreeFlowArtbookActivity;
import com.marshalchen.common.demoofui.blurdialogfragment.BlurDialogActivity;
import com.marshalchen.common.demoofui.circularProgressButton.CircularProgressButtonActivity;
import com.marshalchen.common.demoofui.circularfloatingactionmenu.CircularFloatingActivity;
import com.marshalchen.common.demoofui.dragSortListview.MultipleChoiceListView;
import com.marshalchen.common.demoofui.dragSortListview.SingleChoiceListView;
import com.marshalchen.common.demoofui.easingDemo.EasingActivity;
import com.marshalchen.common.demoofui.easyandroidanimations.EasyAnimationListActivity;
import com.marshalchen.common.demoofui.edgeeffectoverride.EdgeEffectActivity;
import com.marshalchen.common.demoofui.fadingactionbar.HomeActivity;
import com.marshalchen.common.demoofui.fancyCoverFlow.FancyCoverFlowActivity;
import com.marshalchen.common.demoofui.imageprocessingexample.ImageProcessingActivity;
import com.marshalchen.common.demoofui.imageprocessingexample.ImageProcessingVideotoImageActivity;
import com.marshalchen.common.demoofui.materialmenu.MaterialMenuAppcompatActivity;
import com.marshalchen.common.demoofui.materialmenu.MaterialMenuToolbarActivity;
import com.marshalchen.common.demoofui.pulltozoomview.PullToZoomActivity;
import com.marshalchen.common.demoofui.recyclerplayground.RecyclerViewPlayGroundActivity;
import com.marshalchen.common.demoofui.recyclerviewitemanimator.RecyclerViewItemAnimatorActivity;
import com.marshalchen.common.demoofui.recyclerviewstickyheaders.RecyclerViewStickyHeadersActivity;
import com.marshalchen.common.demoofui.roundedimageview.RoundedImageViewActivity;
import com.marshalchen.common.demoofui.sampleModules.GoogleProgressBarActivity;
import com.marshalchen.common.demoofui.quickreturnlistview.QuickReturnListViewActivity;
import com.marshalchen.common.demoofui.shapeimageview.ShapeImageViewActivity;
import com.marshalchen.common.demoofui.slider.SliderActivity;
import com.marshalchen.common.demoofui.staggeredgridview.StaggeredGridViewActivity;
import com.marshalchen.common.demoofui.standUpTimer.ConfigureStandupTimer;
import com.marshalchen.common.demoofui.superlistview.SuperListViewActivity;
import com.marshalchen.common.demoofui.swipelayoutdemo.SwipeLayoutActivity;
import com.marshalchen.common.demoofui.twowayview.TwoWayViewActivity;
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
        map.put("class", "SmoothProgressBarActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "RippleEffect");
        map.put("detail", "try");
        map.put("subname", "RippleEffect");
        map.put("class", "RippleEffectActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Floating");
        map.put("detail", "try");
        map.put("subname", "FloatingActionButton");
        map.put("class", "FloatedActionButtonActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Floating");
        map.put("detail", "try");
        map.put("subname", "FloatingActionButton");
        map.put("class", "FloatingActionButtonDemo");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "CircularMenu");
        map.put("detail", "try");
        map.put("subname", "CircularMenu");
        map.put("class", "CircularFloatingActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "MaterialMenu");
        map.put("detail", "try");
        map.put("subname", "ToolbarActivity");
        map.put("class", "MaterialMenuToolbarActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "MaterialMenu");
        map.put("detail", "try");
        map.put("subname", "AppcompatActivity");
        map.put("class", "MaterialMenuAppcompatActivity");
        enhanceList.add(map);

        map = new HashMap<>();
        map.put("name", "DynamicGrid");
        map.put("detail", "try");
        map.put("subname", "to DynamicGrid");
        map.put("class", "DynamicGridActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Cool Drag Drop ");
        map.put("detail", "try");
        map.put("subname", "to DragDrop");
        map.put("class", "CoolDragAndDropActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Stickyheader");
        map.put("detail", "try");
        map.put("subname", "to StickyHeader");
        map.put("class", "StickGridItemListActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ListViewFilter");
        map.put("detail", "try");
        map.put("subname", "to ListViewFilter");
        map.put("class", "ListViewFilterActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FlipView");
        map.put("detail", "try");
        map.put("subname", "to FlipView");
        map.put("class", "FlipViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Photoview");
        map.put("detail", "try");
        map.put("subname", "to Photoview");
        map.put("class", "PhotoViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FoldingActivity");
        map.put("detail", "try");
        map.put("subname", "to Folding");
        map.put("class", "FoldingActivitys");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "CircularBar");
        map.put("detail", "try");
        map.put("subname", "to CircularBar");
        map.put("class", "CircularBarActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FoldableList");
        map.put("detail", "try");
        map.put("subname", "to FoldableList");
        map.put("class", "FoldableListActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "UnfoldableList");
        map.put("detail", "try");
        map.put("subname", "to UnfoldableList");
        map.put("class", "UnfoldableDetailsActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "TimeSquare");
        map.put("detail", "try");
        map.put("subname", "to TimeSquare");
        map.put("class", "CalendarSquareActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FaceCrop");
        map.put("detail", "try");
        map.put("subname", "to FaceCrop");
        map.put("class", "FaceCropActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "KenBurns");
        map.put("detail", "try");
        map.put("subname", "to KenBurns");
        map.put("class", "KenBurnsViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Discroll");
        map.put("detail", "try");
        map.put("subname", "to Discroll");
        map.put("class", "DiscrollActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Panning");
        map.put("detail", "try");
        map.put("subname", "to Panning");
        map.put("class", "PanningViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ParScroll");
        map.put("detail", "try");
        map.put("subname", "to ParScroll");
        map.put("class", "ParallaxScrollActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ProgressWheel");
        map.put("detail", "try");
        map.put("subname", "to Wheel");
        map.put("class", "ProgressbarWheelActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ViewTabs");
        map.put("detail", "try");
        map.put("subname", "to ViewTabs");
        map.put("class", "ViewpagerSlidingTabsActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Rebound");
        map.put("detail", "try");
        map.put("subname", "Rebound");
        map.put("class", "ReboundActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ReboundSimple");
        map.put("detail", "try");
        map.put("subname", "Rebound");
        map.put("class", "ReboundActivitySimple");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ListAnim");
        map.put("detail", "try");
        map.put("subname", "ListAnim");
        map.put("class", "ListAnimationActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "PullSplash");
        map.put("detail", "try");
        map.put("subname", "PullSplash");
        map.put("class", "PullSplashActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Jsoup");
        map.put("detail", "try");
        map.put("subname", "Jsoup");
        map.put("class", "UtilsDemoActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ViewServer");
        map.put("detail", "try");
        map.put("subname", "ViewServer");
        map.put("class", "ViewServerActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "PassCode");
        map.put("detail", "try");
        map.put("subname", "PassCode");
        map.put("class", "PasscodePreferencesActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "GestureTouch");
        map.put("detail", "try");
        map.put("subname", "GestureTouch");
        map.put("class", "GestureTouchActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "act_anim");
        map.put("detail", "try");
        map.put("subname", "act_anim");
        map.put("class", "ActivityAnimationsActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "pullscrollview");
        map.put("detail", "try");
        map.put("subname", "pull");
        map.put("class", "PullScrollViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "card swiped");
        map.put("detail", "try");
        map.put("subname", "card swiped");
        map.put("class", "CardsSwipedActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ShowCase");
        map.put("detail", "try");
        map.put("subname", "ShowCase");
        map.put("class", "ShowCaseSampleActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ListBuddies");
        map.put("detail", "try");
        map.put("subname", "ListBuddies");
        map.put("class", "ListBuddiesActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ActivityTransition");
        map.put("detail", "try");
        map.put("subname", "act");
        map.put("class", "ActivityTransitionActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "AutoFit");
        map.put("detail", "try");
        map.put("subname", "AutoFit");
        map.put("class", "AutofitTextViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Draggable");
        map.put("detail", "try");
        map.put("subname", "Draggable");
        map.put("class", "DraggableGridViewPagerTestActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FloatLayout");
        map.put("detail", "try");
        map.put("subname", "FloatLayout");
        map.put("class", "FloatLabelActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "WireFrame");
        map.put("detail", "try");
        map.put("subname", "WireFrame");
        map.put("class", "WireFrameActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Trigle");
        map.put("detail", "try");
        map.put("subname", "Frame");
        map.put("class", "TriangleFrameActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "RiseNumber");
        map.put("detail", "try");
        map.put("subname", "RiseNumber");
        map.put("class", "RiseNumberActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Nifty");
        map.put("detail", "try");
        map.put("subname", "Nifty");
        map.put("class", "NiftyDialogActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Icons");
        map.put("detail", "try");
        map.put("subname", "Icons");
        map.put("class", "DifferentIconsActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "EdgeEffect");
        map.put("detail", "try");
        map.put("subname", "EdgeEffect");
        map.put("class", "EdgeEffectActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ViewPager");
        map.put("detail", "try");
        map.put("subname", "Transform");
        map.put("class", "ViewPagerTransformerActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "StandUpTimer");
        map.put("detail", "try");
        map.put("subname", "Timer");
        map.put("class", "ConfigureStandupTimer");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "CircularProgress");
        map.put("detail", "try");
        map.put("subname", "Button");
        map.put("class", "CircularProgressButtonActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "JazzyViewPager");
        map.put("detail", "try");
        map.put("subname", "Jazzy");
        map.put("class", "JazzyViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "PulltoZoom");
        map.put("detail", "try");
        map.put("subname", "ListView");
        map.put("class", "PulltoZoomListViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "QuickReturn");
        map.put("detail", "try");
        map.put("subname", "ListView");
        map.put("class", "QuickReturnListViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "SuperListview");
        map.put("detail", "try");
        map.put("subname", "ListView");
        map.put("class", "SuperListViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Staggered");
        map.put("detail", "try");
        map.put("subname", "GridView");
        map.put("class", "StaggeredGridViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "DropDownListview");
        map.put("detail", "try");
        map.put("subname", "ListView");
        map.put("class", "DropDownListViewDemo");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FlowTextView");
        map.put("detail", "try");
        map.put("subname", "TextView");
        map.put("class", "FlowTextViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Google");
        map.put("detail", "try");
        map.put("subname", "ProgressBar");
        map.put("class", "GoogleProgressBarActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Number");
        map.put("detail", "try");
        map.put("subname", "ProgressBar");
        map.put("class", "NumberProgressBarActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FancyCoverFlow");
        map.put("detail", "try");
        map.put("subname", "FancyCoverFlow");
        map.put("class", "FancyCoverFlowActivity");
        enhanceList.add(map);

        map = new HashMap<>();
        map.put("name", "DragSort");
        map.put("detail", "try");
        map.put("subname", "Listview");
        map.put("class", "SingleChoiceListView");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "DragSort");
        map.put("detail", "try");
        map.put("subname", "Listview");
        map.put("class", "MultipleChoiceListView");
        enhanceList.add(map);
//        map = new HashMap<>();
//        map.put("name", "FadingActionbar");
//        map.put("detail", "try");
//        map.put("subname", "FadingActionbar");
//        map.put("class", "HomeActivity");
//        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "SwipeFreshLayout");
        map.put("detail", "try");
        map.put("subname", "Layout");
        map.put("class", "SwipeRefreshLayoutDemo");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "UltimateListview");
        map.put("detail", "try");
        map.put("subname", "Listview");
        map.put("class", "UltimateListviewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ResideMenu");
        map.put("detail", "try");
        map.put("subname", "ResideMenu");
        map.put("class", "ResideMenuActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Swipe");
        map.put("detail", "try");
        map.put("subname", "SwipeLayout");
        map.put("class", "SwipeLayoutActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Animations");
        map.put("detail", "try");
        map.put("subname", "Animations");
        map.put("class", "AndroidAnimationsDemoActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Easing");
        map.put("detail", "try");
        map.put("subname", "Activity");
        map.put("class", "EasingActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "JumpingBeans");
        map.put("detail", "try");
        map.put("subname", "Activity");
        map.put("class", "JumpingBeansActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "AndroidViewHover");
        map.put("detail", "try");
        map.put("subname", "Hover");
        map.put("class", "AndroidViewHoverActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "Slider");
        map.put("detail", "try");
        map.put("subname", "Slider");
        map.put("class", "SliderActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "CalendarListView");
        map.put("detail", "try");
        map.put("subname", "CalendarListView");
        map.put("class", "CalendarListViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "EasyAnimation");
        map.put("detail", "try");
        map.put("subname", "EasyAnimation");
        map.put("class", "EasyAnimationListActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ImageProcessing");
        map.put("detail", "try");
        map.put("subname", "ImageProcessing");
        map.put("class", "ImageProcessingActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ImageProcessing");
        map.put("detail", "try");
        map.put("subname", "VideotoImage");
        map.put("class", "ImageProcessingVideotoImageActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "TestHtml5Video");
        map.put("detail", "try");
        map.put("subname", "TestHtml5Video");
        map.put("class", "TestHTML5WebView");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "CropImage");
        map.put("detail", "try");
        map.put("subname", "CropImage");
        map.put("class", "");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "CropperImage");
        map.put("detail", "try");
        map.put("subname", "CropperImage");
        map.put("class", "CropExample");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ImageFilter");
        map.put("detail", "try");
        map.put("subname", "ImageFilter");
        map.put("class", "ImageFilterActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "MotionSample");
        map.put("detail", "try");
        map.put("subname", "MotionSample");
        map.put("class", "MotionSampleActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ShapeImage");
        map.put("detail", "try");
        map.put("subname", "ShapeImage");
        map.put("class", "ShapeImageViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "CustomShapeImage");
        map.put("detail", "try");
        map.put("subname", "CustomShapeImage");
        map.put("class", "CustomShapeImageViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "MarqueeView");
        map.put("detail", "try");
        map.put("subname", "MarqueeView");
        map.put("class", "MarqueeViewSample");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "SignaturePad");
        map.put("detail", "try");
        map.put("subname", "SignaturePad");
        map.put("class", "SignaturePadActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "DrawView");
        map.put("detail", "try");
        map.put("subname", "DrawviewActivity");
        map.put("class", "DrawableActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "WeekView");
        map.put("detail", "try");
        map.put("subname", "AndroidWeekView");
        map.put("class", "AndroidWeekViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "AnimationFilcker");
        map.put("detail", "try");
        map.put("subname", "AnimationFilcker");
        map.put("class", "FilckerAnimationListActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ArcMenu");
        map.put("detail", "try");
        map.put("subname", "ArcMenu");
        map.put("class", "ArcMenuActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "ExpandCircle");
        map.put("detail", "try");
        map.put("subname", "ExpandCircle");
        map.put("class", "ExpandCircleProgressExampleActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FreeFlow");
        map.put("detail", "try");
        map.put("subname", "ArtBook");
        map.put("class", "FreeFlowArtbookActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "FreeFlow");
        map.put("detail", "try");
        map.put("subname", "PhotoGrid");
        map.put("class", "FreeFlowPhotoGridActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "RecylerView");
        map.put("detail", "try");
        map.put("subname", "RecylerViewSample");
        map.put("class", "RecyclerViewSample");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "RecylerView");
        map.put("detail", "try");
        map.put("subname", "RecylerViewItem");
        map.put("class", "RecyclerViewItemAnimatorActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "PullToZoom");
        map.put("detail", "try");
        map.put("subname", "PullToZoomActivity");
        map.put("class", "PullToZoomActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "TwoWayView");
        map.put("detail", "try");
        map.put("subname", "TwoWayViewActivity");
        map.put("class", "TwoWayViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "RecyclerView");
        map.put("detail", "try");
        map.put("subname", "PlayGround");
        map.put("class", "RecyclerViewPlayGroundActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "StickyHeaders");
        map.put("detail", "try");
        map.put("subname", "RecyclerView");
        map.put("class", "RecyclerViewStickyHeadersActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "RoundedImage");
        map.put("detail", "try");
        map.put("subname", "RoundedImageViewActivity");
        map.put("class", "RoundedImageViewActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "BlurNavi");
        map.put("detail", "try");
        map.put("subname", "BlurNavigationDrawerActivity");
        map.put("class", "BlurNavigationDrawerActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "BlurDialog");
        map.put("detail", "try");
        map.put("subname", "BlurDialogActivity");
        map.put("class", "BlurDialogActivity");
        enhanceList.add(map);
        map = new HashMap<>();
        map.put("name", "CustomSwipe");
        map.put("detail", "try");
        map.put("subname", "CustomPullToRefreshActivity");
        map.put("class", "CustomPullToRefreshActivity");
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
                switch (enhanceList.get(position).get("class")) {
                    case "SmoothProgressBarActivity":
                        BasicUtils.sendIntent(getActivity(), SmoothProgressBarActivity.class);
                        break;
                    case "DynamicGridActivity":
                        BasicUtils.sendIntent(getActivity(), DynamicGridActivity.class);
                        break;
                    case "CoolDragAndDropActivity":
                        BasicUtils.sendIntent(getActivity(), CoolDragAndDropActivity.class);
                        break;
                    case "StickGridItemListActivity":
                        BasicUtils.sendIntent(getActivity(), StickGridItemListActivity.class);
                        break;
                    case "ListViewFilterActivity":
                        BasicUtils.sendIntent(getActivity(), ListViewFilterActivity.class);
                        break;
                    case "FlipViewActivity":
                        BasicUtils.sendIntent(getActivity(), FlipViewActivity.class);
                        break;
                    case "PhotoViewActivity":
                        BasicUtils.sendIntent(getActivity(), PhotoViewActivity.class);
                        break;
                    case "FoldingActivitys":
                        BasicUtils.sendIntent(getActivity(), FoldingActivitys.class);
                        break;
                    case "CircularBarActivity":
                        BasicUtils.sendIntent(getActivity(), CircularBarActivity.class);
                        break;
                    case "FoldableListActivity":
                        BasicUtils.sendIntent(getActivity(), FoldableListActivity.class);
                        break;
                    case "UnfoldableDetailsActivity":
                        BasicUtils.sendIntent(getActivity(), UnfoldableDetailsActivity.class);
                        break;
                    case "CalendarSquareActivity":
                        BasicUtils.sendIntent(getActivity(), CalendarSquareActivity.class);
                        break;
                    case "FaceCropActivity":
                        BasicUtils.sendIntent(getActivity(), FaceCropActivity.class);
                        break;
                    case "KenBurnsViewActivity":
                        BasicUtils.sendIntent(getActivity(), KenBurnsViewActivity.class);
                        break;
                    case "DiscrollActivity":
                        BasicUtils.sendIntent(getActivity(), DiscrollActivity.class);
                        break;
                    case "PanningViewActivity":
                        BasicUtils.sendIntent(getActivity(), PanningViewActivity.class);
                        break;
                    case "ParallaxScrollActivity":
                        BasicUtils.sendIntent(getActivity(), ParallaxScrollActivity.class);
                        break;
                    case "ProgressbarWheelActivity":
                        BasicUtils.sendIntent(getActivity(), ProgressbarWheelActivity.class);
                        break;
                    case "ViewpagerSlidingTabsActivity":
                        BasicUtils.sendIntent(getActivity(), ViewpagerSlidingTabsActivity.class);
                        break;
                    case "ReboundActivity":
                        BasicUtils.sendIntent(getActivity(), ReboundActivity.class);
                        break;
                    case "ReboundActivitySimple":
                        BasicUtils.sendIntent(getActivity(), ReboundActivitySimple.class);
                        break;
                    case "ListAnimationActivity":
                        BasicUtils.sendIntent(getActivity(), ListAnimationActivity.class);
                        break;
                    case "PullSplashActivity":
                        BasicUtils.sendIntent(getActivity(), PullSplashActivity.class);
                        break;
                    case "UtilsDemoActivity":
                        BasicUtils.sendIntent(getActivity(), UtilsDemoActivity.class);
                        break;
                    case "ViewServerActivity":
                        BasicUtils.sendIntent(getActivity(), ViewServerActivity.class);
                        break;
                    case "PasscodePreferencesActivity":
                        BasicUtils.sendIntent(getActivity(), PasscodePreferencesActivity.class);
                        break;
                    case "GestureTouchActivity":
                        BasicUtils.sendIntent(getActivity(), GestureTouchActivity.class);
                        break;
                    case "ActivityAnimationsActivity":
                        BasicUtils.sendIntent(getActivity(), ActivityAnimationsActivity.class);
                        break;
                    case "PullScrollViewActivity":
                        BasicUtils.sendIntent(getActivity(), PullScrollViewActivity.class);
                        break;
                    case "CardsSwipedActivity":
                        BasicUtils.sendIntent(getActivity(), CardsSwipedActivity.class);
                        break;
                    case "ShowCaseSampleActivity":
                        BasicUtils.sendIntent(getActivity(), ShowCaseSampleActivity.class);
                        break;
                    case "ListBuddiesActivity":
                        BasicUtils.sendIntent(getActivity(), ListBuddiesActivity.class);
                        break;
//                    case 32:
//                        BasicUtils.sendIntent(getActivity(), ResideMenuActivity.class);
//                        break;
                    case "ActivityTransitionActivity":
                        BasicUtils.sendIntent(getActivity(), ActivityTransitionActivity.class);
                        break;
                    case "AutofitTextViewActivity":
                        BasicUtils.sendIntent(getActivity(), AutofitTextViewActivity.class);
                        break;
                    case "DraggableGridViewPagerTestActivity":
                        BasicUtils.sendIntent(getActivity(), DraggableGridViewPagerTestActivity.class);
                        break;
                    case "FloatLabelActivity":
                        BasicUtils.sendIntent(getActivity(), FloatLabelActivity.class);
                        break;
                    case "WireFrameActivity":
                        BasicUtils.sendIntent(getActivity(), WireFrameActivity.class);
                        break;
                    case "TriangleFrameActivity":
                        BasicUtils.sendIntent(getActivity(), TriangleFrameActivity.class);
                        break;
                    case "RiseNumberActivity":
                        BasicUtils.sendIntent(getActivity(), RiseNumberActivity.class);
                        break;
                    case "NiftyDialogActivity":
                        BasicUtils.sendIntent(getActivity(), NiftyDialogActivity.class);
                        break;
                    case "DifferentIconsActivity":
                        BasicUtils.sendIntent(getActivity(), DifferentIconsActivity.class);
                        break;
                    case "EdgeEffectActivity":
                        BasicUtils.sendIntent(getActivity(), EdgeEffectActivity.class);
                        break;
                    case "ViewPagerTransformerActivity":
                        BasicUtils.sendIntent(getActivity(), ViewPagerTransformerActivity.class);
                        break;
                    case "ConfigureStandupTimer":
                        BasicUtils.sendIntent(getActivity(), ConfigureStandupTimer.class);
                        break;
                    case "CircularProgressButtonActivity":
                        BasicUtils.sendIntent(getActivity(), CircularProgressButtonActivity.class);
                        break;
                    case "JazzyViewActivity":
                        BasicUtils.sendIntent(getActivity(), JazzyViewActivity.class);
                        break;
                    case "PulltoZoomListViewActivity":
                        BasicUtils.sendIntent(getActivity(), PulltoZoomListViewActivity.class);
                        break;
                    case "QuickReturnListViewActivity":
                        BasicUtils.sendIntent(getActivity(), QuickReturnListViewActivity.class);
                        break;
                    case "SuperListViewActivity":
                        BasicUtils.sendIntent(getActivity(), SuperListViewActivity.class);
                        break;
                    case "StaggeredGridViewActivity":
                        BasicUtils.sendIntent(getActivity(), StaggeredGridViewActivity.class);
                        break;
                    case "DropDownListViewDemo":
                        BasicUtils.sendIntent(getActivity(), DropDownListViewDemo.class);
                        break;
                    case "FlowTextViewActivity":
                        BasicUtils.sendIntent(getActivity(), FlowTextViewActivity.class);
                        break;
                    case "GoogleProgressBarActivity":
                        BasicUtils.sendIntent(getActivity(), GoogleProgressBarActivity.class);
                        break;
                    case "NumberProgressBarActivity":
                        BasicUtils.sendIntent(getActivity(), NumberProgressBarActivity.class);
                        break;
                    case "FancyCoverFlowActivity":
                        BasicUtils.sendIntent(getActivity(), FancyCoverFlowActivity.class);
                        break;
                    case "SingleChoiceListView":
                        BasicUtils.sendIntent(getActivity(), SingleChoiceListView.class);
                        break;
                    case "MultipleChoiceListView":
                        BasicUtils.sendIntent(getActivity(), MultipleChoiceListView.class);
                        break;
                    case "HomeActivity":
                        BasicUtils.sendIntent(getActivity(), HomeActivity.class);
                        break;
                    case "SwipeRefreshLayoutDemo":
                        BasicUtils.sendIntent(getActivity(), SwipeRefreshLayoutDemo.class);
                        break;
                    case "UltimateListviewActivity":
                        BasicUtils.sendIntent(getActivity(), UltimateListviewActivity.class);
                        break;
                    case "ResideMenuActivity":
                        BasicUtils.sendIntent(getActivity(), ResideMenuActivity.class);
                        break;
                    case "SwipeLayoutActivity":
                        BasicUtils.sendIntent(getActivity(), SwipeLayoutActivity.class);
                        break;
                    case "AndroidAnimationsDemoActivity":
                        BasicUtils.sendIntent(getActivity(), AndroidAnimationsDemoActivity.class);
                        break;
                    case "EasingActivity":
                        BasicUtils.sendIntent(getActivity(), EasingActivity.class);
                        break;
                    case "JumpingBeansActivity":
                        BasicUtils.sendIntent(getActivity(), JumpingBeansActivity.class);
                        break;
                    case "AndroidViewHoverActivity":
                        BasicUtils.sendIntent(getActivity(), AndroidViewHoverActivity.class);
                        break;
                    case "SliderActivity":
                        BasicUtils.sendIntent(getActivity(), SliderActivity.class);
                        break;
                    case "CalendarListViewActivity":
                        BasicUtils.sendIntent(getActivity(), CalendarListViewActivity.class);
                        break;
                    case "EasyAnimationListActivity":
                        BasicUtils.sendIntent(getActivity(), EasyAnimationListActivity.class);
                        break;
                    case "ImageProcessingActivity":
                        BasicUtils.sendIntent(getActivity(), ImageProcessingActivity.class);
                        break;
                    case "ImageProcessingVideotoImageActivity":
                        BasicUtils.sendIntent(getActivity(), ImageProcessingVideotoImageActivity.class);
                        break;
                    case "TestHTML5WebView":
                        BasicUtils.sendIntent(getActivity(), TestHTML5WebView.class);
                        break;
                    case "CropExample":
                        BasicUtils.sendIntent(getActivity(), CropExample.class);
                        break;
                    case "CropperSample":
                        BasicUtils.sendIntent(getActivity(), CropperSample.class);
                        break;
                    case "ImageFilterActivity":
                        BasicUtils.sendIntent(getActivity(), ImageFilterActivity.class);
                        break;
                    case "MotionSampleActivity":
                        BasicUtils.sendIntent(getActivity(), MotionSampleActivity.class);
                        break;
                    case "ShapeImageViewActivity":
                        BasicUtils.sendIntent(getActivity(), ShapeImageViewActivity.class);
                        break;
                    case "CustomShapeImageViewActivity":
                        BasicUtils.sendIntent(getActivity(), CustomShapeImageViewActivity.class);
                        break;
                    case "MarqueeViewSample":
                        BasicUtils.sendIntent(getActivity(), MarqueeViewSample.class);
                        break;
                    case "SignaturePadActivity":
                        BasicUtils.sendIntent(getActivity(), SignaturePadActivity.class);
                        break;
                    case "DrawableActivity":
                        BasicUtils.sendIntent(getActivity(), DrawableActivity.class);
                        break;
                    case "AndroidWeekViewActivity":
                        BasicUtils.sendIntent(getActivity(), AndroidWeekViewActivity.class);
                        break;
                    case "FilckerAnimationListActivity":
                        BasicUtils.sendIntent(getActivity(), FilckerAnimationListActivity.class);
                        break;
                    case "ArcMenuActivity":
                        BasicUtils.sendIntent(getActivity(), ArcMenuActivity.class);
                        break;
                    case "ExpandCircleProgressExampleActivity":
                        BasicUtils.sendIntent(getActivity(), ExpandCircleProgressExampleActivity.class);
                        break;
                    case "FreeFlowArtbookActivity":
                        BasicUtils.sendIntent(getActivity(), FreeFlowArtbookActivity.class);
                        break;
                    case "FreeFlowPhotoGridActivity":
                        BasicUtils.sendIntent(getActivity(), FreeFlowPhotoGridActivity.class);
                        break;
                    case "RecyclerViewSample":
                        BasicUtils.sendIntent(getActivity(), RecyclerViewSample.class);
                        break;
                    case "RecyclerViewItemAnimatorActivity":
                        BasicUtils.sendIntent(getActivity(), RecyclerViewItemAnimatorActivity.class);
                        break;
                    case "RippleEffectActivity":
                        BasicUtils.sendIntent(getActivity(), RippleEffectActivity.class);
                        break;
                    case "FloatedActionButtonActivity":
                        BasicUtils.sendIntent(getActivity(), FloatingActionButtonActivity.class);
                        break;
                    case "FloatingActionButtonDemo":
                        BasicUtils.sendIntent(getActivity(), FloatingActionButtonDemo.class);
                        break;
                    case "CircularFloatingActivity":
                        BasicUtils.sendIntent(getActivity(), CircularFloatingActivity.class);
                        break;
                    case "MaterialMenuToolbarActivity":
                        BasicUtils.sendIntent(getActivity(), MaterialMenuToolbarActivity.class);
                        break;
                    case "MaterialMenuAppcompatActivity":
                        BasicUtils.sendIntent(getActivity(), MaterialMenuAppcompatActivity.class);
                        break;
                    case "PullToZoomActivity":
                        BasicUtils.sendIntent(getActivity(), PullToZoomActivity.class);
                        break;
                    case "TwoWayViewActivity":
                        BasicUtils.sendIntent(getActivity(), TwoWayViewActivity.class);
                        break;
                    case "RecyclerViewPlayGroundActivity":
                        BasicUtils.sendIntent(getActivity(), RecyclerViewPlayGroundActivity.class);
                        break;
                    case "RecyclerViewStickyHeadersActivity":
                        BasicUtils.sendIntent(getActivity(), RecyclerViewStickyHeadersActivity.class);
                        break;
                    case "RoundedImageViewActivity":
                        BasicUtils.sendIntent(getActivity(), RoundedImageViewActivity.class);
                        break;
                    case "BlurNavigationDrawerActivity":
                        BasicUtils.sendIntent(getActivity(), BlurNavigationDrawerActivity.class);
                        break;
                    case "BlurDialogActivity":
                        BasicUtils.sendIntent(getActivity(), BlurDialogActivity.class);
                        break;
                    case "CustomPullToRefreshActivity":
                        BasicUtils.sendIntent(getActivity(), CustomPullToRefreshActivity.class);
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
            viewpagerList.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MaterialActivity.class);
                    startActivity(intent);
                }
            });
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
