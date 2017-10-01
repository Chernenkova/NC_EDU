package Elena.Chernenkova.wrapper;

/**
 * Created by 123 on 01.10.2017.
 */
public class TeacherWrapper {
    private String teacherName, teacherNumber;
    private Integer department;

    public TeacherWrapper() {}

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

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }
}
