package io.agora.rtcwithfu.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import io.agora.rtcwithfu.MyApplication;
import io.agora.rtcwithfu.RtcEngineEventHandler;

public abstract class RtcBasedActivity extends AppCompatActivity implements RtcEngineEventHandler {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addRtcHandler(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeRtcHandler(this);
    }

    protected MyApplication application() {
        return (MyApplication) getApplication();
    }

    private void addRtcHandler(RtcEngineEventHandler handler) {
        application().addRtcHandler(handler);
    }

    private void removeRtcHandler(RtcEngineEventHandler handler) {
        application().removeRtcHandler(handler);
    }
}
