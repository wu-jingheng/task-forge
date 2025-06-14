package com.moxie.task_forge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class TaskForgeApplicationTests {

	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0")
			.withReuse(true);

	static {
		mongoDBContainer.start();
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		String uri = mongoDBContainer.getReplicaSetUrl() + "task-forge-db";
		System.out.println("MongoDB Testcontainer URI: " + uri);
		registry.add("spring.data.mongodb.uri", () -> uri);
	}

	@Test
	void contextLoads() {
	}

}
