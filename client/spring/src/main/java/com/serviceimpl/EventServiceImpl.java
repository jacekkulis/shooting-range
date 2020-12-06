package com.serviceimpl;

import com.model.Event;
import com.repository.EventRepository;
import com.service.EventService;
import com.variables.GunType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;
    
    private static final int PAGE_SIZE = 3;
    
    @Override
    public void save(Event event) {
	eventRepository.save(event);
    }

    @Override
    public List<Event> getListOfEvents() {
	return eventRepository.findAll();
    }

    @Override
    public Event findByTitle(String title) {
	return eventRepository.findByTitle(title);
    }

    @Override
    public List<String> getListOfGuns() {
	List<String> typeOfGuns = new ArrayList<String>();
	typeOfGuns.add(GunType.RIFLE);
	typeOfGuns.add(GunType.PISTOL);
	typeOfGuns.add(GunType.SHOTGUN);
	typeOfGuns.add(GunType.CROSSBOW);
	return typeOfGuns;
    }

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
    
    @Override
    public void deleteEventById(String eventId) {
	Event event = eventRepository.findOne(Long.parseLong(eventId));
	logger.debug(String.format("Event: %s", event.toString()));
	eventRepository.delete(event.getId());
	
    }

    @Override
    public Event findById(String id) {
	return eventRepository.findOne(Long.parseLong(id));	
    }

    @Override
    public void updateEvent(Event event) {
	Event entity = eventRepository.findOne(event.getId());
	
        if(entity != null){
            entity.setTitle(event.getTitle());
            entity.setDescription(event.getDescription());
            entity.setNumberOfCompetitors(event.getNumberOfCompetitors());
            entity.setReferee(event.getReferee());
            entity.setTypeOfGun(event.getTypeOfGun());
            //entity.setImg(event.getImg());
        }
     
        eventRepository.save(entity);
    }

    @Override
    public Page<Event> getEventLog(Integer pageNumber) {
	PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
	return eventRepository.findAll(request);
    }
}
