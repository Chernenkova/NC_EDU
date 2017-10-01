package Elena.Chernenkova.wrapper;

import java.util.List;
import java.util.Set;

/**
 * Created by 123 on 01.10.2017.
 */
public class LessonWrapper {
    private String lessonName, start, finish, place;
    private Integer teacher;
    private Set<Integer> students;

    public LessonWrapper() {}

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
