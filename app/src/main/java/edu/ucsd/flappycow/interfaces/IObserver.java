package edu.ucsd.flappycow.interfaces;

public interface IObserver<T> {

    void onUpdate(T data);
}