package pl.gromada.todo_app_springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.gromada.todo_app_springboot.enums.Category;
import pl.gromada.todo_app_springboot.model.Task;
import pl.gromada.todo_app_springboot.repo.TaskRepository;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public String allTasks(Model model) {
        List<Task> allTasks = taskRepository.findAll();
        model.addAttribute("tasks", allTasks);
        return "index";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("task", new Task());
        Category[] categories = Category.values();
        model.addAttribute("categories", categories);
        return "taskAddForm";
    }

    @PostMapping
    public String addTask(@ModelAttribute Task task, RedirectAttributes redirectAttributes) {
        if (task.getName().equals("")) {
            redirectAttributes.addFlashAttribute("message", "Task hasn't been added");
        } else {
            taskRepository.save(task);
            redirectAttributes.addFlashAttribute("message", "Task has been added");
        }

        return "redirect:/tasks";

    }


}
