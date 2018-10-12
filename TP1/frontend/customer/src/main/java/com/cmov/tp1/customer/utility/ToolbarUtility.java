package com.cmov.tp1.customer.utility;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.cmov.tp1.customer.R;
import com.cmov.tp1.customer.activity.MyShowsActivity;
import com.cmov.tp1.customer.activity.ShowsActivity;
import com.cmov.tp1.customer.activity.VouchersActivity;

public abstract class ToolbarUtility {
    private static void changeActivity(Context context, Class activity) {
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }

    public static void setupToolbar(AppCompatActivity app){
        android.support.v7.widget.Toolbar toolbar = app.findViewById(R.id.toolbar);
        app.setSupportActionBar(toolbar);
        ActionBar actionbar = app.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    public static void setupDrawer(final AppCompatActivity app) {
        final DrawerLayout mDrawerLayout = app.findViewById(R.id.drawer_layout);
        final Resources res = app.getResources();
        NavigationView navigationView = app.findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        if(app instanceof ShowsActivity)
            menu.findItem(R.id.nav_menu_shows).setChecked(true);

        else if(app instanceof MyShowsActivity)
            menu.findItem(R.id.nav_menu_my_shows).setChecked(true);

        else if(app instanceof VouchersActivity)
            menu.findItem(R.id.nav_menu_vouchers).setChecked(true);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();

                        // cannot be done with a switch if Strings are not constants, i. e., cannot be taken from resources.
                        String selection = menuItem.getTitle().toString();

                        if(selection.equals(res.getString(R.string.drawer_main_menu_shows)) &&
                                !(app instanceof ShowsActivity))
                            changeActivity(app, ShowsActivity.class);

                        else if(selection.equals(res.getString(R.string.drawer_main_menu_my_shows)) &&
                                !(app instanceof MyShowsActivity))
                            changeActivity(app, MyShowsActivity.class);

                        else if(selection.equals(res.getString(R.string.drawer_main_menu_vouchers)) &&
                                !(app instanceof VouchersActivity))
                            changeActivity(app, VouchersActivity.class);

                        else System.out.println("Toolbar error: "+menuItem.getTitle());

                        return true;
                    }
                });
    }
}
