package com.codopedia.journalApp.controller;


import com.codopedia.journalApp.entity.JournalEntry;
import com.codopedia.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAllJournal();
    }

    @PostMapping
    public JournalEntry addJournal(@RequestBody JournalEntry journal){
        journalEntryService.createJournal(journal);
        return journal;
    }


    @GetMapping("/{id}")
    public JournalEntry getJournalByID(@PathVariable ObjectId id){
        return journalEntryService.getJournalByID(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable ObjectId id){
        journalEntryService.deleteByID(id);
        return true;
    }

    @PutMapping("/{id}")
    public JournalEntry updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry journalEntry){
        return journalEntryService.updateById(id, journalEntry);
    }
}
