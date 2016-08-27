package com.transition.scorekeeper.mobile.view.activity.interfaces;

import java.io.Serializable;

/**
 * @author diego.rotondale
 * @since 02/06/16
 */
public interface INeedUpdate {
    Serializable getUpdatedObject();

    boolean wasUpdated();
}
