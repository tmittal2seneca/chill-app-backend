package com.example.assignment2.services;

import com.example.assignment2.models.UserModel;
import com.example.assignment2.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {

    @Autowired  //dependency injection
    private UserRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate; //goes beyond CRUD operations to perform advance queries.

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //get all users
    public List<UserModel> getUsers(){
        return repository.findAll();
    }

    // get user by id

    public Optional<UserModel> getUser(String id) throws Exception {
        //validation
        Optional<UserModel> register = repository.findById(id);
        if(!register.isPresent())
        {
            throw new Exception("User with " + id + " is not found");
        }
        return register;
    }

    //register the user - create the user
    public UserModel addUser(UserModel user) throws Exception {
        //hash the password
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        //validation
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(user.getEmail()));
        List<UserModel> emailExist = mongoTemplate.find(query, UserModel.class);

        String emailRegex = "^(.+)@(.+)$";      //email@gmail.com
        String dateRegex = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";  //yyyy-mm-dd
        String mobileRegex = "^\\d{10}$";      //10-digit phone number
        Pattern emailPattern = Pattern.compile(emailRegex);
        Pattern datePattern = Pattern.compile(dateRegex);
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        //if email exists
        if(!emailExist.isEmpty())
        {
            throw new Exception("Email already exists");
        }
        //if fields are null
        else if(user.getBirthDate() == null ||
                user.getEmail() == null || user.getFirstName() == null ||
                user.getLastName() == null || user.getMobile() == null ||
                user.getPassword() == null) {
            throw new Exception("Field cannot be empty");
        }
        //validation checks
        else if(!emailPattern.matcher(user.getEmail()).matches() || user.getPassword().length() < 8
                || !datePattern.matcher(user.getBirthDate()).matches() ||
                !mobilePattern.matcher(user.getMobile()).matches()){
            throw new Exception("The input information is in incorrect format.");
        }
        user.setPassword(encodedPassword);
        UserModel insertedUser = repository.insert(user);
        return insertedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel foundUser = repository.findByEmail(email);
        String emailN = foundUser.getEmail();
        String password = foundUser.getPassword();
        return new User(emailN, password, new ArrayList<>());
    }
}
