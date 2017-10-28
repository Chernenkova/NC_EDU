package Elena.Chernenkova.entity;

import Elena.Chernenkova.wrapper.StudentsWrapper;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 123 on 12.09.2017.
 */
@Entity(name = "student")
@NamedQueries({ @NamedQuery(name = "Student.getStudent", query = "SELECT a FROM student a WHERE a.studentName = :studentName"),
                @NamedQuery(name = "Student.getAllStudents", query = "SELECT a.studentName FROM student a")})
public class Student implements Serializable{

    @Id
    @GeneratedValue
    private Integer studentId;

    @Column
    String studentName;
    @Column
    String studentNumber;

    @JsonIgnore
    @ManyToOne(targetEntity = Department.class)
    private Department studentDepartment;

    public Student(StudentsWrapper studentsWrapper, Department department) {
        this.studentName = studentsWrapper.getStudentName();
        this.studentNumber = studentsWrapper.getStudentNumber();
        this.studentDepartment = department;
        department.getStudents().add(this);
    }

    public Student() {
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String student_Number) {
        this.studentNumber = student_Number;
    }

    public Department getStudentDepartment() {
        return studentDepartment;
    }

    public void setStudentDepartment(Department department) {
        this.studentDepartment = department;
    }
}
