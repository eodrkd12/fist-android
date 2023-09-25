package com.example.fist_android.view;

import static com.example.fist_android.repository.OfficeRepository.getInstance;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.fist_android.R;
import com.example.fist_android.api.MonitorAPI;
import com.example.fist_android.databinding.ActivityChoiceBinding;
import com.example.fist_android.model.Office;
import com.example.fist_android.repository.MonitorRepository;
import com.example.fist_android.repository.OfficeRepository;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChoiceActivity extends AppCompatActivity {

    ActivityChoiceBinding binding;
//    ChoiceViewModel choiceViewModel;
    OfficeRepository officeRepository = OfficeRepository.getInstance();
    MonitorRepository monitorRepository = MonitorRepository.getInstance();

    int seletedOfficeIndex;
    int seletedMonitorIndex;
    private boolean isInitializing = true;
    private boolean isInitializing2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_choice);
//        choiceViewModel = new ChoiceViewModel(MonitorRepository.getInstance());
//        binding.setChoiceViewModel(choiceViewModel);

        //OfficeSpinner 초기값 설정
        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                officeRepository.officeNameList);
        binding.officeSpinner.setAdapter(adapter);

        // Office Spinner Event
        binding.officeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(isInitializing){
                    isInitializing = false;
                    return;
                }
                seletedOfficeIndex = i;

                // Monitor 데이터를 비동기로 가져오고 응답이 도착하면 UI 업데이트
                monitorRepository.fetchMonitor(officeRepository.officeList[i].getOfficeId(), 0, 10, new MonitorRepository.MonitorFetchCallback() {
                    @Override
                    public void onMonitorFetchComplete() {
                        // Monitor 데이터가 업데이트된 후에 Spinner를 업데이트
                        ArrayAdapter monitorAdapter = new ArrayAdapter(
                                getApplicationContext(),
                                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                monitorRepository.monitorNameList);
                        binding.monitorSpinner.setAdapter(monitorAdapter);

                        // 선택된 항목을 0번으로 설정
                        binding.monitorSpinner.setSelection(0);
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        //Monitor Spinner Event
        binding.monitorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(isInitializing2){
                    isInitializing2 = false;
                    return;
                }
                seletedMonitorIndex = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }
}