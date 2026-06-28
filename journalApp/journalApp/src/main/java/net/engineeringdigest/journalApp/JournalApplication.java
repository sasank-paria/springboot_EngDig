package net.engineeringdigest.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement // Enable transaction management for MongoDB operations
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
		return new org.springframework.data.mongodb.MongoTransactionManager(mongoDatabaseFactory);
	}

}
