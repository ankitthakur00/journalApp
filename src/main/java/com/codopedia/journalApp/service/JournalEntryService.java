package com.codopedia.journalApp.service;

import com.codopedia.journalApp.entity.JournalEntry;
import com.codopedia.journalApp.entity.User;
import com.codopedia.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository ;

    @Autowired
    private UserService userService;

    public Boolean createJournalForUser(JournalEntry journalEntry, String userName){
        try{
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry createdJournal = journalEntryRepository.save(journalEntry);
            user.getJournalEntryList().add(createdJournal);
            userService.createUser(user);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }


    public List<JournalEntry> getAllJournalOfUser(String userName){
        try{
            User user = userService.findByUserName(userName);
            return user.getJournalEntryList();
        }
        catch (Exception ex){
            return null;
        }
    }

    public JournalEntry getJournalByID(ObjectId id){
        return journalEntryRepository.findById(id).orElse(null);
    }

    public boolean deleteByID(String userName, ObjectId id){
        User user = userService.findByUserName(userName);
        JournalEntry journal = this.getJournalByID(id);
        if(journal!=null &&  user!=null){
            try{
                user.getJournalEntryList().removeIf(x-> x.getId().equals(id));
                userService.createUser(user);
                journalEntryRepository.deleteById(id);
                return true;
            }
            catch (Exception ex){
                return false;
            }
        }
        return false;
    }

    public JournalEntry updateById(String userName, ObjectId id, JournalEntry journalEntry) {
        JournalEntry oldEntry = this.getJournalByID(id);
        if(oldEntry!=null){
            oldEntry.setTitle(journalEntry.getTitle()!=null && !journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(journalEntry.getContent()!=null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : oldEntry.getContent());
            journalEntryRepository.save(oldEntry);
            return oldEntry;
        }
        return null;
    }
}
