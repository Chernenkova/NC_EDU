package Elena.Chernenkova.entity;

import Elena.Chernenkova.wrapper.TeacherWrapper;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 123 on 12.09.2017.
 */
@Entity(name = "teacher")
@NamedQueries({ @NamedQuery(name = "Teacher.getTeacher", query = "SELECT a FROM teacher a WHERE a.teacherName = :teacherName"),
                @NamedQuery(name = "Teacher.getAllTeachers", query = "SELECT a.teacherName FROM teacher a")
})
public class Teacher implements Serializable{
    @Id
    @GeneratedValue
    private Integer teacherId;

    @Column
    String teacherName;
    @Column
    String teacherNumber;

    @JsonIgnore
    @ManyToOne(targetEntity = Department.class)
    private Department teacherDepartment;

    @JsonIgnore
    @OneToMany(targetEntity = Lesson.class)
    private Set<Lesson> teacherLessons;

    public Teacher(TeacherWrapper teacherWrapper, Department department) {
        this.teacherName = teacherWrapper.getTeacherName();
        this.teacherNumber = teacherWrapper.getTeacherNumber();
        this.teacherDepartment = department;
        this.teacherLessons = new HashSet<>();
        department.getTeachers().add(this);
    }

    public Teacher() {}

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public Department getTeacherDepartment() {
        return teacherDepartment;
    }

    public void setTeacherDepartment(Department department) {
        this.teacherDepartment = department;
    }

    public Set<Lesson> getTeacherLessons() {
        return teacherLessons;
    }

    public void setTeacherLessons(Set<Lesson> lessons) {
        this.teacherLessons = lessons;
    }
}
