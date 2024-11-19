package com.example.robotworker.ImportentClass;

import com.example.robotworker.ImportentClass.Robot;
import com.example.robotworker.ImportentClass.Task;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskTracker {
    private final BlockingQueue<Task> taskQueue;
    private final Map<String, Robot> robots;
    ClassLog log = ClassLog.getInstance();

    public TaskTracker(BlockingQueue<Task> taskQueue, Map<String, Robot> robots) {
        this.taskQueue = taskQueue;
        this.robots = robots;
    }

    // Поиск роботов для выполнения задачи
    public void foundWorker(Task currentTask) throws InterruptedException {
        Task task = currentTask;
        Iterator<Robot> iterator = robots.values().iterator();
        while (iterator.hasNext()) {
            if (task.getmaxRobotWork() > task.getCountRobotWork()) {
                Robot robot = iterator.next();
                if ((!robot.getIsWork() && robot.isAlive()) && (Objects.equals(robot.getType(), task.getWorkType()) || task.isBroadcast())) {
                    task.addRobot(robot);
                    robot.setIsWork(true);
                }
            }
        }
        // Создание роботов для выполнения задачи если нужных роботов не хватает
        while (task.getmaxRobotWork() > task.getCountRobotWork()) {
            Robot robot = new Robot(task.getWorkType());
            robots.put(robot.getId(), robot);
            task.addRobot(robot);
        }
    }

    // Трекер роботов
    public void trackTasks() {
        Thread taskTrackerThread = new Thread(() -> {
            ScheduledExecutorService secondExecutor = Executors.newScheduledThreadPool(1);
            secondExecutor.scheduleAtFixedRate(() -> {
                try {
                    Task currentTask = taskQueue.take();
                    log.addLog("Start task id: " + currentTask.getID());
                    foundWorker(currentTask);
                    currentTask.startTask();
                    log.addLog("Task completed and removed from queue.");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, 0, 1, TimeUnit.SECONDS); // Проверять каждую секунду
        });
        taskTrackerThread.setDaemon(true); // Опционально: позволяет завершить поток при завершении приложения
        taskTrackerThread.start();
    }
}
