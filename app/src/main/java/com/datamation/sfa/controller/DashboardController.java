package com.datamation.sfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.sfa.R;
import com.datamation.sfa.helpers.DatabaseHelper;
import com.datamation.sfa.helpers.SharedPref;
import com.datamation.sfa.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DashboardController {
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    Context context;
    private String TAG = "DashboardController";

    // Shared Preferences variables
    public static final String SETTINGS = "SETTINGS";


    public DashboardController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public Double getRepTarget(String repCode, int month, int year) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        double targetsum =0;

        Cursor cursor = dB.rawQuery("SELECT RepTarget as target from fTarget where RepCode='" + repCode + "' and Month = "+month+" and Year = "+year, null);

        while (cursor.moveToNext()) {

            targetsum = Double.parseDouble(String.valueOf(cursor.getInt(cursor.getColumnIndex("target"))));
            return targetsum;
        }

        cursor.close();
        dB.close();
        return null;

    }
    public int getProductiveCount() {
        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = dB.rawQuery("select count(refno) from FOrdHed where txndate = '" + curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDate) +"' group by DebCode", null);

        while (cursor.moveToNext()) {

            int result = cursor.getInt(0);

            if (result>0)
                return result;

        }
        cursor.close();
        dB.close();
        return 0;

    }

    public String getRoute(String repCode) {
        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));

        String curdate = curYear+"-"+ String.format("%02d", curMonth) + "-" + String.format("%02d", curDate);
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = dB.rawQuery("select RouteCode from fTourHed where '"+curdate+"' between DateFrom And DateTo and RepCode = '"+repCode+"'", null);

        while (cursor.moveToNext()) {

           String result = cursor.getString(cursor.getColumnIndex("RouteCode"));
            return result;

        }
        cursor.close();
        dB.close();
        return "";

    }

    public int getOutletCount(String route) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = dB.rawQuery("select count(DISTINCT DebCode) from fRouteDet where RouteCode = '"+route+"'", null);

        while (cursor.moveToNext()) {

            int result = cursor.getInt(0);

            if (result>0)
                return result;

        }
        cursor.close();
        dB.close();
        return 0;

    }
    public Double getMonthAchievement() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<String[]> list = new ArrayList<String[]>();

        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));

        try {


                double monthAchieve;


                Cursor cursor;

                    cursor = dB.rawQuery("select ifnull((sum(a.Amt)),0)  as totAmt from Forddet a where a.txndate LIKE '" + curYear + "-" + String.format("%02d", curMonth) + "-_%'", null);
                // Old 18-12-2017 cursor1 = dB.rawQuery("select ifnull((sum(a.qty)),0)  as totqty from ftransodet a, fitem b,ftransohed c where a.itemcode=b.itemcode and b.brandcode='" + arr[0] + "' and c.costcode='" + costCode + "' and c.refno=a.refno AND c.txndate LIKE '" + iYear + "-" + String.format("%02d", iMonth) + "-_%'", null);

                while (cursor.moveToNext()) {
                    monthAchieve = Double.parseDouble(cursor.getString(cursor.getColumnIndex("totAmt")));
                   return monthAchieve;
                }


                cursor.close();

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }

        return 0.0;

    }
}