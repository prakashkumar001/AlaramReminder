package com.reminder;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public final class AlarmLandingPageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_alarm_landing_page);
        final Button launchMainActivityBtn = (Button) findViewById(R.id.load_main_activity_btn);
        final Button dismiss = (Button) findViewById(R.id.dismiss_btn);

        launchMainActivityBtn.setOnClickListener(this);
        dismiss.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.load_main_activity_btn:
                startActivity(new Intent(AlarmLandingPageActivity.this, MainActivity.class));
              finish();
                break;
            case R.id.dismiss_btn:
                finish();
                break;
        }

    }
}
