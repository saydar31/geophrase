package ru.itis.geophrase.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.itis.geophrase.generators.RandomStringIdGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Message {
    @Id
    @GeneratedValue(generator = RandomStringIdGenerator.generatorName)
    @GenericGenerator(name = RandomStringIdGenerator.generatorName, strategy = "ru.itis.geophrase.generators.RandomStringIdGenerator")
    private String id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Message parentMessage;
    @OneToMany(mappedBy = "parentMessage", cascade = CascadeType.REMOVE)
    private List<Message> childMessages;
    private Instant createdAt;
    private Instant lastUpdatedAt;
    private String content;
    private Double lat;
    private Double lon;
}
