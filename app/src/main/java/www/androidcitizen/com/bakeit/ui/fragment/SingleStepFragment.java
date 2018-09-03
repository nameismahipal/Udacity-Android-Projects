package www.androidcitizen.com.bakeit.ui.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.custominterface.StepClickListenerInterface;
import www.androidcitizen.com.bakeit.data.model.Step;
import www.androidcitizen.com.bakeit.databinding.FragmentSingleStepBinding;
import www.androidcitizen.com.bakeit.util.Constants;

public class SingleStepFragment extends Fragment {

    FragmentSingleStepBinding singleStepBinding;

    private Step step;

    ExoPlayer player = null;

    Context context;

    long playbackPosition;
    int currentWindow;
    boolean playWhenReady;

    public SingleStepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            this.context = context;
        }
    }

    public static SingleStepFragment newInstance(Step step) {
        SingleStepFragment fragment = new SingleStepFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.SINGLE_STEP_KEY, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = getArguments().getParcelable(Constants.SINGLE_STEP_KEY);
        }

        if(null != savedInstanceState) {
            playbackPosition = savedInstanceState.getLong(Constants.PLAY_BACK_POSITION_KEY);
            currentWindow = savedInstanceState.getInt(Constants.PLAY_CURRENT_WINDOW_KEY);
            playWhenReady = savedInstanceState.getBoolean(Constants.PLAY_WHEN_READY_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (null == singleStepBinding) {
            singleStepBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_single_step,
                    container, false);
        }

        return singleStepBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if(!step.getVideoURL().isEmpty()) {

            singleStepBinding.playerView.setVisibility(View.VISIBLE);
            singleStepBinding.placeholderImage.setVisibility(View.GONE);

            initializePlayer();
        } else {
            singleStepBinding.playerView.setVisibility(View.GONE);
            singleStepBinding.placeholderImage.setVisibility(View.VISIBLE);
        }

        singleStepBinding.stepDescription.setText(step.getDescription());
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(context),
                new DefaultTrackSelector(),
                new DefaultLoadControl());

        singleStepBinding.playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);

        MediaSource mediaSource = buildMediaSource();
        player.prepare(mediaSource);

        player.seekTo(currentWindow, playbackPosition);
    }

    private MediaSource buildMediaSource() {

        Uri uri = Uri.parse(step.getVideoURL());

        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("Recipe Instruction"))
                .createMediaSource(uri);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
           initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {

        singleStepBinding.playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {

            getPlayerStates();

            player.release();
            player = null;
        }
    }

    private void getPlayerStates() {
        playbackPosition = player.getCurrentPosition();
        currentWindow = player.getCurrentWindowIndex();
        playWhenReady = player.getPlayWhenReady();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(!step.getVideoURL().isEmpty()) {

            if (player != null) {
                getPlayerStates();
                outState.putLong(Constants.PLAY_BACK_POSITION_KEY, playbackPosition);
                outState.putInt(Constants.PLAY_CURRENT_WINDOW_KEY, currentWindow);
                outState.putBoolean(Constants.PLAY_WHEN_READY_KEY, playWhenReady);
            }
        }
    }
}

