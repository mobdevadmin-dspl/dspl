package com.datamation.sfa.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.datamation.sfa.R;
import com.datamation.sfa.controller.InvDetController;
import com.datamation.sfa.helpers.PreSalesResponseListener;

import com.datamation.sfa.helpers.VanSalesResponseListener;
import com.datamation.sfa.model.Customer;

import com.datamation.sfa.model.FInvRHed;
import com.datamation.sfa.model.InvHed;
import com.datamation.sfa.vansale.InnerReturnDetails;
import com.datamation.sfa.vansale.VanSalesHeader;
import com.datamation.sfa.vansale.VanSalesOrderDetails;
import com.datamation.sfa.vansale.VanSalesSummary;

public class VanSalesActivity extends AppCompatActivity implements VanSalesResponseListener {
    private VanSalesHeader headerFragment;
    private VanSalesOrderDetails detailFragment;
    private VanSalesSummary salesManagementFragment;
    private InnerReturnDetails orderMainFragment;
    private ViewPager viewPager;
    Context context;
    boolean status = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_sales);

        Toolbar toolbar = (Toolbar) findViewById(R.id.presale_toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText("INVOICE");
        context = this;
        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.presale_tab_strip);
        viewPager = (ViewPager) findViewById(R.id.presale_viewpager);

        slidingTabStrip.setBackgroundColor(getResources().getColor(R.color.theme_color));
        slidingTabStrip.setTextColor(getResources().getColor(android.R.color.black));
        slidingTabStrip.setIndicatorColor(getResources().getColor(R.color.red_error));
        slidingTabStrip.setDividerColor(getResources().getColor(R.color.half_black));

        PreSalesPagerAdapter adapter = new PreSalesPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);
        slidingTabStrip.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 2)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_INNER_RETURN"));
                else if (position == 0)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_HEADER"));
                else if (position == 1)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_DETAILS"));
                else if (position == 3){
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("TAG_SUMMARY"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        status = new InvDetController(getApplicationContext()).isAnyActiveOrders();
    }

    private class PreSalesPagerAdapter extends FragmentPagerAdapter {

        private final String[] titles = {"HEADER", "INVOICE DETAILS", "INVOICE RETURN", "INVOICE SUMMARY"};

        public PreSalesPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    if(headerFragment == null) headerFragment = new VanSalesHeader();
                    return headerFragment;
                case 1:
                    if(detailFragment == null) detailFragment = new VanSalesOrderDetails();
                    return detailFragment;
                case 2:
                    if(orderMainFragment == null) orderMainFragment = new InnerReturnDetails();
                    return orderMainFragment;
                case 3:
                    if(salesManagementFragment == null) salesManagementFragment = new VanSalesSummary();
                    return salesManagementFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }

    @Override
    public void moveBackToCustomer(int index) {

        if (index == 0)
        {
            viewPager.setCurrentItem(0);
        }

        if (index == 1)
        {
            viewPager.setCurrentItem(1);
        }

        if (index == 2)
        {
            viewPager.setCurrentItem(2);
        }

        if (index == 3)
        {
            viewPager.setCurrentItem(3);
        }
    }

    @Override
    public void moveNextToCustomer(int index) {

        if (index == 0)
        {
            viewPager.setCurrentItem(0);
        }

        if (index == 1)
        {
            viewPager.setCurrentItem(1);
        }

        if (index == 2)
        {
            viewPager.setCurrentItem(2);
        }

        if (index == 3)
        {
            viewPager.setCurrentItem(3);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (status)
            viewPager.setCurrentItem(1);
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Toast.makeText(this,"Back button disabled until finish transaction",Toast.LENGTH_SHORT).show();
    }
}
