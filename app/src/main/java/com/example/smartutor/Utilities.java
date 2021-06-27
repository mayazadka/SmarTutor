package com.example.smartutor;

import android.util.Log;

import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Profession;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

public class Utilities {
    public static void validatePassword(String password, String confirm) throws Exception{
        if(!password.equals(confirm)){throw new Exception("password isn't match to confirm password");}
        if(password.length()<8){throw new Exception("password must be 8 characters or more");}
    }
    public static void validateLastName(String lastName) throws Exception{
        if(lastName.length()>20){throw new Exception("last name can't contain more than 10 characters");}
        if(lastName.length()==0){throw new Exception("enter last name");}
        if(!lastName.matches("[a-zA-Z]+")){throw new Exception("last name must contain only english letters");}
    }
    public static void validateFirstName(String firstName) throws Exception{
        if(firstName.length()>20){throw new Exception("first name can't contain more than 10 characters");}
        if(firstName.length()==0){throw new Exception("enter first name");}
        if(!firstName.matches("[a-zA-Z]+")){throw new Exception("first name must be contain only english letters");}
    }
    public static void validateEmail(String email) throws Exception{
        if(!email.matches("^(.+)@(.+)$")){throw new Exception("wrong email");}
    }
    public static void validateDate(String date) throws Exception{
        if(date.equals("")){{throw new Exception("choose date");}}
    }
    public static void validateProfessions(List<String> professions) throws Exception{
        if(professions.size()==0){{throw new Exception("choose professions");}}
    }
    public static void validateAboutMe(String aboutMe) throws Exception{
        if(aboutMe.length()>300){throw new Exception("about me can't contain more than 300 characters");}
        if(aboutMe.length()==0){throw new Exception("enter about me");}
    }

    public static Date convertToDate(String date){
        Date d = null;
        try {d = new SimpleDateFormat("dd/MM/yyyy").parse(date);} catch (ParseException e) {e.printStackTrace();}
        return d;
    }
    public static List<Profession> convertToProfessions(List<String> professions){
        List<Profession> ps = new LinkedList<>();
        for (String profession : professions) {
            profession = profession.replace(" ", "_");
            ps.add(Profession.valueOf(profession.toUpperCase()));
        }
        return ps;
    }
    public static int convertToGrade(String grade){
        Integer g;
        try{g = Integer.parseInt(grade.substring(0, 2));}
        catch (Exception e){g=Integer.parseInt(grade.substring(0, 1));}
        return g;
    }

    public static int age(Date birthday){
        LocalDateTime today = LocalDateTime.now();
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthday);
        int birthdayYear = cal.get(Calendar.YEAR);
        int birthdayMonth = cal.get(Calendar.MONTH);
        int birthdayDay = cal.get(Calendar.DAY_OF_MONTH);

        if(birthdayMonth < today.getMonth().getValue())
            return today.getYear() - birthdayYear;
        else if(birthdayMonth == today.getMonth().getValue()) {
            if(birthdayDay <= today.getDayOfMonth()){
                return today.getYear() - birthdayYear;
            }
            else {
                return today.getYear() - birthdayYear - 1;
            }
        }
        else {
            return today.getYear() - birthdayYear - 1;
        }
    }

    public static List<Lesson> getRemainLessons(List<Lesson> lessons) {
        List<Lesson> remainLessons = new LinkedList<Lesson>();
        Collections.sort(lessons, (l1, l2) -> l1.getDate().isBefore(l2.getDate())?1:0);
        for(int i = 0; i < lessons.size(); i++){
            if(lessons.get(i).getDate().plusHours(1).isAfter(LocalDateTime.now())) {
                remainLessons.add(lessons.get(i));
            }
        }
        return remainLessons;
    }
    public static Lesson getNextLesson(List<Lesson> lessons) {
        List<Lesson> remain = getRemainLessons(lessons);
        if(remain.size() > 0)
            return remain.get(0);
        else
            return null;
    }
    public static List<Lesson> getThisWeekLessons(List<Lesson> lessons){
        List<Lesson> thisWeekLessons = new LinkedList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime thisSunday = today.plusDays(8 - dayOfWeek);
        LocalDateTime lastSaturday = today.minusDays(dayOfWeek);
        for(int i = 0; i < lessons.size(); i++) {
            if(lessons.get(i).getDate().isAfter(lastSaturday) && lessons.get(i).getDate().isBefore(thisSunday)) {
                thisWeekLessons.add(lessons.get(i));
            }
        }
        return thisWeekLessons;
    }
}
