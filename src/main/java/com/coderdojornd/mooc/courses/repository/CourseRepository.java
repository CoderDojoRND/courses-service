package com.coderdojornd.mooc.courses.repository;

import com.coderdojornd.mooc.courses.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
}
