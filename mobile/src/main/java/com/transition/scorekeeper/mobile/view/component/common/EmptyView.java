package com.transition.scorekeeper.mobile.view.component.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import android.widget.TextView;

import com.transition.scorekeeper.R;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class EmptyView extends ScrollView {
    private TextView message;

    public EmptyView(Context context) {
        super(context);
        initialize();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public EmptyView initialize() {
        inflate(getContext(), R.layout.view_empty, this);
        message = (TextView) findViewById(R.id.empty_message);
        return this;
    }

    public void setMessage(String messageValue) {
        message.setText(messageValue);
    }
}
