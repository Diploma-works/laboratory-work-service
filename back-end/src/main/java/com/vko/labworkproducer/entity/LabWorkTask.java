package com.vko.labworkproducer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "labworktask")
@NoArgsConstructor
public class LabWorkTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(Integer.MIN_VALUE)
    @Max(Integer.MAX_VALUE)
    private Integer variant;

    @ManyToMany
    @JoinTable(
            name = "labworktask_task",
            joinColumns = @JoinColumn(name = "labworktask_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "labwork_id")
    private LabWork labWork;

    public LabWorkTask(Integer variant, List<Task> tasks, LabWork labWork) {
        this.variant = variant;
        this.tasks = tasks;
        this.labWork = labWork;
    }
}
