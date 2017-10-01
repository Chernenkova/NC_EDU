package Elena.Chernenkova.entity;

import Elena.Chernenkova.wrapper.DepartmentWrapper;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 123 on 12.09.2017.
 */
@Entity(name = "department")
@NamedQueries({ @NamedQuery(name = "Department.getDepartment", query = "SELECT a FROM department a WHERE a.departmentName = :departmentName"),
                @NamedQuery(name = "Department.getAllDepartments", query = "SELECT a.departmentName FROM department a")})
public class Department implements Serializable{
    @Id
    @GeneratedValue
    private Integer departmentId;

    @Column
    String departmentName;
    @Column
    String deanName;
    @Column
    String deanNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private Set<Student> students;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private Set<Teacher> teachers;


    public Department(String departmentName, String deanName, String deanNumber) {
        this.departmentName = departmentName;
        this.deanName = deanName;
        this.deanNumber = deanNumber;
        this.students = new HashSet<>();
        this.teachers = new HashSet<>();
    }
    public Department(DepartmentWrapper departmentWrapper) {
        this.departmentName = departmentWrapper.getDepartmentName();
        this.deanName = departmentWrapper.getDeanName();
        this.deanNumber = departmentWrapper.getDeanNumber();
        this.students = new HashSet<>();
        this.teachers = new HashSet<>();
    }

    public Department() {}

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDeanName() {
        return deanName;
    }

    public void setDeanName(String deanName) {
        this.deanName = deanName;
    }

    public String getDeanNumber() {
        return deanNumber;
    }

    public void setDeanNumber(String deanNumber) {
        this.deanNumber = deanNumber;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
}
