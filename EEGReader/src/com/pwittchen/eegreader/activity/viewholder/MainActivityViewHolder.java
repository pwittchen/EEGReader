package com.pwittchen.eegreader.activity.viewholder;

import android.view.View;
import android.widget.TextView;

import com.pwittchen.eegreader.R;

public class MainActivityViewHolder {
    public TextView tvAttention;
    public TextView tvMeditation;
    public TextView tvBlink;
    public TextView tvRawData;
    public TextView tvWaveAlphaLow;
    public TextView tvWaveAlphaHigh;
    public TextView tvWaveBetaLow;
    public TextView tvWaveBetaHigh;
    public TextView tvWaveDelta;
    public TextView tvWaveGammaLow;
    public TextView tvWaveGammaMid;
    public TextView tvWaveTheta;

    public MainActivityViewHolder(View view) {
        tvWaveAlphaLow = (TextView) view.findViewById(R.id.tv_wave_alpha_low);
        tvWaveAlphaHigh = (TextView) view.findViewById(R.id.tv_wave_alpha_high);
        tvWaveBetaLow = (TextView) view.findViewById(R.id.tv_wave_beta_low);
        tvWaveBetaHigh = (TextView) view.findViewById(R.id.tv_wave_beta_high);
        tvWaveDelta = (TextView) view.findViewById(R.id.tv_wave_delta);
        tvWaveGammaLow = (TextView) view.findViewById(R.id.tv_wave_gamma_low);
        tvWaveGammaMid = (TextView) view.findViewById(R.id.tv_wave_gamma_mid);
        tvWaveTheta = (TextView) view.findViewById(R.id.tv_wave_theta);
        tvAttention = (TextView) view.findViewById(R.id.tv_attention);
        tvMeditation = (TextView) view.findViewById(R.id.tv_meditation);
        tvBlink = (TextView) view.findViewById(R.id.tv_blink);
        tvRawData = (TextView) view.findViewById(R.id.tv_raw_data);
    }
}
