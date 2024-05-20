package com.vko.labworkproducer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "labwork")
@NoArgsConstructor
public class LabWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 128)
    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    @Column(unique = true)
    private String description;


    @ManyToMany
    @JoinTable(
            name = "labwork_topic",
            joinColumns = @JoinColumn(name = "labwork_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private List<Topic> topics;

    public LabWork(String name, String description, List<Topic> topics) {
        this.name = name;
        this.description = description;
        this.topics = topics;
    }
}
