package edu.ucsd.flappycow.interfaces;

public interface ISubject<T> {

    void register(IObserver<T> observer);
    void remove(IObserver<T> observer);

}