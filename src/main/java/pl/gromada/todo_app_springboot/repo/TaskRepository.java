package pl.gromada.todo_app_springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gromada.todo_app_springboot.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
