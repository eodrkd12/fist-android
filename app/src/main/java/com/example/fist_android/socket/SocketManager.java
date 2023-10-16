package com.example.fist_android.socket;

import android.text.format.DateFormat;

import com.example.fist_android.repository.CourseRepository;
import com.example.fist_android.repository.ExerciseRepository;
import com.example.fist_android.repository.OfficeRepository;
import com.orhanobut.logger.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;

import io.socket.client.IO;
import io.socket.client.*;
import io.socket.emitter.Emitter;

public class SocketManager {
    //=============================================================//
    //Signleton
    //=============================================================//
    private static SocketManager socketInstance; // 싱글톤 인스턴스
    private Socket socket;
    private SocketManager() {
        try{
            socket = IO.socket("http://211.107.110.77:3001");
//            socket = IO.socket("http://10.0.2.2:3001");
            socket.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static SocketManager getInstance() {
        if (socketInstance == null) {
            socketInstance = new SocketManager();
        }
        return socketInstance;
    }
    //=============================================================//
    ExerciseRepository exerciseRepository = ExerciseRepository.getInstance();
    OfficeRepository officeRepository = OfficeRepository.getInstance();
    CourseRepository courseRepository = CourseRepository.getInstance();
    //=============================================================//
    public Socket getSocket(){
        return socketInstance.socket;
    }

    /**
     * Connect Server Socket
     */
    public Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Logger.i("Socket Connect");
        }
    };
    /**
     * Patch Today Course Data
     */
    public Emitter.Listener getTodayCourse = new Emitter.Listener(){
        @Override
        public void call(Object... args) {
            Logger.i("Get Today Course");
            courseRepository.downloadTodayCouse = true;
            LocalDate today = LocalDate.now();

            courseRepository.fetchCourseData(officeRepository.officeId, today.toString(), new CourseRepository.CourseFetchCallback() {
                @Override
                public void onCourseFetchComplete() {
                    courseRepository.printCourseData();
                }
            });
        }
    };
    /**
     * Exercise Start
     */
    public Emitter.Listener startExercise = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Logger.i("Start Exercise");
            exerciseRepository.exerciseStart = true;
        }
    };
    /**
     * Exercise Test
     */
    public Emitter.Listener startTestExercise = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Logger.i("Start Test Exercise");
            exerciseRepository.exerciseTestStart = true;
        }
    };
    /**
     * Exercise Stop
     */
    public Emitter.Listener stopExercise = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Logger.i("stop Exercise");
            exerciseRepository.exerciseStop = true;
        }
    };
    /**
     * Exercise Pause
     */
    public Emitter.Listener pauseExercise = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Logger.i("Pause Exercise");
//            exerciseRepository.exercisePause = !exerciseRepository.exercisePause;
            exerciseRepository.setExercisePause(!exerciseRepository.exercisePause);
        }
    };


}
