package com.service;

import java.util.List;
import com.model.Referee;

public interface RefereeService {
    void save(Referee referee);

    Referee findByName(String name);

    Referee findBySurname(String surname);

    List<Referee> getListOfReferees();

    List<String> getListOfTitles();

    Referee findByFullName(String name, String surname);
}
