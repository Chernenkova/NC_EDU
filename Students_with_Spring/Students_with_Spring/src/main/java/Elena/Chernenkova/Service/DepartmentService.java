package Elena.Chernenkova.Service;

import Elena.Chernenkova.entity.Department;
import Elena.Chernenkova.repository.DepartmentRepository;
import Elena.Chernenkova.wrapper.DepartmentWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Created by 123 on 11.10.2017.
 */
@Component
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public ResponseEntity getDepartment(Integer departmentId){
        return new ResponseEntity<Department>(departmentRepository.findOne(departmentId), HttpStatus.OK);
    }

    public ResponseEntity getAllDepartments(){
        return new ResponseEntity<>(departmentRepository.findAll(new Sort("departmentName")), HttpStatus.OK);
    }

    public ResponseEntity createDepartment(DepartmentWrapper departmentWrapper){
        Department newDepartment = new Department(departmentWrapper);
        departmentRepository.save(newDepartment);
        return new ResponseEntity<Department>(newDepartment, HttpStatus.CREATED);
    }

    public ResponseEntity updateDepartment(DepartmentWrapper departmentWrapper, Integer departmentId){
        Department currentDepartment = departmentRepository.findOne(departmentId);
        currentDepartment.setDepartmentName(departmentWrapper.getDepartmentName());
        currentDepartment.setDeanName(departmentWrapper.getDeanName());
        currentDepartment.setDeanNumber(departmentWrapper.getDeanNumber());
        departmentRepository.saveAndFlush(currentDepartment);
        return new ResponseEntity<Department>(currentDepartment, HttpStatus.OK);
    }

    public ResponseEntity deleteDepartment(Integer departmentId){
        departmentRepository.delete(departmentId);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity deleteAllDepartments(){
        departmentRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }
}
