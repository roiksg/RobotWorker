package com.example.robotworker.controller;


import com.example.robotworker.ImportentClass.ClassLog;
import com.example.robotworker.service.MainWork;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    ClassLog logs = ClassLog.getInstance();
    MainWork mainWork = new MainWork();

    // Главная страница
    @GetMapping("/main")
    public String greeting() {
        mainWork.MainWork();
        return "main";
    }

    // Добавление робота
    @PostMapping("/add-robot")
    public String addRobot(@RequestParam String robotType){
        mainWork.createRobot(robotType);
        return "redirect:main";
    }

    // Удаление робота
    @PostMapping("/delete-robot")
    public String deleteRobot(@RequestParam String id) throws InterruptedException {
        mainWork.killRobot(id);
        return "redirect:main";
    }

    // Создание новой таски
    @PostMapping("/create-task")
    public String createTask(@RequestParam String description,
                             @RequestParam String type,
                             @RequestParam String workload,
                             @RequestParam String max_robots,
                             @RequestParam(value = "broadcast", required = false) boolean broadcast) throws InterruptedException {
        int complexityWork;
        int maxRobotWork;
        // Проверка допустимости значений для объема рабо и мах. кол-во роботов
        try {
            complexityWork = Integer.parseInt(workload);
            maxRobotWork = Integer.parseInt(max_robots);
        }
        catch (NumberFormatException e) {
            logs.addLog("invalid create task");
            return "redirect:main";
        }
        if (complexityWork <= 0 || maxRobotWork <= 0)
        {
            logs.addLog("invalid create task");
            return "redirect:main";
        }
        mainWork.createTask(description, broadcast, type, complexityWork, maxRobotWork);
        return "redirect:main";
    }

}
