package Elena.Chernenkova.controller;

import Elena.Chernenkova.Service;
import Elena.Chernenkova.entity.Teacher;
import Elena.Chernenkova.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 123 on 29.09.2017.
 */
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository teacherRepository;

    @Autowired
    TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{teacherId}")
    ResponseEntity<Teacher> getTeacher(@PathVariable Integer teacherId){
        return new ResponseEntity<Teacher>(this.teacherRepository.findOne(teacherId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getTeachers(){
        return new ResponseEntity<>(this.teacherRepository.
                findAll(new Sort("teacherName")), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Teacher> createTeacher(@RequestBody String json){
        String teacherName = Service.findJson("teacherName", json);
        String teacherNumber = Service.findJson("teacherNumber", json);
        Teacher newTeacher = new Teacher(teacherName, teacherNumber);
        this.teacherRepository.save(newTeacher);
        return new ResponseEntity<Teacher>(newTeacher, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{teacherId}")
    ResponseEntity<Teacher> updateTeacher(@RequestBody String json, @PathVariable Integer teacherId){
        String teacherName = Service.findJson("teacherName", json);
        String teacherNumber = Service.findJson("teacherNumber", json);
        Teacher currentTeacher = this.teacherRepository.findOne(teacherId);
        currentTeacher.setTeacherName(teacherName);
        currentTeacher.setTeacherNumber(teacherNumber);
        this.teacherRepository.save(currentTeacher);
        return new ResponseEntity<Teacher>(currentTeacher, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{teacherId}")
    ResponseEntity deleteTeacher(@PathVariable Integer teacherId){
        this.teacherRepository.delete(teacherId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAllTeachers(){
        this.teacherRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }
}
