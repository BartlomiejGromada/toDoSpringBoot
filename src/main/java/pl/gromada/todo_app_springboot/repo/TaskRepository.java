package pl.gromada.todo_app_springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.gromada.todo_app_springboot.model.Task;

import javax.transaction.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.done = :done WHERE t.idTask = :idTask")
    void setDone(long idTask, boolean done);

    @Transactional
    @Modifying
    @Query("DELETE FROM Task t WHERE t.done = true")
    int deleteAllDone();
}
