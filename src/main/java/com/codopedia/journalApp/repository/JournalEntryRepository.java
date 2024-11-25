package com.codopedia.journalApp.repository;

import com.codopedia.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository  extends MongoRepository<JournalEntry, ObjectId> {
}
