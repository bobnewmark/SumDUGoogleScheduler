package com.sumdu.dao;

import com.sumdu.entity.DoubleClass;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.List;

public class DoubleClassDao implements IDoubleClassDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public void saveClass(DoubleClass d) {
        hibernateTemplate.save(d);
    }

    @Override
    public List<DoubleClass> getAllClasses() {
        List customers = hibernateTemplate.execute(session -> {
            Query query = session.createQuery("select d  from DoubleClass d");
            return query.list();
        });
        return (List<DoubleClass>) customers;
    }

    @Override
    public void removeClass(DoubleClass d) {
        Session session = hibernateTemplate.getSessionFactory().openSession();
        String time = d.getTime();
        String date = d.getDate();
        System.out.println("deleting date=" + date + " time=" + time);

        Query query = session.createSQLQuery("delete from DoubleClass where time = :ti and date = :da");
        query.setString("ti", time);
        query.setString("da", date);
        query.executeUpdate();
    }
}
