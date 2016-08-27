package com.transition.scorekeeper.mobile.view.fragment.interfaces;

import com.transition.scorekeeper.mobile.model.PlayerModel;

import java.util.Collection;

/**
 * @author diego.rotondale
 * @since 19/05/16
 */
public interface PlayersView extends LoadDataView {
    void renderPlayers(Collection<PlayerModel> playerModelCollection);
}
