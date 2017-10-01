package Elena.Chernenkova.repository;

import Elena.Chernenkova.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by 123 on 29.09.2017.
 */
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

}
