package Elena.Chernenkova.Service;

import Elena.Chernenkova.entity.Department;
import Elena.Chernenkova.entity.Student;
import Elena.Chernenkova.repository.DepartmentRepository;
import Elena.Chernenkova.repository.StudentRepository;
import Elena.Chernenkova.wrapper.StudentsWrapper;
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
public class StudentService {
    private DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(DepartmentRepository departmentRepository, StudentRepository studentRepository) {
        this.departmentRepository = departmentRepository;
        this.studentRepository = studentRepository;
    }

    public ResponseEntity getStudent(Integer studentId){
        return new ResponseEntity<Student>(studentRepository.findOne(studentId), HttpStatus.OK);
    }

    public ResponseEntity getAllStudents(){
        return new ResponseEntity<>(studentRepository.findAll(new Sort("studentName")), HttpStatus.OK);
    }

    public ResponseEntity createStudent(StudentsWrapper studentsWrapper){
        Department department = departmentRepository.findOne(studentsWrapper.getStudentDepartment());
        Student newStudent = new Student(studentsWrapper, department);
        studentRepository.save(newStudent);
        departmentRepository.save(department);
        return new ResponseEntity<Student>(newStudent, HttpStatus.CREATED);
    }

    public ResponseEntity updateStudent(StudentsWrapper studentsWrapper, Integer studentId){
        Student currentStudent = studentRepository.findOne(studentId);
        Department department = currentStudent.getStudentDepartment();
        department.getStudents().remove(currentStudent);
        departmentRepository.saveAndFlush(department);
        currentStudent.setStudentName(studentsWrapper.getStudentName());
        currentStudent.setStudentNumber(studentsWrapper.getStudentNumber());
        department = departmentRepository.findOne(studentsWrapper.getStudentDepartment());
        currentStudent.setStudentDepartment(department);
        department.getStudents().add(currentStudent);
        studentRepository.saveAndFlush(currentStudent);
        departmentRepository.saveAndFlush(department);
        return new ResponseEntity<Student>(currentStudent, HttpStatus.OK);
    }

    public ResponseEntity deleteStudent(Integer studentId){
        Student student = studentRepository.findOne(studentId);
        studentRepository.delete(studentId);
        student.getStudentDepartment().getStudents().remove(student);
        departmentRepository.saveAndFlush(student.getStudentDepartment());
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity deleteAllStudents(){
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
