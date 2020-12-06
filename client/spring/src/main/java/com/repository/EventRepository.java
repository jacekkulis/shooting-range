package com.repository;

import com.model.Event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByTitle(String title);
    List<Event> findByTypeOfGun(String typeOfGun);
    Event findById(Long id);
}
