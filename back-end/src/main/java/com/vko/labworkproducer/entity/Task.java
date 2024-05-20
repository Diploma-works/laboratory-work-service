package com.vko.labworkproducer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "description", unique = true)
    private String description;

    @ManyToOne
    private Topic topic;

    public Task(String description, Topic topic) {
        this.description = description;
        this.topic = topic;
    }

}
