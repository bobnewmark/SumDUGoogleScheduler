package com.sumdu.dao;

import com.sumdu.entity.DoubleClass;

import java.util.List;

public interface IDoubleClassDao {

    void saveClass(DoubleClass d);
    List<DoubleClass> getAllClasses();
    void removeClass(DoubleClass d);

}
