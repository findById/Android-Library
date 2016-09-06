package org.cn.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.cn.android.orm.OrmFragment;
import org.cn.android.rpc.RpcFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private LayoutAdapter mLayoutAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Android-Library");
        setSupportActionBar(toolbar);

        initView();
        initData();
        requestData();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mLayoutAdapter = new LayoutAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mLayoutAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

    }

    private void initData() {

    }

    private void requestData() {

        List<TabItem> list = new ArrayList<>();
        list.add(new TabItem("MAIN", "MAIN"));
        list.add(new TabItem("ORM", "ORM"));
        list.add(new TabItem("RPC", "RPC"));

        mLayoutAdapter.updateData(list);
    }

    class LayoutAdapter extends FragmentPagerAdapter {

        private List<TabItem> mData = new ArrayList<>();

        public LayoutAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (mData.get(position).id) {
                case "ORM": {
                    fragment = new OrmFragment();
                    break;
                }
                case "RPC": {
                    fragment = new RpcFragment();
                    break;
                }
                default:
                    fragment = new MainFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mData.get(position).title;
        }

        public void updateData(List<TabItem> list) {
            mData.clear();
            if (list != null) {
                mData.addAll(list);
            }
            if (mData.size() <= 3) {
                mTabLayout.setTabMode(TabLayout.MODE_FIXED);
            }
            notifyDataSetChanged();
        }
    }

}
