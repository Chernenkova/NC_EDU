package Elena.Chernenkova.entity;

import Elena.Chernenkova.wrapper.LessonWrapper;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
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
    String start;
    @Column
    String finish;
    @Column
    String place;

    @ManyToOne(targetEntity = Teacher.class)
    private Teacher teacher;

    @ManyToMany(targetEntity = Student.class)
    private Set<Student> students;


    public Lesson(LessonWrapper lessonWrapper, Teacher teacher, Set<Student> students) {
        this.lessonName = lessonWrapper.getLessonName();
        this.start = lessonWrapper.getStart();
        this.finish = lessonWrapper.getFinish();
        this.place = lessonWrapper.getPlace();
        this.teacher = teacher;
        this.students = students;
        teacher.getLessons().add(this);
    }

    public Lesson() {
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
