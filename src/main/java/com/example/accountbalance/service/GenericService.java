package com.example.accountbalance.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface GenericService<T> {

    Page<T> getPage(Pageable pageable);

    T getById(String id);

    T update(String id, T updated);

    T create(T newDomain);

    T delete(String id);

}
