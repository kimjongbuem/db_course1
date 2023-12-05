package com.example.hello.repository;

import java.util.List;

public interface Repository<T> {
    public List<T> find();

    public T findById(String id);

    public void save(T object);

    public void update(T object);

    public void delete(String id);
}
