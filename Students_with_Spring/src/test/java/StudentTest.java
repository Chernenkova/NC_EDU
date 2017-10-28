import Elena.Chernenkova.Application;
import Elena.Chernenkova.controller.StudentsController;
import Elena.Chernenkova.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Student.class, StudentsController.class})
public class StudentTest {
    private MockMvc mockMvc;
    //pattern builder

    @Autowired
    private StudentsController studentsController;

    @Test
    public void findAll() throws Exception{
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk());
    }

    /*@Test
    public void findOne() throws Exception{
        ResponseEntity<Student> a = studentsController;
        mockMvc.perform(get("/students"))
                .andExpect(a.getStatusCode().compareTo(HttpStatus.OK))
                .andExpect(!a.getBody().isEmpty());

    }*/
}
