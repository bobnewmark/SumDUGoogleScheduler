package com.sumdu.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="doubleClass")
public class DoubleClass {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name="date")
    private String date;
    @Column(name="day")
    private String day;
    @Column(name="time")
    private String time;
    @Column(name="teacher")
    private String teacher;
    @Column(name="classroom")
    private String classroom;
    @Column(name="discipline")
    private String discipline;
    @Column(name="groups")
    private String groups;
    @Column(name="type")
    private String type;

    public DoubleClass() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DoubleClass{" +
                "date='" + date + '\'' +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", teacher='" + teacher + '\'' +
                ", classroom='" + classroom + '\'' +
                ", discipline='" + discipline + '\'' +
                ", groups='" + groups + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleClass that = (DoubleClass) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(day, that.day) &&
                Objects.equals(time, that.time) &&
                Objects.equals(teacher, that.teacher) &&
                Objects.equals(classroom, that.classroom) &&
                Objects.equals(discipline, that.discipline) &&
                Objects.equals(groups, that.groups) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, day, time, teacher, classroom, discipline, groups, type);
    }
}
