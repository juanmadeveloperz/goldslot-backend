package com.goldslot.goldslot_backend.repository;

import com.goldslot.goldslot_backend.entity.DailyNotes;
import com.goldslot.goldslot_backend.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DailyNotesRepository extends JpaRepository<DailyNotes, Long> {
    List<DailyNotes> findByLesson(Lesson lesson);
    List<DailyNotes> findByLessonId(Long lessonId);
}