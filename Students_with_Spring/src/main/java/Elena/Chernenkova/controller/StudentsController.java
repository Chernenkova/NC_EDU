package Elena.Chernenkova.controller;

import Elena.Chernenkova.Service.StudentService;
import Elena.Chernenkova.entity.Student;
import Elena.Chernenkova.wrapper.StudentsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 123 on 20.09.2017.
 */
@RestController
@RequestMapping("/students")
public class StudentsController {
    private StudentService studentService;

    @Autowired
    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{studentId}")
    ResponseEntity<Student> getStudent(@PathVariable Integer studentId){
        return studentService.getStudent(studentId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllStudents(){
        return studentService.getAllStudents();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Student> createStudent(@RequestBody StudentsWrapper studentsWrapper){
        return studentService.createStudent(studentsWrapper);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{studentId}")
    public ResponseEntity<Student> updateStudent(@RequestBody StudentsWrapper studentsWrapper,
                                                 @PathVariable Integer studentId){
        return studentService.updateStudent(studentsWrapper, studentId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{studentId}")
    public ResponseEntity deleteStudent(@PathVariable Integer studentId){
        return studentService.deleteStudent(studentId);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteAllStudents(){
        return studentService.deleteAllStudents();
    }
}
