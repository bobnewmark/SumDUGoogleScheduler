package com.sumdu.http;

import com.sumdu.config.AppConfig;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SumduScheduleRequest {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String SCHEDULE = "http://schedule.sumdu.edu.ua/index/json?method=getSchedules";
    private static final String GROUP = "&id_grp=";
    private static final String FIO = "&id_fio=";
    private static final String AUD = "&id_aud=";
    private static final String DATE_BEG = "&date_beg=";
    private static final String DATE_END = "&date_end=";
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private static Logger LOG = Logger.getLogger(SumduScheduleRequest.class);

    public static String getSchedule() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 7);

        String group = AppConfig.getProperties().getProperty("this.groups");
        group = group.length() > 0 ? group : "0";

        String teacher_fio = AppConfig.getProperties().getProperty("this.teacher");
        teacher_fio = teacher_fio.length() > 0 ? teacher_fio : "1640"; // "Petrov" by default

        String aud = AppConfig.getProperties().getProperty("this.classroom");
        aud = aud.length() > 0 ? aud : "0";

        String date_beg = AppConfig.getProperties().getProperty("this.begindate");
        date_beg = date_beg.length() > 0 ? date_beg : formatter.format(date);

        String date_end = AppConfig.getProperties().getProperty("this.enddate");
        date_end = date_end.length() > 0 ? date_end : "0";

        String url = SCHEDULE
                + GROUP + group
                + FIO + teacher_fio
                + AUD + aud
                + DATE_BEG + date_beg
                + DATE_END + date_end;

        URL obj = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            LOG.error(e);
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) Objects.requireNonNull(obj).openConnection();
        } catch (IOException e) {
            LOG.error(e);
        }
        try {
            Objects.requireNonNull(con).setRequestMethod("GET");
        } catch (ProtocolException e) {
            LOG.error(e);
        }
        Objects.requireNonNull(con).setRequestProperty("User-Agent", USER_AGENT);

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            LOG.error(e);
        }
        return response.toString();
    }
}