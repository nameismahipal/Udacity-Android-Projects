package www.androidcitizen.com.bakeit.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.custominterface.PrevNextInterface;
import www.androidcitizen.com.bakeit.data.model.Step;
import www.androidcitizen.com.bakeit.databinding.FragmentSingleStepBinding;
import www.androidcitizen.com.bakeit.util.Constants;

public class SingleStepFragment extends Fragment {

    private FragmentSingleStepBinding singleStepBinding;

    private Step step;

    private ExoPlayer player = null;

    private Context context;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;

    private String stepNumberState;
    private boolean nxtBtnState;
    private boolean prevBtnState;

    private PrevNextInterface prevNextInterface;

    public SingleStepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            this.context = context;
        }

        try {
            prevNextInterface = (PrevNextInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement PrevNextInterface Interface methods");
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
            stepNumberState = getArguments().getString(Constants.STEP_NUMBER_STATE_KEY);
            prevBtnState = getArguments().getBoolean(Constants.STEP_PREV_BTN_STATE_KEY);
            nxtBtnState = getArguments().getBoolean(Constants.STEP_NEXT_BTN_STATE_KEY);
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

        singleStepBinding.prevStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevNextInterface.prevButtonClicked();
            }
        });

        singleStepBinding.nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevNextInterface.nextButtonClicked();
            }
        });

        setupViews();

        return singleStepBinding.getRoot();
    }

    private void setupViews() {

        if(context.getResources().getBoolean(R.bool.is_tablet)){
            singleStepBinding.prevStep.setVisibility(View.GONE);
            singleStepBinding.nextStep.setVisibility(View.GONE);
            singleStepBinding.stepNumber.setVisibility(View.GONE);
        } else {
            if(prevBtnState) singleStepBinding.prevStep.setVisibility(View.GONE);
            if(nxtBtnState) singleStepBinding.nextStep.setVisibility(View.GONE);
            singleStepBinding.stepNumber.setVisibility(View.VISIBLE);
        }

        singleStepBinding.stepNumber.setText(stepNumberState);
        singleStepBinding.stepDescription.setText(step.getDescription());

        if(context.getResources().getBoolean(R.bool.is_lanscape)){
            singleStepBinding.stepNumber.setVisibility(View.GONE);
            if(!step.getVideoURL().isEmpty()) {
                ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar().hide();
            }
        }
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

        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
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

