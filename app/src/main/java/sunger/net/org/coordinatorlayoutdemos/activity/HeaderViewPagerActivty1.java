package sunger.net.org.coordinatorlayoutdemos.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sunger.net.org.coordinatorlayoutdemos.R;
import sunger.net.org.coordinatorlayoutdemos.adapter.MainTabAdapter;
import sunger.net.org.coordinatorlayoutdemos.fragment.NestedscrollFragment;
import sunger.net.org.coordinatorlayoutdemos.fragment.RecyclerFragment;
import sunger.net.org.coordinatorlayoutdemos.widget.ScrollFeedbackRecyclerView;

/**
 * Created by sunger on 15/12/15.
 */
public class HeaderViewPagerActivty1 extends FragmentActivity implements SwipeRefreshLayout.OnRefreshListener, AppBarLayout.OnOffsetChangedListener, ScrollFeedbackRecyclerView.Callbacks{
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MainTabAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AppBarLayout appBarLayout;
    private RecyclerFragment fragment;
//    private Toolbar toolbar;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_viewpager1);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);
        collapsingToolbar.setTitleEnabled(false);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, 100);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //set the toolbar
//        int toolbar_hight = Utils.getToolbarHeight(this) * 2;
//        CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
//        params.height = toolbar_hight;
//        toolbar.setLayoutParams(params);
//
//        setUpCommonBackTooblBar(R.id.toolbar, "HeaderViewPager");
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);

        List<Fragment> fragments = new ArrayList<>();
        fragment = new RecyclerFragment();
        fragments.add(fragment);
        fragments.add(new NestedscrollFragment());
        fragments.add(new NestedscrollFragment());

        List<String> titles = new ArrayList<>();
        titles.add("Item1");
        titles.add("Item2");
        titles.add("Item3");

        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));

        mAdapter = new MainTabAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.setTabsFromPagerAdapter(mAdapter);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(this).inflate(R.layout.bbs_tab_view, mTabLayout, false);

            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tv_tab_0);
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);
//            tab.select();
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HeaderViewPagerActivty1.this, "aaaaaaaaaaaaa", Toast.LENGTH_SHORT).show();
            }
        });

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTabLayout.getTabAt(0).getCustomView().setSelected(true);
                    }
                });
            }
        });
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        mSwipeRefreshLayout.setEnabled(i == 0);
        float alpha = (float) Math.abs(i) / (float) appBarLayout.getTotalScrollRange() * 1.0f;
//        toolbar.setAlpha(alpha);
    }


    @Override
    public void onRefresh() {
        if(mViewPager.getCurrentItem()==0) {
            fragment.refresh();
        }
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);

    }

    @Override
    protected void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    @Override
    public boolean isAppBarCollapsed() {
        int ay = (int)appBarLayout.getY();
        int ah = appBarLayout.getHeight();
        final int appBarVisibleHeight = ay + ah;

//        final int toolbarHeight = toolbar.getHeight();
//        boolean flag = (appBarVisibleHeight == toolbarHeight);
        Log.i("aaaaaaaaaa", appBarVisibleHeight + " xx " + mTabLayout.getHeight());
        return (appBarVisibleHeight == mTabLayout.getHeight());
    }

    @Override
    public void setExpanded(boolean expanded) {
        appBarLayout.setExpanded(expanded, true);
    }
}
