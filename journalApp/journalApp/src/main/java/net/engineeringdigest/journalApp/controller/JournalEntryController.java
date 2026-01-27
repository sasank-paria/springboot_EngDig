package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAllEntries();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("/id/{myid}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myid){
        return journalEntryService.findEntryById(myid);
    }

    @DeleteMapping("/id/{myid}")
    public boolean deleteJournalEntries(@PathVariable ObjectId myid){
        journalEntryService.deleteEntryById(myid);
        return true;
    };


    @PutMapping("/id/{myid}")
    public JournalEntry updateJournalById(@PathVariable ObjectId myid, @RequestBody JournalEntry myEntry){
        JournalEntry existingEntry = journalEntryService.findEntryById(myid);
        if(existingEntry != null){
            existingEntry.setTitle(myEntry.getTitle());
            existingEntry.setContent(myEntry.getContent());
            journalEntryService.saveEntry(existingEntry);
        }
        return existingEntry;
    }

}
