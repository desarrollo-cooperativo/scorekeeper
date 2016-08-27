package com.transition.scorekeeper.mobile.navigation;

import android.content.Context;

import com.transition.scorekeeper.mobile.ViewConstants;
import com.transition.scorekeeper.mobile.model.MatchModel;
import com.transition.scorekeeper.mobile.view.activity.MatchActivity;
import com.transition.scorekeeper.mobile.view.activity.MatchesActivity;
import com.transition.scorekeeper.mobile.view.fragment.BaseFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author diego.rotondale
 * @since 18/05/16
 */
@Singleton
public class Navigator {
    @Inject
    public Navigator() {
    }

    public static void navigateToMatchList(Context context) {
        context.startActivity(MatchesActivity.getCallingIntent(context));
    }

    public static void navigateToMatch(BaseFragment fragment, MatchModel matchModel) {
        fragment.startActivityForResult(MatchActivity.getCallingIntent(fragment.getContext(), matchModel), ViewConstants.Result.MATCH);
    }
}
