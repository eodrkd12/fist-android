package com.example.fist_android.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.fist_android.R;
import com.example.fist_android.databinding.ActivityChoiceBinding;
import com.example.fist_android.viewmodel.ChoiceViewModel;

import java.util.ArrayList;

public class ChoiceActivity extends AppCompatActivity {

    ActivityChoiceBinding binding;
    ChoiceViewModel choiceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_choice);
    }
}