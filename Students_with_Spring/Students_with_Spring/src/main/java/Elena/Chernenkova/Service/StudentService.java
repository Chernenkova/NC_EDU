package Elena.Chernenkova.Service;

import Elena.Chernenkova.entity.Department;
import Elena.Chernenkova.entity.Lesson;
import Elena.Chernenkova.entity.Student;
import Elena.Chernenkova.repository.DepartmentRepository;
import Elena.Chernenkova.repository.LessonRepository;
import Elena.Chernenkova.repository.StudentRepository;
import Elena.Chernenkova.wrapper.StudentsWrapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by 123 on 11.10.2017.
 */
@Component
public class StudentService {
    private DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public StudentService(DepartmentRepository departmentRepository, StudentRepository studentRepository,
                          LessonRepository lessonRepository) {
        this.departmentRepository = departmentRepository;
        this.studentRepository = studentRepository;
        this.lessonRepository = lessonRepository;
    }

    public ResponseEntity getStudent(Integer studentId){
        return new ResponseEntity<Student>(studentRepository.findOne(studentId), HttpStatus.OK);
    }

    public ResponseEntity getStudentLessons(Integer studentId, HttpServletRequest request,
                                            HttpServletResponse response) throws IOException{
        Student student = studentRepository.findOne(studentId);
        List<Lesson> lessons = lessonRepository.findAll();
        List<Lesson> studentLessons = new LinkedList<>();
        for(int i = 0; i < lessons.size(); i++){
            if (lessons.get(i).getStudents().contains(student))
                studentLessons.add(lessons.get(i));
        }
        writeIntoExcel(studentLessons, student.getStudentName(), request, response);
        return new ResponseEntity(HttpStatus.OK);
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

    @SuppressWarnings("deprecation")
    private void writeIntoExcel(List<Lesson> lessons, String studentName, HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
        Workbook book = new HSSFWorkbook();
        int p = 0, c = 0, f = 0, j = 0;
        Sheet previous = book.createSheet("Previous Lessons");
        Sheet current = book.createSheet("Current Lessons");
        Sheet future = book.createSheet("Future Lessons");
        Iterator<Lesson> iterator = lessons.iterator();
        Row row = null;
        while(iterator.hasNext()) {
            Lesson lesson = iterator.next();
            int number = lesson.getLessonType();
            if(lesson.getLessonDate().before(new Date()))
                row = previous.createRow(p++);
            else
                if(lesson.getLessonDate().getTime() - new Date().getTime() < 7*24*3600*1000)
                    row = current.createRow(c++);
                else
                    row = future.createRow(f++);
            Cell name = row.createCell(0);
            name.setCellValue(lesson.getLessonName());
            name.setCellStyle(createStyle(book, number));
            Cell date = row.createCell(1);
            date.setCellValue(lesson.getLessonDate().toString());
            date.setCellStyle(createStyle(book, number));
            Cell place = row.createCell(2);
            place.setCellValue(lesson.getLessonPlace());
            place.setCellStyle(createStyle(book, number));
            Cell teacher = row.createCell(3);
            teacher.setCellValue(lesson.getLessonTeacher().getTeacherName());
            teacher.setCellStyle(createStyle(book, number));
        }
        //book.write(new FileOutputStream(studentName + ".xls"));
        previous.autoSizeColumn(1);
        current.autoSizeColumn(1);
        future.autoSizeColumn(1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        book.write(byteArrayOutputStream);
        book.close();
        doDownload(request, response, byteArrayOutputStream.toByteArray(), studentName);
    }

    private void doDownload(HttpServletRequest request, HttpServletResponse response, byte[] array,
                                   String studentName) throws IOException {

        ServletContext context = request.getServletContext();

        InputStream inputStream = new ByteArrayInputStream(array);
        String mimeType = context.getMimeType(studentName + ".xls");
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setContentLength(array.length);

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", studentName + ".xls");
        response.setHeader(headerKey, headerValue);

        OutputStream outStream = response.getOutputStream();
        byte[] buffer = new byte[2048];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outStream.close();
    }

    private CellStyle createStyle(Workbook book, int number){
        CellStyle[] cellStyle = new CellStyle[1];
        cellStyle[0] = book.createCellStyle();
        cellStyle[0].setFillPattern(CellStyle.SOLID_FOREGROUND);
        if (number == 1)
            cellStyle[0].setFillForegroundColor(IndexedColors.BLUE.getIndex());
        if (number == 2)
            cellStyle[0].setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        if (number == 3)
            cellStyle[0].setFillForegroundColor(IndexedColors.PINK.getIndex());
        return cellStyle[0];
    }

}
