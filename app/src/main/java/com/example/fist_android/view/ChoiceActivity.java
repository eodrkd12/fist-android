package com.example.fist_android.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.fist_android.R;
import com.example.fist_android.databinding.ActivityChoiceBinding;
import com.example.fist_android.repository.MonitorRepository;
import com.example.fist_android.repository.OfficeRepository;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class ChoiceActivity extends AppCompatActivity {

    ActivityChoiceBinding binding;
//    ChoiceViewModel choiceViewModel;
    OfficeRepository officeRepository = OfficeRepository.getInstance();
    MonitorRepository monitorRepository = MonitorRepository.getInstance();

    int selectedOfficeIndex;
    int selectedMonitorIndex;
    int selectedSecondMonitorIndex = -1;
    int selectedHorizontalMonitorIndex = -1;
    private boolean officeIsInitializing = true;
    private boolean monitorIsInitializing = true;

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

        officeRepository.fetchOffice(new OfficeRepository.OfficeFetchCallback() {
            @Override
            public void onOfficeFetchComplete() {
                //지점 불러오기 완료
            }
        });

        // Office Spinner Event
        binding.officeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedOfficeIndex = i-1;
                Logger.d("SeletedOfficeIndex : " + selectedOfficeIndex);

                // Monitor 데이터를 비동기로 가져오고 응답이 도착하면 UI 업데이트
                if(selectedOfficeIndex >= 0){
                    monitorRepository.fetchMonitor(officeRepository.officeList[selectedOfficeIndex].getOfficeId(), 0, 10, new MonitorRepository.MonitorFetchCallback() {
                        @Override
                        public void onMonitorFetchComplete() {
                            Logger.d(monitorRepository.monitorNameList.get(0).toString());
                            // Monitor 데이터가 업데이트된 후에 Spinner를 업데이트
                            ArrayAdapter monitorAdapter = new ArrayAdapter(
                                    getApplicationContext(),
                                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                    monitorRepository.monitorNameList);
                            binding.monitorSpinner.setAdapter(monitorAdapter);

                            ArrayAdapter secondMonitorAdapter = new ArrayAdapter(
                                    getApplicationContext(),
                                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                    monitorRepository.monitorNameList);
                            binding.secondMonitorSpinner.setAdapter(secondMonitorAdapter);

                            ArrayAdapter horizontalMonitorAdapter = new ArrayAdapter(
                                    getApplicationContext(),
                                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                    monitorRepository.horizontalMonitorNameList);
                            binding.horizontalMonitorSpinner.setAdapter(horizontalMonitorAdapter);

                            // 선택된 항목을 0번으로 설정
                            binding.monitorSpinner.setSelection(0);
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        //Monitor Spinner Event
        binding.monitorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMonitorIndex = i-1;
                Logger.d("SeletedMonitorIndex : " + selectedMonitorIndex);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        binding.secondMonitorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSecondMonitorIndex = i-1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        binding.horizontalMonitorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedHorizontalMonitorIndex = i-1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedOfficeIndex < 0 || selectedMonitorIndex < 0){
                    Toast.makeText(ChoiceActivity.this, "지점 및 모니터 선택을 확인하세요.", Toast.LENGTH_LONG).show();
                    return;
                }

                officeRepository.saveOfficeData(getApplicationContext(), selectedOfficeIndex);
                monitorRepository.saveMonitorData(
                        getApplicationContext(),
                        selectedMonitorIndex,
                        selectedSecondMonitorIndex,
                        selectedHorizontalMonitorIndex);

                Intent intent = new Intent(ChoiceActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();

            }
        });
    }
}