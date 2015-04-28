package com.fourreau.systemdroidmonitor.core.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Pierre on 28/04/2015.
 */
public class ListViewItemApp {
    public final Drawable icon;       // the drawable for the ListView item ImageView
    public final String title;        // the text for the ListView item title
    public final String description;  // the text for the ListView item description

    public ListViewItemApp(Drawable icon, String title, String description) {
        this.icon = icon;
        this.title = title;
        this.description = description;
    }
}
