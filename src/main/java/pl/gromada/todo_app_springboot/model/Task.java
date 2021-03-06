package pl.gromada.todo_app_springboot.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.gromada.todo_app_springboot.enums.Category;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTask;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String date;
    private Integer priority;
    private Boolean done;


}
