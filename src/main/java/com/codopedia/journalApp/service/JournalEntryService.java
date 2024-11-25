package com.codopedia.journalApp.service;

import com.codopedia.journalApp.entity.JournalEntry;
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

    public void createJournal(JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllJournal(){
        return journalEntryRepository.findAll();
    }

    public JournalEntry getJournalByID(ObjectId id){
        return journalEntryRepository.findById(id).orElse(null);
    }

    public void deleteByID(ObjectId id){
        journalEntryRepository.deleteById(id);
    }

    public JournalEntry updateById(ObjectId id, JournalEntry journalEntry) {
        JournalEntry oldEntry = this.getJournalByID(id);
        if(oldEntry!=null){
            oldEntry.setTitle(journalEntry.getTitle()!=null && !journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(journalEntry.getContent()!=null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : oldEntry.getContent());
            journalEntryRepository.save(oldEntry);
        }
        return oldEntry;
    }
}
