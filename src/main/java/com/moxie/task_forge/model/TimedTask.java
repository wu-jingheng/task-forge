package com.moxie.task_forge.model;

import java.time.LocalDate;

public class TimedTask extends Task {

    private LocalDate dueDate;

    public TimedTask() {
        super();
    }

    public TimedTask(String id,
                     String title,
                     String description,
                     String assigneeId,
                     LocalDate dueDate) {
        super(id, title, description, assigneeId);
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
