package pl.gromada.todo_app_springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String addUpdateTask(@ModelAttribute Task task, RedirectAttributes redirectAttributes) {

        if (task.getIdTask() == null)
            redirectAttributes.addFlashAttribute("message", "Task: "
                    + task.getName() + " has been added");
        else
            redirectAttributes.addFlashAttribute("message", "Task with id: "
                    + task.getIdTask() + " has been updated");

        taskRepository.save(task);

        return "redirect:/tasks";
    }

    @GetMapping("/done/{idTask}")
    public String doneOpenTask(@PathVariable long idTask, RedirectAttributes redirectAttributes) {
        boolean isDone = taskRepository.findById(idTask).get().getDone();
        if (isDone) {
            taskRepository.setDone(idTask, false);
            redirectAttributes.addFlashAttribute("message", "Task with id: " + idTask + " has been marked as open");
        } else {
            taskRepository.setDone(idTask, true);
            redirectAttributes.addFlashAttribute("message", "Task with id: " + idTask + " has been marked as done");
        }

        return "redirect:/tasks";
    }

    @GetMapping("/delete/{idTask}")
    public String deleteTask(@PathVariable long idTask, RedirectAttributes redirectAttributes) {
        taskRepository.deleteById(idTask);
        redirectAttributes.addFlashAttribute("message", "Task with id: " + idTask + " has been deleted");

        return "redirect:/tasks";
    }

    @GetMapping("/removeAllDone")
    public String removeAllDone(RedirectAttributes redirectAttributes) {
        int c = taskRepository.deleteAllDone();

        if(c>0)
            redirectAttributes.addFlashAttribute("message", "All done tasks has been deleted");
        else
            redirectAttributes.addFlashAttribute("message", "No open task");

        return "redirect:/tasks";
    }

    @GetMapping("/update/{idTask}")
    public String updateForm(@PathVariable long idTask, Model model) {
        Task task = taskRepository.findById(idTask).get();
        model.addAttribute("task", task);
        Category[] categories = Category.values();
        model.addAttribute("categories", categories);

        return "taskUpdateForm";
    }

}
