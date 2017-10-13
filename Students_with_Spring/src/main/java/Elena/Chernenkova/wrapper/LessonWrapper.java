package Elena.Chernenkova.wrapper;

import java.util.Date;
import java.util.Set;

/**
 * Created by 123 on 01.10.2017.
 */
public class LessonWrapper {
    private String lessonName;
    private String lessonDate;
    private String lessonPlace;
    private Integer teacher;
    private Set<Integer> students;

    public LessonWrapper() {}

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(String lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getLessonPlace() {
        return lessonPlace;
    }

    public void setLessonPlace(String lessonPlace) {
        this.lessonPlace = lessonPlace;
    }

    public Integer getTeacher() {
        return teacher;
    }

    public void setTeacher(Integer teacher) {
        this.teacher = teacher;
    }

    public Set<Integer> getStudents() {
        return students;
    }

    public void setStudents(Set<Integer> students) {
        this.students = students;
    }
}
