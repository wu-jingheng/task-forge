package com.moxie.task_forge.repository;

import com.moxie.task_forge.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
}
