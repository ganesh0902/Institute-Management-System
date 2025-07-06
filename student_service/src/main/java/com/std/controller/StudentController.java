package com.std.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.std.event.StudentDroppedEvent;
import com.std.event.StudentEnrolledEvent;
import com.std.rabbitService.StudentEventPublisher;

@RestController
@RequestMapping("/newStudent")
public class StudentController {

    private final StudentEventPublisher publisher;

    public StudentController(StudentEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/enroll")
    public String enroll(@RequestParam Long studentId, @RequestParam Long courseId, @RequestParam Long batchId) {
        publisher.publishEnrolled(new StudentEnrolledEvent(studentId, courseId, batchId));
        return "Student enrolled event sent!";
    }

    @PostMapping("/drop")
    public String drop(@RequestParam Long studentId, @RequestParam String reason) {
        publisher.publishDropped(new StudentDroppedEvent(studentId, reason));
        return "Student dropped event sent!";
    }
}
