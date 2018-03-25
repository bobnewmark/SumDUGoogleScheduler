package com.sumdu;

import com.sumdu.config.AppConfig;
import com.sumdu.dao.IDoubleClassDao;
import com.sumdu.entity.DoubleClass;
import com.sumdu.http.CalendarOperations;
import com.sumdu.http.SumduScheduleRequest;
import com.sumdu.utils.APIResponseConverter;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

public class Main {

    private static Logger LOG = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.info("Application started");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        IDoubleClassDao idcd = ctx.getBean(IDoubleClassDao.class);

        //getting classes from SumDU schedule
        Map<String, DoubleClass> sumduClasses = APIResponseConverter.fromSumDu(SumduScheduleRequest.getSchedule());
        for (DoubleClass d : idcd.getAllClasses()) {
            // If database has classes that schedule doesn't - remove them
            if (!sumduClasses.containsKey(d.toString())) {
                idcd.removeClass(d);
            } else {
            // If database already has the class from schedule - remove it from schedule map
                if (sumduClasses.containsKey(d.toString())) sumduClasses.remove(d.toString());
            }
        }

        // saving new classes to database
        sumduClasses.forEach((key, value) -> idcd.saveClass(value));

        // creating a map of classes from database
        Map<String, DoubleClass> dbMap = new HashMap<>();
        idcd.getAllClasses().forEach(d -> dbMap.put(d.toString(), d));

        // and map from Google calendar
        Map<String, DoubleClass> googleMap = APIResponseConverter.fromCalendar(CalendarOperations.getCalendarSchedule());

        // removing classes from calendar if they are not in database
        for (Map.Entry<String, DoubleClass> entry : googleMap.entrySet()) {
            if (!dbMap.containsKey(entry.getValue().toString())) {
                CalendarOperations.deleteEvent(entry.getKey());
            }
        }

        //getting updated map from Google calendar
        googleMap = APIResponseConverter.fromCalendar(CalendarOperations.getCalendarSchedule());
        List<String> list = new ArrayList<>();
        googleMap.values().forEach(doubleClass -> list.add(doubleClass.toString()));

        // adding new classes to calendar if they aren't there yet
        for (DoubleClass d : idcd.getAllClasses()) {
            if (!list.contains(d.toString())) {
                CalendarOperations.postEvent(d);
            }
        }
        LOG.info("Application is shutting down");
    }
}
