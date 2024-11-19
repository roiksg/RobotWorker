package com.example.robotworker.controller;

import com.example.robotworker.ImportentClass.ClassLog;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedList;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    ClassLog logs = ClassLog.getInstance();

    @GetMapping("/get-logs")
    public LinkedList<String> getLogs() {
        // Возвращаем текущий список логов
        return logs.takeLogs();
    }
}
