package com.example.robotworker.service;

import com.example.robotworker.ImportentClass.ClassLog;
import com.example.robotworker.ImportentClass.Robot;
import com.example.robotworker.ImportentClass.Task;
import com.example.robotworker.ImportentClass.TaskTracker;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;

@Service
public class MainWork {
    private final BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    private final Map<String, Robot> robots = new ConcurrentHashMap<>();
    ClassLog logs = ClassLog.getInstance();
    TaskTracker taskTracker;

    // Запускает трекер
    public void MainWork() {
        taskTracker = new TaskTracker(taskQueue, robots);
        taskTracker.trackTasks();
    }

    // Создает роботов
    public void createRobot(String robotType) {
        Robot robot = new Robot(robotType);
        robots.put(robot.getId(), robot);
    }

    // Убивает роботов
    public void killRobot(String robotId) {
        if (robots.containsKey(robotId)){
            robots.get(robotId).killRobot();
        } else {
            logs.addLog("Robot with id " + robotId + " not found");
        }

    }

    // Создает таску
    public void createTask(String description, boolean broadcast, String workType, int complexityWork, int maxRobotWork) throws InterruptedException {
        Task task = new Task(description, broadcast, workType, complexityWork, maxRobotWork);
        taskQueue.put(task);
    }



}
