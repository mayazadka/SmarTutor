package com.example.smartutor.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    private String password;

    public Tutor(){}
    public Tutor(String email, String lastName, String firstName, Gender gender, Date birthdayDate, List<Profession> professions, String aboutMe) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
        this.professions = professions;
        this.aboutMe = aboutMe;
    }
    public Tutor(String email, String lastName, String firstName, Gender gender, Date birthdayDate, List<Profession> professions, String aboutMe, String password) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
        this.professions = professions;
        this.aboutMe = aboutMe;
        this.password = password;
    }
    public Tutor(Map<String, Object> json){
        email =             (String)json.get("email");
        lastName =          (String)json.get("lastName");
        firstName =         (String)json.get("firstName");
        gender =            Converters.fromStringToGender((String)json.get("gender"));
        birthdayDate =      Converters.fromStringToDate((String)json.get("birthdayDate"));
        professions =       Converters.fromStringToProfessions((String)json.get("professions"));
        aboutMe =           (String)json.get("aboutMe");
        password =          (String)json.get("password");
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
    public String getPassword()                                 {return password;}
    public void setPassword(String password)                    {this.password = password;}

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
        data.put("password",  password);
        return data;
    }
}
