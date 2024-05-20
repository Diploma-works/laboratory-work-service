package com.vko.labworkproducer.service;

import com.vko.labworkproducer.entity.LabWork;
import com.vko.labworkproducer.entity.LabWorkTask;
import com.vko.labworkproducer.entity.Task;
import com.vko.labworkproducer.repository.LabWorkTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LabWorkTaskService {

    private final LabWorkTaskRepository labWorkTaskRepository;
    private final TaskService taskService;
    private final LabWorkService labWorkService;

    public LabWorkTask getLabWorkTask(Integer labWorkId, Integer variant) {
        LabWork labWork = labWorkService.getLabWorkById(labWorkId);
        LabWorkTask labWorkTask = labWorkTaskRepository.findByLabWorkIdAndVariant(labWork.getId(), variant);
        if (labWorkTask == null) labWorkTask = generateLabWorkTask(labWork, variant);
        return labWorkTask;
    }

    private LabWorkTask generateLabWorkTask(LabWork labWork, Integer variant) {
        List<Task> tasks = taskService.getRandomTaskListByTopicList(labWork.getTopics());
        LabWorkTask newLabWorkTask = new LabWorkTask(variant, tasks, labWork);
        return labWorkTaskRepository.save(newLabWorkTask);
    }

    public void resetVariants() {
        labWorkTaskRepository.resetVariants();
    }

    public LabWorkTask findLabWorkTaskById(Integer id) {
        Optional<LabWorkTask> labWorkTask = labWorkTaskRepository.findById(id);
        return labWorkTask.get();
    }

}
