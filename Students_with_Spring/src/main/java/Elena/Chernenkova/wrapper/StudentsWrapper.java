package Elena.Chernenkova.wrapper;

import java.io.Serializable;

/**
 * Created by 123 on 01.10.2017.
 */
public class StudentsWrapper implements Serializable{
    private String studentName;
    private String studentNumber;
    private Integer studentDepartment;

    public StudentsWrapper() {}

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getStudentDepartment() {
        return studentDepartment;
    }

    public void setStudentDepartment(Integer studentDepartment) {
        this.studentDepartment = studentDepartment;
    }
}
