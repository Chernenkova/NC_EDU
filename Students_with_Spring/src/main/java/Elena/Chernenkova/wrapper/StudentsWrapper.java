package Elena.Chernenkova.wrapper;

import java.io.Serializable;

/**
 * Created by 123 on 01.10.2017.
 */
public class StudentsWrapper implements Serializable{
    private String name, number;
    private Integer department;

    public StudentsWrapper() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }
}
