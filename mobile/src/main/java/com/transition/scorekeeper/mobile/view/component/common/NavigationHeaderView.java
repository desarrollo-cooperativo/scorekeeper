package com.transition.scorekeeper.mobile.view.component.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.transition.scorekeeper.BuildConfig;
import com.transition.scorekeeper.R;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class NavigationHeaderView extends RelativeLayout {

    public NavigationHeaderView(Context context) {
        super(context);
        init(context);
    }

    public NavigationHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NavigationHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.view_navigation_header_content, this);
            TextView userName = (TextView) findViewById(R.id.nav_header_user_name);
            userName.setText(R.string.app_name);
            TextView versionName = (TextView) findViewById(R.id.nav_header_version_name);
            versionName.setText(BuildConfig.VERSION_NAME);
        }
    }
}
