package Elena.Chernenkova.controller;

import Elena.Chernenkova.entity.Department;
import Elena.Chernenkova.entity.Student;
import Elena.Chernenkova.repository.DepartmentRepository;
import Elena.Chernenkova.repository.StudentRepository;
import Elena.Chernenkova.wrapper.StudentsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by 123 on 20.09.2017.
 */
@RestController
@RequestMapping("/students")
public class StudentsController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    StudentsController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }




    @RequestMapping(method = RequestMethod.GET, value = "/{studentId}")
    ResponseEntity<Student> getStudent(@PathVariable Integer studentId){
        return new ResponseEntity<Student>(studentRepository.findOne(studentId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getStudents(){
        return new ResponseEntity<>(studentRepository.
                findAll(new Sort("studentName")), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Student> createStudent(@RequestBody StudentsWrapper studentsWrapper){
        Department department = departmentRepository.findOne(studentsWrapper.getDepartment());
        Student newStudent = new Student(studentsWrapper, department);
        studentRepository.save(newStudent);
        departmentRepository.save(department);
        return new ResponseEntity<Student>(newStudent, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{studentId}")
    ResponseEntity<Student> updateStudent(@RequestBody StudentsWrapper studentsWrapper, @PathVariable Integer studentId){
        Student currentStudent = studentRepository.findOne(studentId);
        Department department = currentStudent.getDepartment();
        department.getStudents().remove(currentStudent);
        departmentRepository.saveAndFlush(department);
        currentStudent.setStudentName(studentsWrapper.getName());
        currentStudent.setStudentNumber(studentsWrapper.getNumber());
        department = departmentRepository.findOne(studentsWrapper.getDepartment());
        currentStudent.setDepartment(department);
        department.getStudents().add(currentStudent);
        studentRepository.saveAndFlush(currentStudent);
        departmentRepository.saveAndFlush(department);
        return new ResponseEntity<Student>(currentStudent, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{studentId}")
    ResponseEntity deleteStudent(@PathVariable Integer studentId){
        Student student = studentRepository.findOne(studentId);
        studentRepository.delete(studentId);
        student.getDepartment().getStudents().remove(student);
        departmentRepository.saveAndFlush(student.getDepartment());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAllStudents(){
        studentRepository.deleteAll();
        Iterator<Department> iterator = departmentRepository.findAll().iterator();
        while(iterator.hasNext()){
            Department department = iterator.next();
            department.setStudents(new HashSet<Student>());
            departmentRepository.saveAndFlush(department);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
