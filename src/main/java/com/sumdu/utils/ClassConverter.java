package com.sumdu.utils;


import com.sumdu.entity.DoubleClass;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClassConverter {

    private static Logger LOG = Logger.getLogger(ClassConverter.class);
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

    public static Event classToEvent(DoubleClass d) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyyHH:mm");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = formatter.parse(d.getDate() + d.getTime().substring(0, d.getTime().indexOf("-")));
            endDate = formatter.parse(d.getDate() + d.getTime().substring(d.getTime().indexOf("-") + 1));
        } catch (ParseException e) {
            LOG.error(e);
        }
        String description =
                "subject: " + d.getDiscipline()
                        + "\nteacher: " + d.getTeacher()
                        + "\ngroup: " + d.getGroups()
                        + "\ntype: " + d.getType()
                        + "\nclassroom: " + d.getClassroom()
                        + "\nday: " + d.getDay();

        Event event = new Event()
                .setSummary("SUMDU" + d.getDiscipline())
                .setDescription(description);
        DateTime startDateTime = new DateTime(startDate);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Kiev");
        event.setStart(start);
        DateTime endDateTime = new DateTime(endDate);
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Europe/Kiev");
        event.setEnd(end);
        return event;
    }

    public static DoubleClass eventToClass(Event e) {
        DoubleClass doubleClass = new DoubleClass();
        String[] arr = e.getDescription().split("\n");

        doubleClass.setDiscipline(e.getSummary());
        doubleClass.setTeacher(arr[1].substring(arr[1].indexOf(":") + 2));
        doubleClass.setGroups(arr[2].substring(arr[2].indexOf(":") + 2));
        doubleClass.setType(arr[3].substring(arr[3].indexOf(":") + 2));
        doubleClass.setClassroom(arr[4].substring(arr[4].indexOf(":") + 2));
        doubleClass.setDay(arr[5].substring(arr[5].indexOf(":") + 2));

        EventDateTime start = e.getStart();
        EventDateTime end = e.getEnd();
        String date = dateFormatter.format(new Date(start.getDateTime().getValue()));
        String time = timeFormatter.format(new Date(start.getDateTime().getValue()))
                + "-" + timeFormatter.format(new Date(end.getDateTime().getValue()));
        doubleClass.setDate(date);
        doubleClass.setTime(time);
        return doubleClass;
    }
}
