package com.example.nzlive.fragment.homePage.easyRepair;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.nzlive.R;

public class RepairScheduleActivity extends AppCompatActivity {

    private ListView lv_repairSchedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_schedule);

        init();
    }

    private void init() {
        lv_repairSchedule=findViewById(R.id.lv_repairSchedule);
    }
}
