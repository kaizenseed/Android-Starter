package com.example.androidstarter.custom;


import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.androidstarter.R;
import com.example.androidstarter.base.activities.WidgetActivity;
import com.example.androidstarter.data.models.User;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by samvedana on 26/1/18.
 */

public class MaterialDrawerHelper {
    public static Drawer configureStdNavDrawer(WidgetActivity activity,
                                             Toolbar toolbar, User user) {
        // this sample app has the same nav drawer across all activities that may exist
        // otherwise the variations can be configured using params and other techniques
        Drawer drawer;
        // Create the AccountHeader
        if (user != null) {
            //todo v v hacky behaviour
            Timber.d("configureStdNavDrawer : user with name %s", user.getName());
            AccountHeader headerResult = new AccountHeaderBuilder()
                    .withActivity(activity)
                    .withHeaderBackground(R.drawable.header)
                    .addProfiles(
                            new ProfileDrawerItem()
                                    .withName(user.getName())
                            //.withIcon(getResources().getDrawable(R.drawable.profile))
                    )
                    .withSelectionListEnabledForSingleProfile(false) //this removes the dropdown for cases where there is no profile switching
                    .build();

            drawer = new DrawerBuilder()
                    .withAccountHeader(headerResult)
                    .withActivity(activity)
                    .withToolbar(toolbar)
                    .build();
        }
        else {
            Timber.d("configureStdNavDrawer : Null User");
            drawer = new DrawerBuilder()
                    .withActivity(activity)
                    .withToolbar(toolbar)
                    .build();
        }

        //build drawer items list
        PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.menu_tasks)
                .withIcon(GoogleMaterial.Icon.gmd_check_circle);

        List<IDrawerItem> filters = new ArrayList<>();
        filters.add(new SecondaryDrawerItem()
                .withName(R.string.submenu_quick_tasks)
                .withLevel(2)
                .withIdentifier(2001)
        );
        filters.add(new SecondaryDrawerItem()
                .withName(R.string.submenu_short_tasks)
                .withLevel(2)
                .withIdentifier(2002)
        );
        filters.add(new SecondaryDrawerItem()
                .withName(R.string.submenu_big_tasks)
                .withLevel(2)
                .withIdentifier(2003)
        );
        filters.add(new SecondaryDrawerItem()
                .withName(R.string.submenu_unestimated_tasks)
                .withLevel(2)
                .withIdentifier(2004)
        );
        filters.add(new SecondaryDrawerItem()
                .withName(R.string.submenu_manage_filters)
                .withLevel(2)
                .withIcon(GoogleMaterial.Icon.gmd_settings)
                .withIdentifier(2005)
        );

        ExpandableDrawerItem item2 = new ExpandableDrawerItem()
                .withIdentifier(2)
                .withName(R.string.menu_filters)
                .withIdentifier(2)
                .withSelectable(false)
                .withIcon(GoogleMaterial.Icon.gmd_filter_list)
                .withSubItems(filters);

        PrimaryDrawerItem item3 = new PrimaryDrawerItem()
                .withIdentifier(3)
                .withName(R.string.menu_settings)
                .withIcon(GoogleMaterial.Icon.gmd_settings);
        //add items to drawer
        drawer.addItems(item1, item2, item3);

        //configure onDrawerItemClickListener and add to drawer

        Drawer.OnDrawerItemClickListener drawerItemClickListener =
                new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return false; //todo add logic here once appropriate fragments in place
                    }
                };
        drawer.setOnDrawerItemClickListener(drawerItemClickListener);

        return drawer;
    }
}
