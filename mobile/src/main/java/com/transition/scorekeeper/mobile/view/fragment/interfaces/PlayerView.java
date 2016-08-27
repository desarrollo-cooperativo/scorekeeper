package com.transition.scorekeeper.mobile.view.fragment.interfaces;

import com.transition.scorekeeper.mobile.model.PlayerModel;

/**
 * @author diego.rotondale
 * @since 19/05/16
 */
public interface PlayerView extends LoadDataView {
    void onPlayerCreated(PlayerModel playerModel);
}
