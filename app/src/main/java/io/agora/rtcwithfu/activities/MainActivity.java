package io.agora.rtcwithfu.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import io.agora.rtcwithfu.R;
import io.agora.rtcwithfu.utils.Constants;

public class MainActivity extends Activity {
    private static final int REQUEST_CODE_ALL_PERMISSIONS = 999;
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;
    private EditText mChannelName;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        mChannelName = findViewById(R.id.edt_channel);
        checkPermissions();
    }

    private void checkPermissions() {
        if (!permissionsGranted()) {
            requestPermissions();
        }
    }

    private boolean permissionsGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_ALL_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean granted = true;
        if (requestCode == REQUEST_CODE_ALL_PERMISSIONS) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }

            if (!granted) {
                requestPermissions();
                Toast.makeText(this, getString(R.string.msg_permission_granted), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onStartBroadcastClick(View view) {
        if (!isFastClick()) {
            return;
        }
        String name = mChannelName.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, R.string.empty_room_name_toast, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, FUChatActivity.class);
            intent.putExtra(Constants.ACTION_KEY_ROOM_NAME, name);
            startActivity(intent);
        }
    }
}
