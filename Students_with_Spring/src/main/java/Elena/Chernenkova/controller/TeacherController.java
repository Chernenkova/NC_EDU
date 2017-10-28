package Elena.Chernenkova.controller;

import Elena.Chernenkova.Service.TeacherService;
import Elena.Chernenkova.entity.Teacher;
import Elena.Chernenkova.wrapper.TeacherWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 123 on 29.09.2017.
 */
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{teacherId}")
    ResponseEntity<Teacher> getTeacher(@PathVariable Integer teacherId) {
        return teacherService.getTeacher(teacherId);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Teacher> createTeacher(@RequestBody TeacherWrapper teacherWrapper) {
        return teacherService.createTeacher(teacherWrapper);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{teacherId}")
    ResponseEntity<Teacher> updateTeacher(@RequestBody TeacherWrapper teacherWrapper, @PathVariable Integer teacherId) {
        return teacherService.updateTeacher(teacherWrapper, teacherId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{teacherId}")
    ResponseEntity deleteTeacher(@PathVariable Integer teacherId) {
        return teacherService.deleteTeacher(teacherId);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAllTeachers() {
        return teacherService.deleteAllTeachers();
    }
}
