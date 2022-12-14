package org.example.service;

public interface BaseService<T,R> {
    T add(T t);
    T get(Long key);
    T upDate(T t, Long key);
    R delete(Long key);
}
