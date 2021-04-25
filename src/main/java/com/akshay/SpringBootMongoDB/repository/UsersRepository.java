package com.akshay.SpringBootMongoDB.repository;

import com.akshay.SpringBootMongoDB.document.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {

    List<Users> findByName(String name);
    List<Users> findByTeamName(String teamName);
    List<Users> findBySalary(Long salary);
}
