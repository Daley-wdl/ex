package com.exhibition.service;

import com.exhibition.po.Carouse;

import java.util.List;

public interface CarouseService {

    List<Carouse> getCarouseListForUser();

    void add(Carouse carouse);

    void delete(int id);

    void update(Carouse carouse);
}
