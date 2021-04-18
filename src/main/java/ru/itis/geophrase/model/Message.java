package ru.itis.geophrase.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.itis.geophrase.generators.RandomStringIdGenerator;

import javax.persistence.*;

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
    @OneToOne
    private Message parentMessage;
    @OneToOne
    private Message childMessage;
    private String content;
    private Double lat;
    private Double lon;
}