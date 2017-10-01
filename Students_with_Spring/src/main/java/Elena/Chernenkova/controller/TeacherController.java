package Elena.Chernenkova.controller;

import Elena.Chernenkova.entity.Department;
import Elena.Chernenkova.entity.Teacher;
import Elena.Chernenkova.repository.DepartmentRepository;
import Elena.Chernenkova.repository.TeacherRepository;
import Elena.Chernenkova.wrapper.TeacherWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by 123 on 29.09.2017.
 */
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    private DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{teacherId}")
    ResponseEntity<Teacher> getTeacher(@PathVariable Integer teacherId){
        return new ResponseEntity<Teacher>(teacherRepository.findOne(teacherId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getTeachers(){
        return new ResponseEntity<>(teacherRepository.
                findAll(new Sort("teacherName")), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Teacher> createTeacher(@RequestBody TeacherWrapper teacherWrapper){
        Department department = departmentRepository.findOne(teacherWrapper.getDepartment());
        Teacher newTeacher = new Teacher(teacherWrapper, department);
        teacherRepository.save(newTeacher);
        departmentRepository.saveAndFlush(department);
        return new ResponseEntity<Teacher>(newTeacher, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{teacherId}")
    ResponseEntity<Teacher> updateTeacher(@RequestBody TeacherWrapper teacherWrapper, @PathVariable Integer teacherId){
        Department department = departmentRepository.findOne(teacherWrapper.getDepartment());
        Teacher currentTeacher = teacherRepository.findOne(teacherId);
        department.getTeachers().remove(currentTeacher);
        currentTeacher.setTeacherName(teacherWrapper.getTeacherName());
        currentTeacher.setTeacherNumber(teacherWrapper.getTeacherNumber());
        currentTeacher.setDepartment(department);
        teacherRepository.save(currentTeacher);
        department.getTeachers().add(currentTeacher);
        departmentRepository.saveAndFlush(department);
        return new ResponseEntity<Teacher>(currentTeacher, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{teacherId}")
    ResponseEntity deleteTeacher(@PathVariable Integer teacherId){
        Teacher teacher = teacherRepository.findOne(teacherId);
        teacherRepository.delete(teacherId);
        teacher.getDepartment().getTeachers().remove(teacher);
        departmentRepository.saveAndFlush(teacher.getDepartment());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAllTeachers(){
        this.teacherRepository.deleteAll();
        Iterator<Department> iterator = departmentRepository.findAll().iterator();
        while(iterator.hasNext()){
            Department department = iterator.next();
            department.setTeachers(new HashSet<Teacher>());
            departmentRepository.saveAndFlush(department);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
