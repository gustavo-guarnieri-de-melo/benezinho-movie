package br.com.fiap.domain.repository;

import java.util.List;

public interface Repository<T, U> {

    public List<T> findAll();

    public T findById(U id);

    public T persist(T t);

}
