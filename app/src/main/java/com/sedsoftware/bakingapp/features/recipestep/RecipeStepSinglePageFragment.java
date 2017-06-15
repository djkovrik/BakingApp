package com.sedsoftware.bakingapp.features.recipestep;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sedsoftware.bakingapp.R;

public class RecipeStepSinglePageFragment extends Fragment implements ExoPlayer.EventListener {

  private static final String EXTRA_DESCRIPTION_ID = "EXTRA_DESCRIPTION_ID";
  private static final String EXTRA_VIDEO_URL_ID = "EXTRA_VIDEO_URL_ID";

  @BindView(R.id.recipe_step_desc_card)
  CardView descriptionCard;

  @BindView(R.id.recipe_step_desc)
  TextView descTextView;

  @BindView(R.id.recipe_step_video)
  SimpleExoPlayerView exoPlayerView;

  @BindBool(R.bool.two_pane_mode)
  boolean isTwoPane;

  SimpleExoPlayer exoPlayer;
  private MediaSessionCompat mediaSession;
  private PlaybackStateCompat.Builder stateBuilder;

  Unbinder unbinder;

  public static RecipeStepSinglePageFragment newInstance(String description, String videoUrl) {

    Bundle arguments = new Bundle();
    arguments.putString(EXTRA_DESCRIPTION_ID, description);
    arguments.putString(EXTRA_VIDEO_URL_ID, videoUrl);
    RecipeStepSinglePageFragment fragment = new RecipeStepSinglePageFragment();
    fragment.setArguments(arguments);
    return fragment;
  }

  public RecipeStepSinglePageFragment() {
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_recipe_step_page, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    String description = getArguments().getString(EXTRA_DESCRIPTION_ID);
    descTextView.setText(description);

    int orientation = getResources().getConfiguration().orientation;
    String video = getArguments().getString(EXTRA_VIDEO_URL_ID);

    if (video != null && !video.isEmpty()) {

      // Init and show video view
      setViewVisibility(exoPlayerView, true);
      initializeMediaSession();
      initializePlayer(Uri.parse(video));

      if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
        // Expand video, hide description, hide system UI
        expandVideoView(exoPlayerView);
        setViewVisibility(descriptionCard, false);
        hideSystemUI();
      }

    } else {
      // Hide video view
      setViewVisibility(exoPlayerView, false);
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    releasePlayer();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private void setViewVisibility(View view, boolean show) {
    if (show) {
      view.setVisibility(View.VISIBLE);
    } else {
      view.setVisibility(View.GONE);
    }
  }

  private void expandVideoView(SimpleExoPlayerView exoPlayer) {
    exoPlayer.getLayoutParams().height = LayoutParams.MATCH_PARENT;
    exoPlayer.getLayoutParams().width = LayoutParams.MATCH_PARENT;
  }

  // Src: https://developer.android.com/training/system-ui/immersive.html
  private void hideSystemUI() {
    getActivity().getWindow().getDecorView().setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE);
  }

  // EXO PLAYER METHODS

  private void initializeMediaSession() {

    mediaSession = new MediaSessionCompat(getContext(), "RecipeStepSinglePageFragment");

    mediaSession.setFlags(
        MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
            MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

    mediaSession.setMediaButtonReceiver(null);
    stateBuilder = new PlaybackStateCompat.Builder()
        .setActions(
            PlaybackStateCompat.ACTION_PLAY |
                PlaybackStateCompat.ACTION_PAUSE |
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                PlaybackStateCompat.ACTION_PLAY_PAUSE);

    mediaSession.setPlaybackState(stateBuilder.build());
    mediaSession.setCallback(new MediaSessionCompat.Callback() {
      @Override
      public void onPlay() {
        exoPlayer.setPlayWhenReady(true);
      }

      @Override
      public void onPause() {
        exoPlayer.setPlayWhenReady(false);
      }

      @Override
      public void onSkipToPrevious() {
        exoPlayer.seekTo(0);
      }
    });
    mediaSession.setActive(true);
  }

  private void initializePlayer(Uri mediaUri) {
    if (exoPlayer == null) {

      TrackSelector trackSelector = new DefaultTrackSelector();
      exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
      exoPlayerView.setPlayer(exoPlayer);
      exoPlayer.addListener(this);

      String userAgent = Util.getUserAgent(getContext(), "StepVideo");
      MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
          getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
      exoPlayer.prepare(mediaSource);
      exoPlayer.setPlayWhenReady(true);
    }
  }

  private void releasePlayer() {
    if (exoPlayer != null) {
      exoPlayer.stop();
      exoPlayer.release();
      exoPlayer = null;
    }

    if (mediaSession != null) {
      mediaSession.setActive(false);
    }
  }

  @Override
  public void onTimelineChanged(Timeline timeline, Object manifest) {

  }

  @Override
  public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

  }

  @Override
  public void onLoadingChanged(boolean isLoading) {

  }

  @Override
  public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
    if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
      stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, exoPlayer.getCurrentPosition(), 1f);
    } else if ((playbackState == ExoPlayer.STATE_READY)) {
      stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, exoPlayer.getCurrentPosition(), 1f);
    }
    mediaSession.setPlaybackState(stateBuilder.build());
  }

  @Override
  public void onPlayerError(ExoPlaybackException error) {

  }

  @Override
  public void onPositionDiscontinuity() {

  }

  @Override
  public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

  }
}
