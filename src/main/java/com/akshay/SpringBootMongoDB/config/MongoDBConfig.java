package com.akshay.SpringBootMongoDB.config;

import com.akshay.SpringBootMongoDB.document.Users;
import com.akshay.SpringBootMongoDB.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = UsersRepository.class)
@Configuration
public class MongoDBConfig {

    //Command Line Client for Inserting Demo Data For Test
    @Bean
    CommandLineRunner commandLineRunner(UsersRepository usersRepository) {
        return strings -> {
            usersRepository.save(new Users("Peter", "Development", 3000L));
            usersRepository.save(new Users("Sam", "Operations", 2000L));
        };
    }

}

