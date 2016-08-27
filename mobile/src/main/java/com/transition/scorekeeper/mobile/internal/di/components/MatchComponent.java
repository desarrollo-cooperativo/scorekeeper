package com.transition.scorekeeper.mobile.internal.di.components;

import com.transition.scorekeeper.mobile.internal.di.PerActivity;
import com.transition.scorekeeper.mobile.internal.di.modules.ActivityModule;
import com.transition.scorekeeper.mobile.internal.di.modules.MatchModule;
import com.transition.scorekeeper.mobile.view.fragment.MatchFragment;
import com.transition.scorekeeper.mobile.view.fragment.MatchesFragment;
import com.transition.scorekeeper.mobile.view.fragment.dialog.PlayerDialog;
import com.transition.scorekeeper.mobile.view.fragment.dialog.PlayersDialog;

import dagger.Component;

/**
 * @author diego.rotondale
 * @since 19/05/16
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MatchModule.class})
public interface MatchComponent extends ActivityComponent {
    void inject(MatchesFragment matchesFragment);

    void inject(MatchFragment matchFragment);

    void inject(PlayerDialog playerDialog);

    void inject(PlayersDialog playersDialog);
}
