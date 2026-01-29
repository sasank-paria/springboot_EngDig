package net.engineeringdigest.journalApp.entity;


import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

//POJO class-> plain old java object
@Document(collection="journalEntries") //specify the collection name in MongoDB
@Getter
@Setter //Because of Lombok annotations, no need to write getters and setters
public class JournalEntry {
    @Id  //primary key, unique key
    private ObjectId id;

    private String title;

    private String content;

    private LocalDateTime date;

}
