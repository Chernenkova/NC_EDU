package Elena.Chernenkova.repository;

import Elena.Chernenkova.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by 123 on 29.09.2017.
 */
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

}
