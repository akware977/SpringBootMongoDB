package com.akshay.SpringBootMongoDB.resource;

import com.akshay.SpringBootMongoDB.document.Users;
import com.akshay.SpringBootMongoDB.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/users")
public class UserResource {

    private UsersRepository usersRepository;

    public UserResource(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Users>> getAllUsers(@RequestParam(required = false) String name) {
        try {
            List<Users> users = new ArrayList<>();

            if (name == null)
                this.usersRepository.findAll().forEach(users::add);
            else
                this.usersRepository.findByName(name).forEach(users::add);

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<Users> getSingleUser(@PathVariable("id") String id) {
        System.out.println("ID:" + id);
        Optional<Users> userData = this.usersRepository.findById(id);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/saveuser")
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
        try {
            Users _user = this.usersRepository.save(new Users(users.getName(), users.getTeamName(), users.getSalary()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateuser/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable("id") String id, @RequestBody Users users) {
        Optional<Users> usersData = this.usersRepository.findById(id);
        if (usersData.isPresent()) {
            Users _user = usersData.get();
            _user.setName(users.getName());
            _user.setTeamName(users.getTeamName());
            _user.setSalary(users.getSalary());
            return new ResponseEntity<>(this.usersRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<HttpStatus> deleteSingleResponse(@PathVariable("id") String id) {
        try {
            this.usersRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
