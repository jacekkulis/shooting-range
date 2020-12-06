package com.serviceimpl;

import com.model.Referee;
import com.repository.RefereeRepository;
import com.service.RefereeService;
import com.variables.Title;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RefereeServiceImpl implements RefereeService {
    @Autowired
    private RefereeRepository refereeRepository;

    @Override
    public void save(Referee referee) {
	refereeRepository.save(referee);
    }

    @Override
    public Referee findByName(String name) {
	return refereeRepository.findByName(name);
    }

    @Override
    public Referee findBySurname(String surname) {
	return refereeRepository.findByName(surname);
    }

    @Override
    public List<Referee> getListOfReferees() {
	return refereeRepository.findAll();
    }

    @Override
    public List<String> getListOfTitles() {
	List<String> titlesList = new ArrayList<String>();
	titlesList.add(Title.THIRD_CLASS);
	titlesList.add(Title.SECOND_CLASS);
	titlesList.add(Title.FIRST_CLASS);
	titlesList.add(Title.WORLD_CLASS);
	return titlesList;
    }

    @Override
    public Referee findByFullName(String name, String surname) {
	return refereeRepository.findByFullName(name, surname);
    }
}
