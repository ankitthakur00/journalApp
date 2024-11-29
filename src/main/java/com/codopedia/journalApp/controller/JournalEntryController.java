package com.codopedia.journalApp.controller;


import com.codopedia.journalApp.entity.JournalEntry;
import com.codopedia.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getJournalEntryOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<JournalEntry>  journalEntries = journalEntryService.getAllJournalOfUser(authentication.getName());
        return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> addJournal(@RequestBody JournalEntry journal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        journalEntryService.createJournalForUser(journal, authentication.getName());
        return new ResponseEntity<>(journal, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<JournalEntry> getJournalByID(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<JournalEntry> journalEntry = journalEntryService.getJournal(id, authentication.getName());
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<JournalEntry> journal = journalEntryService.getJournal(id, authentication.getName());
        if(journal.isPresent()){
            journalEntryService.deleteJournal(authentication.getName(), journal.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJournalById(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry updateJournal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<JournalEntry> oldJournal = journalEntryService.getJournal(id, authentication.getName());
        if(oldJournal.isPresent()){
            JournalEntry updatedJournal = journalEntryService.updateJournal(authentication.getName(), oldJournal.get(), updateJournal);
            return new ResponseEntity<>(updatedJournal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
