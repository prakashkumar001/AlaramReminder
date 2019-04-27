package com.reminder;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.reminder.adapter.AlarmsAdapter;
import com.reminder.model.Alarm;
import com.reminder.service.LoadAlarmsReceiver;
import com.reminder.service.LoadAlarmsService;
import com.reminder.util.AlarmUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadAlarmsReceiver.OnAlarmsLoadedListener {

    private LoadAlarmsReceiver mReceiver;
    private AlarmsAdapter mAdapter;
    TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        mReceiver = new LoadAlarmsReceiver(this);

        final RecyclerView rv = (RecyclerView) findViewById(R.id.recycler);
        textView = (TextView) findViewById(R.id.empty_view);

        mAdapter = new AlarmsAdapter();

        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new DividerItemDecoration(this));
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmUtils.checkAlarmPermissions(MainActivity.this);
                final Intent i =
                        AddEditAlarmActivity.buildAddEditAlarmActivityIntent(
                                MainActivity.this, AddEditAlarmActivity.ADD_ALARM
                        );
                startActivity(i);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        final IntentFilter filter = new IntentFilter(LoadAlarmsService.ACTION_COMPLETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, filter);
        LoadAlarmsService.launchLoadAlarmsService(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    @Override
    public void onAlarmsLoaded(ArrayList<Alarm> alarms) {
        if (alarms.size() == 0) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);

        }
        mAdapter.setAlarms(alarms);
    }
}