package com.geniq.content.repository;

import com.geniq.content.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MentorRepository extends JpaRepository<Mentor, String> {
    List<Mentor> findByCategoryIgnoreCase(String category);
}
