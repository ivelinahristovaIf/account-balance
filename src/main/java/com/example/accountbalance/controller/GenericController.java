package com.example.accountbalance.controller;

import lombok.Getter;

import com.example.accountbalance.service.GenericService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public abstract class GenericController<V> {

    private final GenericService<V> service;

    public GenericController(final GenericService<V> service) {
        this.service = service;
    }

    public ResponseEntity<Page<V>> getPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getPage(pageable));
    }

    public ResponseEntity<V> getOne(String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    public ResponseEntity<V> update(String id, final V updated) {
        return ResponseEntity.ok(service.update(id, updated));
    }

    public ResponseEntity<V> create(V created) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(created));
    }

    public ResponseEntity<V> delete(String id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
