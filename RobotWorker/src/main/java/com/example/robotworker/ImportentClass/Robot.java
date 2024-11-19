package com.example.robotworker.ImportentClass;

public class Robot {

    private static int currentID = 1; // значение для инкриментации ID
    private int id; // ID робота
    private boolean isWork = false; // Статус робота
    private String robotType; // Тип робота
    private boolean isAlive = true; // Жив ли робот

    ClassLog log = ClassLog.getInstance();

    public Robot(String robotType) {

        this.id = currentID++;
        this.robotType = robotType;
        log.addLog("Create robot id: " + id + " robotType: " + robotType);
    }

    // Получение Id робота
    public String getId() {
        return Integer.toString(id);
    }
    // Получение типа робота
    public String getType() {
        return robotType;
    }
    // Изменение статуса работы робота
    public void setIsWork(boolean isWork) {
        this.isWork = isWork;
    }
    //Получение статуса работы робота
    public boolean getIsWork() {
        return isWork;
    }
    // Получение статуса жизни робота
    public boolean isAlive() {
        return isAlive;
    }
    // Убийство робота
    public void killRobot() {
        isAlive = false;
        log.addLog("Kill robot id: " + id + " robotType: " + robotType);
    }
}
