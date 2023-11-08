package br.com.fiap.infra.security.repository;

import org.jvnet.hk2.annotations.Contract;

import java.util.List;

@Contract
public interface Repository<T, U> {

    List<T> findAll();

    T findById(U id);

    T persist(T t);

}
