package Elena.Chernenkova.Service;

import Elena.Chernenkova.entity.Lesson;
import Elena.Chernenkova.entity.Student;
import Elena.Chernenkova.entity.Teacher;
import Elena.Chernenkova.repository.LessonRepository;
import Elena.Chernenkova.repository.StudentRepository;
import Elena.Chernenkova.repository.TeacherRepository;
import Elena.Chernenkova.wrapper.LessonWrapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 123 on 11.10.2017.
 */
@Component
public class LessonService {
    private final LessonRepository lessonRepository;
    private TeacherRepository teacherRepository;
    private StudentRepository studentRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.lessonRepository = lessonRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public ResponseEntity getLesson(Integer lessonId){
        return new ResponseEntity<Lesson>(lessonRepository.findOne(lessonId), HttpStatus.OK);
    }

    public ResponseEntity getAllLessons(){
        return new ResponseEntity<>(lessonRepository.findAll(new Sort("lessonName")), HttpStatus.OK);
    }

    public ResponseEntity createLesson(LessonWrapper lessonWrapper){
        Teacher teacher = teacherRepository.findOne(lessonWrapper.getTeacher());
        Set<Student> students = getStudentsSetFromStudentsIdSet(lessonWrapper.getStudents());
        Lesson newLesson = new Lesson(lessonWrapper, teacher, students);
        lessonRepository.save(newLesson);
        teacher.getTeacherLessons().add(newLesson);
        teacherRepository.saveAndFlush(teacher);
        return new ResponseEntity<Lesson>(newLesson, HttpStatus.CREATED);
    }

    public ResponseEntity updateLesson(LessonWrapper lessonWrapper, Integer lessonId){
        Lesson currentLesson = lessonRepository.findOne(lessonId);
        Teacher teacher = currentLesson.getLessonTeacher();
        teacher.getTeacherLessons().remove(currentLesson);
        teacherRepository.saveAndFlush(teacher);
        currentLesson.setLessonName(lessonWrapper.getLessonName());
        currentLesson.setLessonDate(toDate(lessonWrapper.getLessonDate()));
        currentLesson.setLessonPlace(lessonWrapper.getLessonPlace());
        teacher = teacherRepository.findOne(lessonWrapper.getTeacher());
        currentLesson.setLessonTeacher(teacher);
        teacher.getTeacherLessons().add(currentLesson);
        lessonRepository.saveAndFlush(currentLesson);
        teacherRepository.saveAndFlush(teacher);
        return new ResponseEntity<Lesson>(currentLesson, HttpStatus.OK);
    }

    public ResponseEntity deleteLesson(Integer lessonId){
        Lesson lesson = lessonRepository.findOne(lessonId);
        lessonRepository.delete(lessonId);
        lesson.getLessonTeacher().getTeacherLessons().remove(lesson);
        teacherRepository.saveAndFlush(lesson.getLessonTeacher());
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity deleteAllLessons(){
        lessonRepository.deleteAll();
        Iterator<Teacher> iterator = teacherRepository.findAll().iterator();
        while(iterator.hasNext()){
            Teacher teacher = iterator.next();
            teacher.setTeacherLessons(new HashSet<Lesson>());
            teacherRepository.saveAndFlush(teacher);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity getStudentsFromLesson(Integer lessonId) throws IOException{
        writeIntoExcel(lessonRepository.findOne(lessonId).getStudents(), lessonRepository.findOne(lessonId));
        return new ResponseEntity<>(lessonRepository.findOne(lessonId).getStudents(), HttpStatus.OK);
    }

    private Set<Student> getStudentsSetFromStudentsIdSet(Set<Integer> studentsId){
        Set<Student> students = new HashSet<>();
        Iterator<Integer> iterator = studentsId.iterator();
        while(iterator.hasNext()){
            students.add(studentRepository.findOne(iterator.next()));
        }
        return students;
    }

    @SuppressWarnings("deprecation")
    public static void writeIntoExcel(Set<Student> students, Lesson lesson) throws IOException {
        Workbook book = new HSSFWorkbook();
        int i = 0, j = 0;
        Sheet sheet = book.createSheet("Students");
        Iterator<Student> iterator = students.iterator();
        while(iterator.hasNext()) {
            Student student = iterator.next();
            Row row = sheet.createRow(i);
            Cell name = row.createCell(0);
            name.setCellValue(student.getStudentName());
            Cell number = row.createCell(1);
            number.setCellValue(student.getStudentNumber());
            Cell department = row.createCell(2);
            department.setCellValue(student.getStudentDepartment().getDepartmentName());
            i++;
        }
        book.write(new FileOutputStream(lesson.getLessonName() + ".xls"));
        book.close();
    }

    public static Date toDate(String dateString){
        String[] a = dateString.split(" ");
        Date date = new Date();
        date.setYear(Integer.parseInt(a[0]) - 1900);
        date.setMonth(Integer.parseInt(a[1]) - 1);
        date.setDate(Integer.parseInt(a[2]));
        date.setHours(Integer.parseInt(a[3]));
        date.setMinutes(Integer.parseInt(a[4]));
        date.setSeconds(0);
        return date;
    }
}
