package com.vko.labworkproducer.repository;

import com.vko.labworkproducer.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

    @Query("select t from Topic t where t.name in :nameList")
    List<Topic> findAllByNameList(@Param("nameList") List<String> nameList);

    Topic findByName(String name);

}
