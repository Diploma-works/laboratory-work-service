package com.vko.labworkproducer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private byte[] data;

    private String filename;

    private String description;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "labworktask_id")
    private LabWorkTask labWorkTask;

    @ManyToOne
    private User user;

}
