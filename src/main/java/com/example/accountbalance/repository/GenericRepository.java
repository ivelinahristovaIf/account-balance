package com.example.accountbalance.repository;

import com.example.accountbalance.model.GenericEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T extends GenericEntity<T>> extends JpaRepository<T, String> {

    //    T add(T entity);
    //
    //    Optional<T> findById(ID id);
    //
    //    Set<T> findAll();
    //
    //    Optional<T> deleteById(ID id);

}
