package Elena.Chernenkova.controller;

import Elena.Chernenkova.Service;
import Elena.Chernenkova.entity.Department;
import Elena.Chernenkova.entity.Student;
import Elena.Chernenkova.repository.StudentRepository;
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

    private final StudentRepository studentRepository;

    @Autowired
    StudentsController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        Service.setStudentsController(this);
    }



    @RequestMapping(method = RequestMethod.GET, value = "/{studentId}")
    ResponseEntity<Student> getStudent(@PathVariable Integer studentId){
        return new ResponseEntity<Student>(this.studentRepository.findOne(studentId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getStudents(){
        return new ResponseEntity<>(this.studentRepository.
                findAll(new Sort("studentName")), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Student> createStudent(@RequestBody String json){
        String name = Service.findJson("name", json);
        String number = Service.findJson("number", json);
        Integer departmentId = Integer.parseInt(Service.findJson("departmentId", json));
        Department department = Service.getDepartmentController().getDepartmentRepository().findOne(departmentId);
        Student newStudent = new Student(name, number, department);
        department.getStudents().add(newStudent);
        this.studentRepository.save(newStudent);
        return new ResponseEntity<Student>(newStudent, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{studentId}")
    ResponseEntity<Student> updateStudent(@RequestBody String json, @PathVariable Integer studentId){
        String name = Service.findJson("name", json);
        String number = Service.findJson("number", json);
        Integer departmentId = Integer.parseInt(Service.findJson("departmentId", json));
        Department department = Service.getDepartmentController().getDepartmentRepository().findOne(departmentId);
        Student currentStudent = this.studentRepository.findOne(studentId);
        department.getStudents().remove(currentStudent);
        currentStudent.setStudentName(name);
        currentStudent.setStudentNumber(number);
        currentStudent.setDepartment(department);
        department.getStudents().add(currentStudent);
        this.studentRepository.save(currentStudent);
        return new ResponseEntity<Student>(currentStudent, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{studentId}")
    ResponseEntity deleteStudent(@PathVariable Integer studentId){
        this.studentRepository.delete(studentId);
        Student student = this.studentRepository.findOne(studentId);
        student.getDepartment().getStudents().remove(student);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAllStudents(){
        this.studentRepository.deleteAll();
        Iterator<Department> iterator = Service.departments.iterator();
        while(iterator.hasNext()){
            Department department = iterator.next();
            department.getStudents().removeAll(department.getStudents());
        }
        return new ResponseEntity(HttpStatus.OK);
    }



    public StudentRepository getStudentRepository() {
        return studentRepository;
    }
}
