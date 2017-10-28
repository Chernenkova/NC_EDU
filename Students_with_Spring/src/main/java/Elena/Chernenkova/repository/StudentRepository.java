package Elena.Chernenkova.repository;

import Elena.Chernenkova.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by 123 on 20.09.2017.
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {

}