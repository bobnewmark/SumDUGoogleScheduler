package com.sumdu.utils;

import com.google.api.services.calendar.model.Event;
import com.sumdu.entity.DoubleClass;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class APIResponseConverter {

    private static Logger LOG = Logger.getLogger(APIResponseConverter.class);

    public static Map<String, DoubleClass> fromSumDu(String response) {

        Map<String, DoubleClass> map = new HashMap<>();
        String modified = response.substring(1,response.length()-1);
        String[] strings = modified.split("\\{");
        List<String> lessons = Arrays.asList(strings);
        List<String> list2 = lessons.stream()
                .filter(s -> s.length() != 0)
                .map(s -> s.endsWith(",") ? s.substring(0, s.length()-1) : s)
                .map(s -> "{"+s)
                .collect(Collectors.toList());

        List<JSONObject> jsons = new ArrayList<>();
        list2.forEach(s -> jsons.add(new JSONObject(s)));

        for (JSONObject j: jsons) {
            DoubleClass d = new DoubleClass();
            d.setDate(j.getString("DATE_REG"));
            d.setDiscipline(j.getString("ABBR_DISC"));
            d.setDay(j.getString("NAME_WDAY"));
            d.setTime(j.getString("TIME_PAIR"));
            d.setTeacher(j.getString("NAME_FIO"));
            d.setClassroom(j.getString("NAME_AUD"));
            d.setGroups(j.getString("NAME_GROUP"));
            d.setType(j.getString("NAME_STUD"));
            map.put(d.toString(), d);
        }
        return map;
    }

    public static Map<String, DoubleClass> fromCalendar(List<Event> events) {
        Map<String, DoubleClass> map = new HashMap<>();
        for (Event e: events) {
            DoubleClass d = null;
            d = ClassConverter.eventToClass(e);
            map.put(e.getId(), d);
        }
        return map;
    }

}
