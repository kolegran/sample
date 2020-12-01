package com.gitlab.kolegran.sample.cupboard;

import java.util.Objects;

public class Cupboard {

    private int id;
    private String title;

    public Cupboard(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cupboard cupboard = (Cupboard) o;
        return id == cupboard.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
