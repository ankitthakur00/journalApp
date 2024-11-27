package com.codopedia.journalApp.service;

import com.codopedia.journalApp.entity.User;
import com.codopedia.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        try{
            return userRepository.findAll();
        }
        catch (Exception ex){
            return null;
        }
    }

    public User createUser(User user) {
        try{
            return userRepository.save(user);
        }
        catch (Exception ex){
            return null;
        }
    }

    public User getUserById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }


    public Boolean deleteUserById(ObjectId id) {

        return false;
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }


    public User updateUser(User user, String userName) {
        User userInDB = this.findByUserName(userName);
        if(userInDB!=null){
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            return this.createUser(userInDB);
        }
        return null;
    }
}
