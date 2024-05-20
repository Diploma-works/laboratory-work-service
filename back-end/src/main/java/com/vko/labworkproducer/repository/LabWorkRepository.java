package com.vko.labworkproducer.repository;

import com.vko.labworkproducer.entity.LabWork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabWorkRepository extends JpaRepository<LabWork, Integer> {

    LabWork findByName(String name);

}
