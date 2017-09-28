package Elena.Chernenkova.controller;

import Elena.Chernenkova.Service;
import Elena.Chernenkova.entity.Department;
import Elena.Chernenkova.repository.DepartmentRepository;
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
        Service.setDepartmentController(this);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{departmentId}")
    ResponseEntity<Department> getDepartment(@PathVariable Integer departmentId){
        return new ResponseEntity<Department>(this.departmentRepository.findOne(departmentId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity getDepartments(){
        return new ResponseEntity<>(this.departmentRepository.
                findAll(new Sort("departmentName")), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Department> createDepartment(@RequestBody String json){
        String departmentName = Service.findJson("departmentName", json);
        String deanName = Service.findJson("deanName", json);
        String deanNumber = Service.findJson("deanNumber", json);
        Department newDepartment = new Department(departmentName, deanName, deanNumber);
        this.departmentRepository.save(newDepartment);
        Service.departments.add(newDepartment);
        return new ResponseEntity<Department>(newDepartment, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{departmentId}")
    ResponseEntity<Department> updateDepartment(@RequestBody String json, @PathVariable Integer departmentId){
        String departmentName = Service.findJson("departmentName", json);
        String deanName = Service.findJson("deanName", json);
        String deanNumber = Service.findJson("deanNumber", json);
        Department currentDepartment = this.departmentRepository.findOne(departmentId);
        Service.departments.remove(currentDepartment);
        currentDepartment.setDepartmentName(departmentName);
        currentDepartment.setDeanName(deanName);
        currentDepartment.setDeanNumber(deanNumber);
        this.departmentRepository.save(currentDepartment);
        Service.departments.add(currentDepartment);
        return new ResponseEntity<Department>(currentDepartment, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{departmentId}")
    ResponseEntity deleteDepartment(@PathVariable Integer departmentId){
        this.departmentRepository.delete(departmentId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity deleteAllDepartments(){
        this.departmentRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    public DepartmentRepository getDepartmentRepository() {
        return departmentRepository;
    }
}
