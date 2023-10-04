package edu.ucsd.flappycow.interfaces;


import android.os.strictmode.UnbufferedIoViolation;

import java.util.ArrayList;

public class SubjectImpl<T> implements ISubjectImpl<T> {

    private ArrayList<IObserver<T>> observers;


    public SubjectImpl(){
        observers = new ArrayList<>();
    }

    @Override
    public void register(IObserver<T> observer) {
        /* ... */
        observers.add(observer);
    }
    @Override
    public void remove(IObserver<T> observer) {
        /* ... */
        observers.remove(observer);
    }

    public void notify(T data) {

        /* ... */
        for (int i = 0; i <observers.size(); i ++){
            observers.get(i).onUpdate(data);
        }

    }
}