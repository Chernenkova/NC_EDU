package Elena.Chernenkova.repository;

import Elena.Chernenkova.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by 123 on 28.09.2017.
 */
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}