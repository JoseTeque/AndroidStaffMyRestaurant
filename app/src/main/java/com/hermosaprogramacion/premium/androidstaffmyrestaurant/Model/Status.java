package com.hermosaprogramacion.premium.androidstaffmyrestaurant.Model;

import androidx.annotation.NonNull;

public class Status {

    private int id;
    private String description;

    public Status(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return description;
    }
}
