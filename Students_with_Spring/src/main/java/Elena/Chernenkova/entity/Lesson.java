package Elena.Chernenkova.entity;

import Elena.Chernenkova.Service.LessonService;
import Elena.Chernenkova.wrapper.LessonWrapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by 123 on 20.09.2017.
 */
@Entity(name = "lesson")
@NamedQuery(name = "Lesson.getLesson", query = "SELECT a FROM lesson a WHERE a.lessonId = :lessonId")
public class Lesson implements Serializable{
    @Id
    @GeneratedValue
    private Integer lessonId;

    @Column
    String lessonName;
    @Column
    Date lessonDate;
    @Column
    String lessonPlace;

    @ManyToOne(targetEntity = Teacher.class)
    private Teacher lessonTeacher;

    @ManyToMany(targetEntity = Student.class)
    private Set<Student> students;

    public Lesson() {}

    public Lesson(LessonWrapper lessonWrapper, Teacher teacher, Set<Student> students) {
        this.lessonName = lessonWrapper.getLessonName();
        this.lessonDate = LessonService.toDate(lessonWrapper.getLessonDate());
        this.lessonPlace = lessonWrapper.getLessonPlace();
        this.lessonTeacher = teacher;
        this.students = students;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Date getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(Date lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getLessonPlace() {
        return lessonPlace;
    }

    public void setLessonPlace(String place) {
        this.lessonPlace = place;
    }

    public Teacher getLessonTeacher() {
        return lessonTeacher;
    }

    public void setLessonTeacher(Teacher lessonTeacher) {
        this.lessonTeacher = lessonTeacher;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

}
