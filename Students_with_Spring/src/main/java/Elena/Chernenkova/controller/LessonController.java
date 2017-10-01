package Elena.Chernenkova.controller;

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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 123 on 29.09.2017.
 */
@RestController
@RequestMapping("/lessons")
public class LessonController {
    private final LessonRepository lessonRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    LessonController(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{lessonId}")
    ResponseEntity<Lesson> getLesson(@PathVariable Integer lessonId){
        return new ResponseEntity<Lesson>(lessonRepository.findOne(lessonId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getLessons(){
        return new ResponseEntity<>(lessonRepository.
                findAll(new Sort("lessonName")), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Lesson> createLesson(@RequestBody LessonWrapper lessonWrapper){
        Teacher teacher = teacherRepository.findOne(lessonWrapper.getTeacher());
        Set<Integer> studentsId = lessonWrapper.getStudents();
        Set<Student> students = new HashSet<>();
        Iterator<Integer> iterator = studentsId.iterator();
        while(iterator.hasNext()){
            students.add(studentRepository.findOne(iterator.next()));
        }
        Lesson newLesson = new Lesson(lessonWrapper, teacher, students);
        lessonRepository.save(newLesson);
        Iterator<Student> iteratorS = students.iterator();
        while(iteratorS.hasNext()){
            Student student = iteratorS.next();
            student.getLessons().add(newLesson);
            studentRepository.saveAndFlush(student);
        }
        lessonRepository.saveAndFlush(newLesson);
        teacher.getLessons().add(newLesson);
        teacherRepository.saveAndFlush(teacher);
        return new ResponseEntity<Lesson>(newLesson, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{lessonId}")
    ResponseEntity<Lesson> updateLesson(@RequestBody LessonWrapper lessonWrapper, @PathVariable Integer lessonId){
        Lesson currentLesson = lessonRepository.findOne(lessonId);
        Teacher teacher = currentLesson.getTeacher();
        teacher.getLessons().remove(currentLesson);
        teacherRepository.saveAndFlush(teacher);
        currentLesson.setLessonName(lessonWrapper.getLessonName());
        currentLesson.setStart(lessonWrapper.getStart());
        currentLesson.setFinish(lessonWrapper.getFinish());
        currentLesson.setPlace(lessonWrapper.getPlace());
        teacher = teacherRepository.findOne(lessonWrapper.getTeacher());
        currentLesson.setTeacher(teacher);
        teacher.getLessons().add(currentLesson);
        lessonRepository.saveAndFlush(currentLesson);
        teacherRepository.saveAndFlush(teacher);
        return new ResponseEntity<Lesson>(currentLesson, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{lessonId}")
    ResponseEntity deleteLesson(@PathVariable Integer lessonId){
        Lesson lesson = lessonRepository.findOne(lessonId);
        this.lessonRepository.delete(lessonId);
        lesson.getTeacher().getLessons().remove(lesson);
        teacherRepository.saveAndFlush(lesson.getTeacher());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAllLessons(){
        lessonRepository.deleteAll();
        Iterator<Teacher> iterator = teacherRepository.findAll().iterator();
        while(iterator.hasNext()){
            Teacher teacher = iterator.next();
            teacher.setLessons(new HashSet<Lesson>());
            teacherRepository.saveAndFlush(teacher);
        }

        Iterator<Student> iteratorS = studentRepository.findAll().iterator();
        while(iteratorS.hasNext()){
            Student student = iteratorS.next();
            student.setLessons(new HashSet<Lesson>());
            studentRepository.saveAndFlush(student);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
