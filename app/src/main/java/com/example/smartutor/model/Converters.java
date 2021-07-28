package com.example.smartutor.model;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Converters {
    @TypeConverter
    public static String fromGenderToString(Gender gender) {
        if (gender == null){return null;}
        return gender.toString();
    }
    @TypeConverter
    public static Gender fromStringToGender(String gender) {
        if(gender == null) {return null; }
        return Gender.valueOf(gender);
    }
    @TypeConverter
    public static String fromProfessionToString(Profession profession) {
        if (profession == null){return null;}
        return profession.toString();
    }
    @TypeConverter
    public static Profession fromStringToProfession(String profession) {
        if(profession == null) {return null; }
        return Profession.valueOf(profession);
    }
    @TypeConverter
    public static String fromProfessionsToString(List<Profession> professions) {
        if (professions == null) {return null;}
        if(professions.size()==0){return "";}

        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<professions.size()-1;i++){
            sb.append(professions.get(i).toString()+",");
        }
        sb.append(professions.get(professions.size()-1).toString());
        return sb.toString();
    }
    @TypeConverter
    public static List<Profession> fromStringToProfessions(String professions) {
        if(professions == null){return null;}
        List<Profession> p = new LinkedList<>();
        if(professions.equals("")){return p;}

        String[] strs = professions.split(",");
        for (int i=0;i<strs.length;i++){
            p.add(Profession.valueOf(strs[i]));
        }
        return p;
    }
    @TypeConverter
    public static String fromDateToString(Date date) {
        if (date == null){return null;}
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
    @TypeConverter
    public static Date fromStringToDate(String date) {
        if(date == null){return null;}
        try {return new SimpleDateFormat("dd/MM/yyyy").parse(date);} catch (ParseException e) {return null;}
    }
    @TypeConverter
    public static String fromLocalDateTimeToString(LocalDateTime date) {
        if (date == null){return null;}
        return date.format( DateTimeFormatter.ISO_DATE_TIME);
    }
    @TypeConverter
    public static LocalDateTime fromStringToLocalDateTime(String date) {
        if(date == null){return null;}
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
    }
}
