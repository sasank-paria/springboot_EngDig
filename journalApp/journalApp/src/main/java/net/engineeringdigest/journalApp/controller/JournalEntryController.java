package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping("{username}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String username){

        User user = userService.findByUsername(username);
        List<JournalEntry> journalEntries = user.getJournalEntries();

        if(journalEntries != null && !journalEntries.isEmpty()){
            return new ResponseEntity<>(journalEntries,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String username){
        try{
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myid){
        Optional<JournalEntry> journalEntry = Optional.ofNullable(journalEntryService.findEntryById(myid));
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{myid}")
    public ResponseEntity<?> deleteJournalEntries(@PathVariable ObjectId myid , @PathVariable String username){
        try{
            journalEntryService.deleteEntryById(myid, username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };


    @PutMapping("/id/{username}/{myid}")
    public ResponseEntity<JournalEntry> updateJournalById(
            @PathVariable ObjectId myid,
            @RequestBody JournalEntry newEntry,
            @PathVariable String username
    ){
        JournalEntry journalEntryInDb = journalEntryService.findEntryById(myid);
        if(journalEntryInDb != null){
            journalEntryInDb.setTitle(newEntry.getTitle() != null ? newEntry.getTitle() : journalEntryInDb.getTitle());
            journalEntryInDb.setContent(newEntry.getContent() != null ? newEntry.getContent() : journalEntryInDb.getContent());
            journalEntryInDb.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntryInDb, username);
            return new ResponseEntity<>(journalEntryInDb, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
