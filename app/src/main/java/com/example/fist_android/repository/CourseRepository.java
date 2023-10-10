package com.example.fist_android.repository;

import com.example.fist_android.model.Course;
import com.example.fist_android.model.CourseCategory;
import com.example.fist_android.model.Exercise;
import com.example.fist_android.model.ExerciseList;
import com.example.fist_android.model.Monitor;
import com.example.fist_android.model.ResponseDTO;
import com.example.fist_android.model.RoundCaseList;
import com.example.fist_android.model.Roundcase;
import com.example.fist_android.preference.PreferenceManager;
import com.example.fist_android.retrofit.RetrofitAPI;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseRepository {
    //=============================================================//
    //Signleton
    //=============================================================//
    private static CourseRepository courseInstance; // 싱글톤 인스턴스
    private CourseRepository() {}
    public static CourseRepository getInstance() {
        if (courseInstance == null) {
            courseInstance = new CourseRepository();
        }
        return courseInstance;
    }
    //=============================================================//
    MonitorRepository monitorRepository = MonitorRepository.getInstance();
    public boolean downloadTodayCouse = false;
    public Course course;
    public CourseCategory[] courseCategory;
    public ArrayList<ExerciseList> exerciseList = new ArrayList<>();
    public ArrayList<ExerciseList> warmupExerciseList = new ArrayList<>();
    public ArrayList<ExerciseList> cooldownExerciseList = new ArrayList<>();
    public ArrayList<ExerciseList> demoExerciseList = new ArrayList<>();
    public ArrayList<Roundcase> weightRoundcase = new ArrayList<>();
    public ArrayList<Roundcase> hiitRoundcase = new ArrayList<>();
    //=============================================================//
    //Retrofit
    //=============================================================//
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://211.107.110.77:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    public void fetchCourseData(String officeId, String courseDateStr, final CourseRepository.CourseFetchCallback callback) {
        Logger.d("Course Fetch Start");
        retrofitAPI.getCourseData(officeId, courseDateStr).enqueue(new Callback<ResponseDTO<Course>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Course>> call, Response<ResponseDTO<Course>> response) {
                if(response.isSuccessful()){
                    ResponseDTO<Course> courseData = response.body();
                    course = courseData.getData();
                    courseCategory = course.getCategoryList();
                    for(CourseCategory categoryList : courseCategory){
                        if(categoryList.getExerciseType().compareTo("WARM UP") == 0){
                            ExerciseList[] wuDataList = categoryList.getExerciseList();
                            for(ExerciseList data : wuDataList){
                                warmupExerciseList.add(data);
                            }
                        }
                        else if(categoryList.getExerciseType().compareTo("WEIGHT") == 0){
                            //Roundcase
                            RoundCaseList[] roundDataList = categoryList.getRoundcaseList();
                            for(RoundCaseList data : roundDataList){
                                weightRoundcase.add(data.getRoundcase());
                            }
                            //Exercise
                            ExerciseList[] exerciseDataList = categoryList.getExerciseList();
                            for(ExerciseList data : exerciseDataList){
                                if(data.getMonitor().getMonitorName().compareTo(monitorRepository.monitorName) == 0){
                                    exerciseList.add(data);
                                }
                            }
                        }
                        else if(categoryList.getExerciseType().compareTo("HIIT") == 0){
                            //Roundcase
                            RoundCaseList[] roundDataList = categoryList.getRoundcaseList();
                            for(RoundCaseList data : roundDataList){
                                hiitRoundcase.add(data.getRoundcase());
                            }
                            //Exercise
                            ExerciseList[] exerciseDataList = categoryList.getExerciseList();
                            for(ExerciseList data : exerciseDataList){
                                //Demo
                                demoExerciseList.add(data);
                                if(data.getMonitor().getMonitorName().compareTo(monitorRepository.monitorName) == 0){
                                    exerciseList.add(data);
                                }
                            }
                        }
                        else if(categoryList.getExerciseType().compareTo("COOL DOWN") == 0){
                            ExerciseList[] cdDataList = categoryList.getExerciseList();
                            for(ExerciseList data : cdDataList){
                                cooldownExerciseList.add(data);
                            }
                        }
                    }
                }
                else {
                    Logger.e("Course Fetch Fail");
                }
                if (callback != null) {
                    callback.onCourseFetchComplete();
                }
                Logger.d("Course Fetch Done");
            }

            @Override
            public void onFailure(Call<ResponseDTO<Course>> call, Throwable t) {

            }
        });
    }
    // Course 데이터를 가져온 후 UI를 업데이트하는 콜백 인터페이스
    public interface CourseFetchCallback {
        void onCourseFetchComplete();
    }

    //=============================================================//
    //Print
    //=============================================================//
    public void printCourseData(){
//        for(ExerciseList data : courseInstance.exerciseList){
//            Logger.i("ExerciseName : " + data.getExercise().getExerciseName());
//        }
        Logger.wtf("ExerciseLength : " + courseInstance.exerciseList.size());

//        for(ExerciseList data : courseInstance.warmupExerciseList){
//            Logger.i("WarmUpExerciseName : " + data.getExercise().getExerciseName());
//        }
        Logger.wtf("WarmUpExerciseName : " + courseInstance.warmupExerciseList.size());

//        for(ExerciseList data : courseInstance.cooldownExerciseList){
//            Logger.i("CoolDownExerciseName : " + data.getExercise().getExerciseName());
//        }
        Logger.wtf("CoolDownExerciseName : " + courseInstance.cooldownExerciseList.size());

//        for(ExerciseList data : courseInstance.demoExerciseList){
//            Logger.i("DemoExerciseName : " + data.getExercise().getExerciseName());
//        }
        Logger.wtf("DemoExerciseName : " + courseInstance.demoExerciseList.size());
    }

}
