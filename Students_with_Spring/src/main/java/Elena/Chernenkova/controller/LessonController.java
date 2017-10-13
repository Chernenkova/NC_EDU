package Elena.Chernenkova.controller;

import Elena.Chernenkova.Service.LessonService;
import Elena.Chernenkova.Service.TeacherService;
import Elena.Chernenkova.entity.Lesson;
import Elena.Chernenkova.entity.Student;
import Elena.Chernenkova.entity.Teacher;
import Elena.Chernenkova.repository.LessonRepository;
import Elena.Chernenkova.repository.StudentRepository;
import Elena.Chernenkova.repository.TeacherRepository;
import Elena.Chernenkova.wrapper.LessonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 123 on 29.09.2017.
 */
@RestController
@RequestMapping("/lessons")
public class LessonController {
    private LessonService lessonService;

    @Autowired
    LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{lessonId}")
    ResponseEntity<Lesson> getLesson(@PathVariable Integer lessonId){
        return lessonService.getLesson(lessonId);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getAllLessons(){
        return lessonService.getAllLessons();
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Lesson> createLesson(@RequestBody LessonWrapper lessonWrapper){
        return lessonService.createLesson(lessonWrapper);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{lessonId}")
    ResponseEntity<Lesson> updateLesson(@RequestBody LessonWrapper lessonWrapper, @PathVariable Integer lessonId){
        return lessonService.updateLesson(lessonWrapper, lessonId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{lessonId}")
    ResponseEntity deleteLesson(@PathVariable Integer lessonId){
        return lessonService.deleteLesson(lessonId);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAllLessons(){
        return lessonService.deleteAllLessons();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{lessonId}/students")
    ResponseEntity<Lesson> getStudentsFromLesson(@PathVariable Integer lessonId) throws IOException{
        return lessonService.getStudentsFromLesson(lessonId);
    }
}
