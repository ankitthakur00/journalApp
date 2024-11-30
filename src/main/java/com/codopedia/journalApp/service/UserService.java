package com.codopedia.journalApp.service;

import com.codopedia.journalApp.entity.JournalEntry;
import com.codopedia.journalApp.entity.User;
import com.codopedia.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            return userRepository.save(user);
        }
        catch (Exception ex){
            return null;
        }
    }

    public User getUserById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }


    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }


    public User updateUser(User user, String userName) {
        User userInDB = this.findByUserName(userName);
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());
        return this.createUser(userInDB);
    }



    public void deleteUserByName(String userName) {
        userRepository.deleteByUserName(userName);
    }

    public void updateUserAddJournal(User user, JournalEntry journal) {
        user.getJournalEntryList().add(journal);
        userRepository.save(user);
    }

    public void updateUserDeleteJournal(User user, JournalEntry journal) {
        user.getJournalEntryList().removeIf(x-> x.getId().equals(journal.getId()));
        userRepository.save(user);
    }

    public User createAdminUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER", "ADMIN"));
            return userRepository.save(user);
        }
        catch (Exception ex){
            return null;
        }
    }

    public User createSuperAdminUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER", "ADMIN", "SUPER_ADMIN"));
            return userRepository.save(user);
        }
        catch (Exception ex){
            return null;
        }
    }

}
