package Elena.Chernenkova.controller;

import Elena.Chernenkova.Service;
import Elena.Chernenkova.entity.Lesson;
import Elena.Chernenkova.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 123 on 29.09.2017.
 */
@RestController
@RequestMapping("/lessons")
public class LessonController {
    private final LessonRepository lessonRepository;

    @Autowired
    LessonController(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{lessonId}")
    ResponseEntity<Lesson> getLesson(@PathVariable Integer lessonId){
        return new ResponseEntity<Lesson>(this.lessonRepository.findOne(lessonId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getLessons(){
        return new ResponseEntity<>(this.lessonRepository.
                findAll(new Sort("lessonName")), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Lesson> createLesson(@RequestBody String json){
        String lessonName = Service.findJson("lessonName", json);
        String start = Service.findJson("start", json);
        String finish = Service.findJson("finish", json);
        String teacherName = Service.findJson("teacherName", json);
        String place = Service.findJson("place", json);
        Lesson newLesson = new Lesson(lessonName, start, finish, teacherName, place);
        this.lessonRepository.save(newLesson);
        return new ResponseEntity<Lesson>(newLesson, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{lessonId}")
    ResponseEntity<Lesson> updateLesson(@RequestBody String json, @PathVariable Integer lessonId){
        String lessonName = Service.findJson("lessonName", json);
        String start = Service.findJson("start", json);
        String finish = Service.findJson("finish", json);
        String teacherName = Service.findJson("teacherName", json);
        String place = Service.findJson("place", json);
        Lesson currentLesson = this.lessonRepository.findOne(lessonId);
        currentLesson.setLessonName(lessonName);
        currentLesson.setStart(start);
        currentLesson.setFinish(finish);
        currentLesson.setTeacherName(teacherName);
        currentLesson.setPlace(place);
        this.lessonRepository.save(currentLesson);
        return new ResponseEntity<Lesson>(currentLesson, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{lessonId}")
    ResponseEntity deleteLesson(@PathVariable Integer lessonId){
        this.lessonRepository.delete(lessonId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAllLessons(){
        this.lessonRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }
}
