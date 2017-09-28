package Elena.Chernenkova.entity;

import javax.persistence.*;
import java.io.Serializable;

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

/*
    @ManyToOne(targetEntity = Department.class)
    private Department department;
*/


    public Teacher(String teacherName, String teacherNumber) {
        this.teacherName = teacherName;
        this.teacherNumber = teacherNumber;
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
}
