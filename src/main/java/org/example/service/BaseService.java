package org.example.service;

public interface BaseService<T,R> {
    R add(T t);
    T get(int key);
    R upDate(T t, int key);
    R delete(int key);
}
