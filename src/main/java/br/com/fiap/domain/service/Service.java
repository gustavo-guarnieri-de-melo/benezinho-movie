package br.com.fiap.domain.service;

import java.util.List;

public interface Service<T, U> {

    public List<T> findAll();

    public T findById(U id);

    public T persist(T t);

}