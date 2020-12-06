package com.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import com.model.Referee;
import com.repository.RefereeRepository;

final class StringToReferee implements Converter<String, Referee> {
    @Autowired
    private RefereeRepository refereeRepository;
  
    public Referee convert(String id) {
        return refereeRepository.findOne(Long.parseLong(id));
    }
    
}