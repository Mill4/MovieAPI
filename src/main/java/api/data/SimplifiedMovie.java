package api.data;

import java.io.Serializable;

public class SimplifiedMovie implements Serializable {
    private String name;
    private int year;
    private int ageLimit;

    public SimplifiedMovie(Movie m) {
        name = m.getName();
        year = m.getYear();
        ageLimit = m.getAgeLimit();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }
}
