package com.vko.labworkproducer.repository;

import com.vko.labworkproducer.dto.AnswerResponseDto;
import com.vko.labworkproducer.dto.DownloadFileResponseDto;
import com.vko.labworkproducer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Query("SELECT NEW com.vko.labworkproducer.dto.DownloadFileResponseDto(a.data, a.filename) FROM Answer a WHERE a.id = :id")
    DownloadFileResponseDto downloadFile(@Param("id") Integer id);

    @Query("SELECT NEW com.vko.labworkproducer.dto.AnswerResponseDto(a.id, a.description, TO_CHAR(a.dateTime, 'YYYY-MM-DD HH24:MI:SS'), " +
            "lw.name, u.username, lwt.variant, CASE WHEN a.data IS NOT NULL THEN true ELSE false END) " +
            "FROM Answer a " +
            "JOIN LabWorkTask lwt ON a.labWorkTask.id = lwt.id " +
            "JOIN LabWork lw ON lwt.labWork.id = lw.id " +
            "JOIN User u ON a.user.id = u.id")
    List<AnswerResponseDto> getAnswerList();

    @Query("SELECT NEW com.vko.labworkproducer.dto.AnswerResponseDto(a.id, a.description, TO_CHAR(a.dateTime, 'YYYY-MM-DD HH24:MI:SS'), " +
            "lw.name, u.username, lwt.variant, CASE WHEN a.data IS NOT NULL THEN true ELSE false END) " +
            "FROM Answer a " +
            "JOIN LabWorkTask lwt ON a.labWorkTask.id = lwt.id " +
            "JOIN LabWork lw ON lwt.labWork.id = lw.id " +
            "JOIN User u ON a.user.id = u.id " +
            "WHERE lw.id = :labWorkId")
    List<AnswerResponseDto> getAnswerListByLabWorkId(@Param("labWorkId") Integer labWorkId);

    @Query("SELECT NEW com.vko.labworkproducer.dto.AnswerResponseDto(a.id, a.description, TO_CHAR(a.dateTime, 'YYYY-MM-DD HH24:MI:SS'), " +
            "lwt.variant, CASE WHEN a.data IS NOT NULL THEN true ELSE false END) " +
            "FROM Answer a " +
            "JOIN LabWorkTask lwt ON a.labWorkTask.id = lwt.id " +
            "JOIN LabWork lw ON lwt.labWork.id = lw.id " +
            "JOIN User u ON a.user.id = u.id " +
            "WHERE lw.id = :labWorkId AND u.username = :username")
    List<AnswerResponseDto> getAnswerListByLabWorkIdAndUsername(@Param("labWorkId") Integer labWorkId,
                                                                @Param("username") String username);
}
