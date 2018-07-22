package com.owiwi.model;

import java.io.Serializable;

public class SearchRequest implements Serializable{

    /** number of years age difference **/
    private String age;

    /** education level **/
    private String educationLevel;

    /** gender **/
    private String gender;

    /** user id **/
    private String id;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
