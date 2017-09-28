package Elena.Chernenkova.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by 123 on 12.09.2017.
 */
@Entity(name = "student")
@NamedQueries({ @NamedQuery(name = "Student.getStudent", query = "SELECT a FROM student a WHERE a.studentName = :studentName"),
                @NamedQuery(name = "Student.getAllStudents", query = "SELECT a.studentName FROM student a")})
public class Student implements Serializable{
    //@JsonPoperty

    @Id
    @GeneratedValue
    private Integer studentId;

    @Column
    String studentName;
    @Column
    String studentNumber;

    @ManyToOne(targetEntity = Department.class)
    private Department department;


    public Student(String studentName, String studentNumber, Department department) {
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.department = department;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
