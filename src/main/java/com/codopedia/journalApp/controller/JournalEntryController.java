package com.codopedia.journalApp.controller;


import com.codopedia.journalApp.entity.JournalEntry;
import com.codopedia.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getJournalEntryOfUser(@PathVariable String userName){
        List<JournalEntry>  journalEntries = journalEntryService.getAllJournalOfUser(userName);
        if(journalEntries!=null && !journalEntries.isEmpty()){
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> addJournal(@RequestBody JournalEntry journal, @PathVariable String userName){
        Boolean isCreated = journalEntryService.createJournalForUser(journal, userName);
        if(isCreated){
            return new ResponseEntity<>(journal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getJournalByID(@PathVariable ObjectId id){
        JournalEntry journal = journalEntryService.getJournalByID(id);
        if(journal!=null){
            return  new ResponseEntity<>(journal, HttpStatus.OK);
        }
        else{
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("id/{userName}/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String userName, @PathVariable ObjectId id){
        boolean isDeleted = journalEntryService.deleteByID(userName, id);
        if(isDeleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<?> updateJournalById(
            @PathVariable String userName,
            @PathVariable ObjectId id,
            @RequestBody JournalEntry journalEntry){
        JournalEntry journal = journalEntryService.updateById(userName, id, journalEntry);
        if(journal!=null){
            return new ResponseEntity<>(journal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
