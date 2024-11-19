package com.example.robotworker.ImportentClass;

import java.util.LinkedList;

// Класс для хранения и добавления логов
public class ClassLog {

    private final LinkedList<String> logs = new LinkedList<>();

    private static ClassLog classLog_instance = null;

    private ClassLog() {
    }
    // Получение логов
    public LinkedList<String> takeLogs() {
        return logs;
    }

    // Делает синглтон
    public static synchronized ClassLog getInstance()
    {
        if (classLog_instance == null)
            classLog_instance = new ClassLog();

        return classLog_instance;
    }



    // Метод для добавления новых логов (например, через вызов из другой части системы)
    public void addLog(String log) {
        logs.add(log);
    }
}
