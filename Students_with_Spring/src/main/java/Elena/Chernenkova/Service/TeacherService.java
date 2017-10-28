package Elena.Chernenkova.Service;

import Elena.Chernenkova.entity.Department;
import Elena.Chernenkova.entity.Teacher;
import Elena.Chernenkova.repository.DepartmentRepository;
import Elena.Chernenkova.repository.TeacherRepository;
import Elena.Chernenkova.wrapper.TeacherWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by 123 on 11.10.2017.
 */
@Component
public class TeacherService {
    private DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(DepartmentRepository departmentRepository, TeacherRepository teacherRepository) {
        this.departmentRepository = departmentRepository;
        this.teacherRepository = teacherRepository;
    }

    public ResponseEntity getTeacher(Integer teacherId){
        return new ResponseEntity<Teacher>(teacherRepository.findOne(teacherId), HttpStatus.OK);
    }

    public ResponseEntity getAllTeachers(){
        return new ResponseEntity<>(teacherRepository.findAll(new Sort("teacherName")), HttpStatus.OK);
    }

    public ResponseEntity createTeacher(TeacherWrapper teacherWrapper){
        Department department = departmentRepository.findOne(teacherWrapper.getTeacherDepartment());
        Teacher newTeacher = new Teacher(teacherWrapper, department);
        teacherRepository.save(newTeacher);
        departmentRepository.saveAndFlush(department);
        return new ResponseEntity<Teacher>(newTeacher, HttpStatus.CREATED);
    }

    public ResponseEntity updateTeacher(TeacherWrapper teacherWrapper, Integer teacherId){
        Department department = departmentRepository.findOne(teacherWrapper.getTeacherDepartment());
        Teacher currentTeacher = teacherRepository.findOne(teacherId);
        department.getTeachers().remove(currentTeacher);
        currentTeacher.setTeacherName(teacherWrapper.getTeacherName());
        currentTeacher.setTeacherNumber(teacherWrapper.getTeacherNumber());
        currentTeacher.setTeacherDepartment(department);
        teacherRepository.save(currentTeacher);
        department.getTeachers().add(currentTeacher);
        departmentRepository.saveAndFlush(department);
        return new ResponseEntity<Teacher>(currentTeacher, HttpStatus.OK);
    }

    public ResponseEntity deleteTeacher(Integer teacherId){
        Teacher teacher = teacherRepository.findOne(teacherId);
        teacherRepository.delete(teacherId);
        teacher.getTeacherDepartment().getTeachers().remove(teacher);
        departmentRepository.saveAndFlush(teacher.getTeacherDepartment());
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity deleteAllTeachers(){
        teacherRepository.deleteAll();
        Iterator<Department> iterator = departmentRepository.findAll().iterator();
        while (iterator.hasNext()) {
            Department department = iterator.next();
            department.setTeachers(new HashSet<Teacher>());
            departmentRepository.saveAndFlush(department);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
