package com.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.model.Event;

public interface EventService {
    void save(Event event);
    Event findByTitle(String title);
    Event findById(String id);
    List<Event> getListOfEvents();
    List<String> getListOfGuns();
    void deleteEventById(String eventId);
    void updateEvent(Event eventForm);
    Page<Event> getEventLog(Integer pageNumber);
}
