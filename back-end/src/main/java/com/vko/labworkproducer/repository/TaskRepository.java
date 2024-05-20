package com.vko.labworkproducer.repository;

import com.vko.labworkproducer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query(value = "select * from get_random_tasks_by_topics(:ids)", nativeQuery = true)
    List<Task> findRandomTasksByTopics(@Param("ids") Integer[] ids);

    List<Task> findTasksByTopicId(Integer topicId);

}
