package com.codopedia.journalApp.service;

import com.codopedia.journalApp.entity.JournalEntry;
import com.codopedia.journalApp.entity.User;
import com.codopedia.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository ;

    @Autowired
    private UserService userService;

    @Transactional
    public void createJournalForUser(JournalEntry journalEntry, String userName){
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry createdJournal = journalEntryRepository.save(journalEntry);
        userService.updateUserAddJournal(user, createdJournal);
    }


    public List<JournalEntry> getAllJournalOfUser(String userName){
        User user = userService.findByUserName(userName);
        return user.getJournalEntryList();
    }

    public Optional<JournalEntry> getJournalByID(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public Optional<JournalEntry> getJournal(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntryList().stream().filter(x->x.getId().equals(id)).toList();
        if(!collect.isEmpty()){
            return journalEntryRepository.findById(id);
        }
        return Optional.empty();
    }

    @Transactional
    public void deleteJournal(String userName, JournalEntry journal){
        User user = userService.findByUserName(userName);
        journalEntryRepository.deleteById(journal.getId());
        userService.updateUserDeleteJournal(user, journal);
    }

    public JournalEntry updateJournal(String userName, JournalEntry oldJournal, JournalEntry journalEntry) {
        oldJournal.setTitle(!journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : oldJournal.getTitle());
        oldJournal.setContent(journalEntry.getContent()!=null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : oldJournal.getContent());
        return journalEntryRepository.save(oldJournal);
    }

}
