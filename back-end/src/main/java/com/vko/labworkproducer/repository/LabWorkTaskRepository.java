package com.vko.labworkproducer.repository;

import com.vko.labworkproducer.entity.LabWorkTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LabWorkTaskRepository extends JpaRepository<LabWorkTask, Integer> {

    LabWorkTask findByLabWorkIdAndVariant(Integer labWorkId, Integer variant);

    @Modifying
    @Query("DELETE FROM LabWorkTask")
    void resetVariants();

}
