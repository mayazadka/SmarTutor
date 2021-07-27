package com.example.smartutor.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.smartutor.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
public class Tutor  {
    @PrimaryKey
    @NonNull
    private String email;
    private String lastName;
    private String firstName;
    private Gender gender;
    private Date birthdayDate;
    private List<Profession> professions;
    private String aboutMe;
    private Long lastUpdated;
    private Boolean isDeleted;

    public Tutor(){}
    public Tutor(String email, String lastName, String firstName, Gender gender, Date birthdayDate, List<Profession> professions, String aboutMe) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
        this.professions = professions;
        this.aboutMe = aboutMe;
        this.isDeleted = false;
        this.lastUpdated = Long.valueOf(0);
    }
    public Tutor(Map<String, Object> json){
        email =             (String)json.get("email");
        lastName =          (String)json.get("lastName");
        firstName =         (String)json.get("firstName");
        gender =            Converters.fromStringToGender((String)json.get("gender"));
        birthdayDate =      Converters.fromStringToDate((String)json.get("birthdayDate"));
        professions =       Converters.fromStringToProfessions((String)json.get("professions"));
        aboutMe =           (String)json.get("aboutMe");
        Timestamp ts =      (Timestamp)json.get("lastUpdated");
        if(ts!=null)        {lastUpdated =ts.getSeconds();}
        else                {lastUpdated = Long.valueOf(0);}
        isDeleted =         (Boolean)json.get("isDeleted");
    }

    public String getEmail()                                    {return email;}
    public void setEmail(String email)                          {this.email = email;}
    public String getLastName()                                 {return lastName;}
    public void setLastName(String lastName)                    {this.lastName = lastName;}
    public String getFirstName()                                {return firstName;}
    public void setFirstName(String firstName)                  {this.firstName = firstName;}
    public Gender getGender()                                   {return gender;}
    public void setGender(Gender gender)                        {this.gender = gender;}
    public Date getBirthdayDate()                               {return birthdayDate;}
    public void setBirthdayDate(Date birthdayDate)              {this.birthdayDate = birthdayDate;}
    public List<Profession> getProfessions()                    {return professions;}
    public void setProfessions(List<Profession> professions)    {this.professions = professions;}
    public String getAboutMe()                                  {return aboutMe;}
    public void setAboutMe(String aboutMe)                      {this.aboutMe = aboutMe;}
    public Long getLastUpdated()                        {return lastUpdated;}
    public void setLastUpdated(Long lastUpdated)        {this.lastUpdated = lastUpdated;}
    public Boolean getDeleted()                         {return isDeleted;}
    public void setDeleted(Boolean deleted)             {isDeleted = deleted;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return email.equals(tutor.email);
    }
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public Map<String, Object> toJson(){
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("lastName",  lastName);
        data.put("firstName", firstName);
        data.put("birthdayDate", Converters.fromDateToString(birthdayDate));
        data.put("gender", Converters.fromGenderToString(gender));
        data.put("professions",  Converters.fromProfessionsToString(professions));
        data.put("aboutMe",  aboutMe);
        data.put("lastUpdated", FieldValue.serverTimestamp());
        data.put("isDeleted", isDeleted);
        return data;
    }

    static public void setLocalLatUpdateTime(Long timeStamp){
        SharedPreferences.Editor editor = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong("TutorLastUpdate", timeStamp);
        editor.commit();
    }

    static public Long getLocalLatUpdateTime(){
        return MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("TutorLastUpdate", 0);
    }
}
