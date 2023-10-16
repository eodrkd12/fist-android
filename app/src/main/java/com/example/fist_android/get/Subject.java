package com.example.fist_android.get;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(boolean isPaused);
}
