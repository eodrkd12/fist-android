package com.example.fist_android.common;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class LogManager {
    private static LogManager instance;
    private FormatStrategy formatStrategy;

    private LogManager() {
        // 생성자에서 formatStrategy 초기화
        formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                // .methodCount(0)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static LogManager getInstance() {
        if (instance == null) {
            synchronized (LogManager.class) {
                if (instance == null) {
                    instance = new LogManager();
                }
            }
        }
        return instance;
    }
}

