package Elena.Chernenkova.controller;

import Elena.Chernenkova.Service.DepartmentService;
import Elena.Chernenkova.entity.Department;
import Elena.Chernenkova.wrapper.DepartmentWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 123 on 28.09.2017.
 */
@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private DepartmentService departmentService;

    @Autowired
    DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{departmentId}")
    ResponseEntity<Department> getDepartment(@PathVariable Integer departmentId){
        return departmentService.getDepartment(departmentId);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Department> createDepartment(@RequestBody DepartmentWrapper departmentWrapper){
        return departmentService.createDepartment(departmentWrapper);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{departmentId}")
    ResponseEntity<Department> updateDepartment(@RequestBody DepartmentWrapper departmentWrapper, @PathVariable Integer departmentId){
        return departmentService.updateDepartment(departmentWrapper, departmentId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{departmentId}")
    ResponseEntity deleteDepartment(@PathVariable Integer departmentId){
        return departmentService.deleteDepartment(departmentId);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAllDepartments(){
        return departmentService.deleteAllDepartments();
    }
}
