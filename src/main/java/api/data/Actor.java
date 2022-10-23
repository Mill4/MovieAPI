package api.data;

import java.io.Serializable;

public class Actor implements Serializable {
    public String firstName;
    public String lastName;


    public void setFirstName(String name) {
        firstName = name;
    }

    public void setLastName(String name) {
        lastName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


}
