package com.datamation.sfa.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.datamation.sfa.R;
import com.datamation.sfa.helpers.SalesReturnResponseListener;
import com.datamation.sfa.salesreturn.SalesReturnHeader;
import com.datamation.sfa.salesreturn.SalesReturnDetails;
import com.datamation.sfa.salesreturn.SalesReturnSummary;

public class SalesReturnActivity extends AppCompatActivity implements SalesReturnResponseListener{

    ViewPager viewPager;
    private SalesReturnHeader salesRetrunHeader;
    private SalesReturnDetails salesReturnDetails;
    private SalesReturnSummary salesReturnSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_return);

        Toolbar toolbar = (Toolbar) findViewById(R.id.saleretrun_toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText("SALES RETURN");

        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.saleretrun_tab_strip);
        viewPager = (ViewPager) findViewById(R.id.saleretrun_viewpager);

        slidingTabStrip.setBackgroundColor(getResources().getColor(R.color.theme_color));
        slidingTabStrip.setTextColor(getResources().getColor(android.R.color.black));
        slidingTabStrip.setIndicatorColor(getResources().getColor(R.color.red_error));
        slidingTabStrip.setDividerColor(getResources().getColor(R.color.half_black));

        SalesReturnPagerAdapter adapter = new SalesReturnPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);
        slidingTabStrip.setViewPager(viewPager);
    }

    @Override
    public void moveBackTo_ret(int index) {
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
    }

    @Override
    public void moveNextTo_ret(int index) {

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

    }

    private class SalesReturnPagerAdapter extends FragmentPagerAdapter {

        private final String[] titles = {"RETURN HEADER", "RETURN DETAILS", "RETURN SUMMARY"};

        public SalesReturnPagerAdapter(FragmentManager fm) {
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
                    if(salesRetrunHeader == null) salesRetrunHeader = new SalesReturnHeader();
                    return salesRetrunHeader;
                case 1:
                    if(salesReturnDetails == null) salesReturnDetails = new SalesReturnDetails();
                    return salesReturnDetails;
                case 2:
                    if(salesReturnSummary == null) salesReturnSummary = new SalesReturnSummary();
                    return salesReturnSummary;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }


}