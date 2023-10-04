package edu.ucsd.flappycow.interfaces;

public interface ISubjectImpl<T> extends ISubject<T> {

    void notify(T data);
}