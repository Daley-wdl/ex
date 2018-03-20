package com.exhibition.dao;

import com.exhibition.po.Carouse;

import java.util.List;

public interface CarouseDao {

    void insert(Carouse carouse);

    void delete(int id);

    void update(Carouse carouse);

    List<Carouse> getCarouseListForUser(int size);
}
