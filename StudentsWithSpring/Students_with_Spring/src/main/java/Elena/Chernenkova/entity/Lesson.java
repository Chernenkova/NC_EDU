package Elena.Chernenkova.entity;

import javax.persistence.*;
import java.io.Serializable;

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
    String teacherName;
    @Column
    String place;

    public Lesson(String lessonName, String start, String finish, String teacherName, String place) {
        this.lessonName = lessonName;
        this.start = start;
        this.finish = finish;
        this.teacherName = teacherName;
        this.place = place;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
