package io.github.codejanovic.discord.bot.listener.defaults;

import org.javacord.api.entity.activity.Activity;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.user.UserChangeActivityEvent;
import org.javacord.api.listener.user.UserChangeActivityListener;

import java.util.Optional;

public abstract class UserActivityChangedListener implements UserChangeActivityListener {

    @Override
    public void onUserChangeActivity(final UserChangeActivityEvent event) {
        onAnyActivityChanged(event.getNewActivity(), event.getOldActivity(), event.getUser());
        if (event.getNewActivity().isPresent()) {
            if (event.getOldActivity().isPresent()) {
                onActivityChanged(event.getOldActivity().get(), event.getNewActivity().get(), event.getUser());
                switch (event.getNewActivity().get().getType()) {
                    case PLAYING:
                        onChangedToPlaying(event.getNewActivity().get(), event.getOldActivity().get(), event.getUser());
                        break;
                    case STREAMING:
                        onChangedToStreaming(event.getNewActivity().get(), event.getOldActivity().get(), event.getUser());
                        break;
                    case LISTENING:
                        onChangedToListening(event.getNewActivity().get(), event.getOldActivity().get(), event.getUser());
                        break;
                    case WATCHING:
                        onChangedToWatching(event.getNewActivity().get(), event.getOldActivity().get(), event.getUser());
                        break;
                }

                switch (event.getOldActivity().get().getType()) {
                    case PLAYING:
                        onChangedFromPlaying(event.getNewActivity().get(), event.getOldActivity().get(), event.getUser());
                        break;
                    case STREAMING:
                        onChangedFromStreaming(event.getNewActivity().get(), event.getOldActivity().get(), event.getUser());
                        break;
                    case LISTENING:
                        onChangedFromListening(event.getNewActivity().get(), event.getOldActivity().get(), event.getUser());
                        break;
                    case WATCHING:
                        onChangedFromWatching(event.getNewActivity().get(), event.getOldActivity().get(), event.getUser());
                        break;
                }
            } else {
                onActivityStarted(event.getNewActivity().get(), event.getUser());
                switch (event.getNewActivity().get().getType()) {
                    case PLAYING:
                        onStartedPlaying(event.getNewActivity().get(), event.getUser());
                        break;
                    case STREAMING:
                        onStartedStreaming(event.getNewActivity().get(), event.getUser());
                        break;
                    case LISTENING:
                        onStartedListening(event.getNewActivity().get(), event.getUser());
                        break;
                    case WATCHING:
                        onStartedWatching(event.getNewActivity().get(), event.getUser());
                        break;
                }
            }
        } else {
            onActivityFinished(event.getOldActivity().get(), event.getUser());

            switch (event.getOldActivity().get().getType()) {
                case PLAYING:
                    onFinishedPlaying(event.getNewActivity().get(), event.getUser());
                    break;
                case STREAMING:
                    onFinishedStreaming(event.getNewActivity().get(), event.getUser());
                    break;
                case LISTENING:
                    onFinishedListening(event.getNewActivity().get(), event.getUser());
                    break;
                case WATCHING:
                    onFinishedWatching(event.getNewActivity().get(), event.getUser());
                    break;
            }
        }
    }

    protected void onFinishedWatching(final Activity finishedWatchActivity, final User user) {

    }

    ;

    protected void onFinishedListening(final Activity finishedMusicActivity, final User user) {

    }

    ;

    protected void onFinishedStreaming(final Activity finishedStreamActivity, final User user) {

    }

    ;

    protected void onFinishedPlaying(final Activity finishedGameActivity, final User user) {

    }

    ;

    protected void onChangedFromWatching(final Activity newActivity, final Activity finishedWatchActivity, final User user) {

    }

    ;

    protected void onChangedFromListening(final Activity newActivity, final Activity finishedMusicActivity, final User user) {

    }

    ;

    protected void onChangedFromStreaming(final Activity newActivity, final Activity finishedStreamActivity, final User user) {

    }

    ;

    protected void onChangedFromPlaying(final Activity newActivity, final Activity finishedGameActivity, final User user) {

    }

    ;

    protected void onAnyActivityChanged(final Optional<Activity> newActivity, final Optional<Activity> oldActivity, final User user) {

    }

    ;

    protected void onChangedToWatching(final Activity watchActivity, final Activity oldActivity, final User user) {

    }

    ;

    protected void onChangedToListening(final Activity musicActivity, final Activity oldActivity, final User user) {

    }

    ;

    protected void onChangedToStreaming(final Activity streamActivity, final Activity oldActivity, final User user) {

    }

    ;

    protected void onChangedToPlaying(final Activity gameActivity, final Activity oldActivity, final User user) {

    }

    ;

    protected void onStartedWatching(final Activity activity, final User user) {

    }

    ;

    protected void onStartedListening(final Activity activity, final User user) {

    }

    ;

    protected void onStartedStreaming(final Activity activity, final User user) {

    }

    ;

    protected void onStartedPlaying(final Activity activity, final User user) {

    }

    ;

    protected void onActivityFinished(final Activity activity, final User user) {

    }

    ;

    protected void onActivityChanged(final Activity oldActivity, final Activity newActivity, final User user) {

    }

    ;

    protected void onActivityStarted(final Activity activity, final User oldActivity) {

    }

    ;
}
