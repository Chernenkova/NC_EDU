package Elena.Chernenkova.controller;

import Elena.Chernenkova.entity.Department;
import Elena.Chernenkova.repository.DepartmentRepository;
import Elena.Chernenkova.wrapper.DepartmentWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 123 on 28.09.2017.
 */
@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;

    @Autowired
    DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{departmentId}")
    ResponseEntity<Department> getDepartment(@PathVariable Integer departmentId){
        return new ResponseEntity<Department>(departmentRepository.findOne(departmentId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getDepartments(){
        return new ResponseEntity<>(departmentRepository.
                findAll(new Sort("departmentName")), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Department> createDepartment(@RequestBody DepartmentWrapper departmentWrapper){
        Department newDepartment = new Department(departmentWrapper);
        departmentRepository.save(newDepartment);
        return new ResponseEntity<Department>(newDepartment, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{departmentId}")
    ResponseEntity<Department> updateDepartment(@RequestBody DepartmentWrapper departmentWrapper, @PathVariable Integer departmentId){
        Department currentDepartment = departmentRepository.findOne(departmentId);
        currentDepartment.setDepartmentName(departmentWrapper.getDepartmentName());
        currentDepartment.setDeanName(departmentWrapper.getDeanName());
        currentDepartment.setDeanNumber(departmentWrapper.getDeanNumber());
        departmentRepository.saveAndFlush(currentDepartment);
        return new ResponseEntity<Department>(currentDepartment, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{departmentId}")
    ResponseEntity deleteDepartment(@PathVariable Integer departmentId){
        departmentRepository.delete(departmentId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAllDepartments(){
        departmentRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    public DepartmentRepository getDepartmentRepository() {
        return departmentRepository;
    }
}
