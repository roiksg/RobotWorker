package com.example.robotworker.ImportentClass;

import java.util.Iterator;
import java.util.LinkedList;

public class Task {

    private static int currentID = 0;
    private int id; // id задачи
    private String description; // Описание задачи
    private  boolean broadcast; // Маркер для отправки всем
    private  String workType; // Необходимый тип работ
    private  int complexityWork; // Объем работ
    private final LinkedList<Robot> robotWorker = new LinkedList<>(); // Список роботов трудящихся над задачей
    private int maxRobotWork; // Максимальное кол-во роботов для этой задачи
    private int progress = 0; // Прогресс выполнения задачи
    ClassLog logs = ClassLog.getInstance();
    public int robotWorkerValue; //Вспомогательное значение т.к robotWorker.size() изначально null

    // Конструктор для таски
    public Task(String description, boolean broadcast, String workType, int complexityWork, int maxRobotWork) {
        this.description = description;
        this.broadcast = broadcast;
        this.workType = workType;
        this.complexityWork = complexityWork;
        this.maxRobotWork = maxRobotWork;
        this.id = currentID++;
        if (robotWorker.isEmpty()) {
            robotWorkerValue = 0;
        } else {
            robotWorkerValue = robotWorker.size();
        }
        logs.addLog("Add new task id: " +id);
    }

    // Получение ID
    public int getID() {
        return id;
    }
    // Получение типа задачи
    public String getWorkType() {
        return workType;
    }
    // Получение количества работающих роботов
    public int getCountRobotWork() {
        if (robotWorker.isEmpty()) {
            robotWorkerValue = 0;
        } else {
            robotWorkerValue = robotWorker.size();
        }
        return robotWorkerValue;
    }
    // Получение мах. кол-во роботов
    public int getmaxRobotWork() {
        return maxRobotWork;
    }
    // Получение описания задачи
    public String getDescription() {
        return description;
    }
    // Получение Broadcast
    public boolean isBroadcast() {
        return broadcast;
    }
    // Добавление роботов к задаче
    public void addRobot(Robot robot) {
        logs.addLog("Add to task robot id" + robot.getId() + " type: " + robot.getType() + " description: " + description);
            robotWorker.add(robot);
            robot.setIsWork(true);
    }
    // Освобождение роботов от тяжкого бремени работы по окончанию работы
    public void removeRobot() {
        Iterator<Robot> iterator = robotWorker.iterator(); // Получаем итератор

        while (iterator.hasNext()) { // Пока есть элементы
            Robot robot = iterator.next(); // Получаем следующий элемент
            robot.setIsWork(false); // Вызываем метод isWork()
            logs.addLog("Robot id: " + robot.getId() + " is free");
        }
    }
    // Выполнение работы где 1 робот дает +1 прогресса выполнения за 0,5 секунду
    public void startTask() throws InterruptedException {
        while (complexityWork > progress) {
            progress += robotWorker.size();
            logs.addLog("Task" + id + " progress: " + progress + " / " + complexityWork);
            Thread.sleep(500);
        }
        removeRobot();
    }

}
