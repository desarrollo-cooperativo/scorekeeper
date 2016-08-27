package com.transition.scorekeeper.mobile.view.fragment.interfaces;

import com.transition.scorekeeper.mobile.model.MatchModel;

import java.util.Collection;

/**
 * @author diego.rotondale
 * @since 19/05/16
 */
public interface MatchesView extends LoadDataView {
    void viewMatch(MatchModel matchModel);

    void renderMatches(Collection<MatchModel> matchModelCollection);

    void onFabClick();

    void createMatch(MatchModel matchModel);
}
