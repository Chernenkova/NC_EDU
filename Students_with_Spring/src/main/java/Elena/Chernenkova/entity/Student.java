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
    private Department department;

    @JsonIgnore
    @ManyToMany(targetEntity = Lesson.class)
    private Set<Lesson> lessons;


    public Student(StudentsWrapper studentsWrapper, Department department) {
        this.studentName = studentsWrapper.getName();
        this.studentNumber = studentsWrapper.getNumber();
        this.department = department;
        department.getStudents().add(this);
        this.lessons = new HashSet<>();
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

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }
}
