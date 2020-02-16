package com.coderdojornd.mooc.courses.controller;

import com.coderdojornd.mooc.courses.domain.Comment;
import com.coderdojornd.mooc.courses.api.CommentRequest;
import com.coderdojornd.mooc.courses.domain.Course;
import com.coderdojornd.mooc.courses.repository.CourseRepository;
import com.coderdojornd.mooc.courses.api.CourseRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository repository;

    public CourseController(final CourseRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Course> listAllCourses() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody final CourseRequest request) {
        var course = repository.save(
                new Course(
                        UUID.randomUUID().toString(),
                        "",
                        request.getName(),
                        request.getDescription(),
                        request.getTags(),
                        request.getLink(),
                        List.of()));
        return ResponseEntity.created(URI.create("/api/courses/" + course.getId())).body(course);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable("id") final String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") final String id,
                                               @RequestBody final CourseRequest request) {
        return repository.findById(id)
                .map(course -> {
                    course.setName(request.getName());
                    course.setDescription(request.getDescription());
                    course.setTags(request.getTags());
                    course.setLink(request.getLink());
                    return course;
                })
                .map(repository::saveAndFlush)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable("id") final String id) {
        repository.deleteById(id);
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<List<Comment>> listComments(@PathVariable("id") final String id) {
        return repository.findById(id)
                .map(Course::getComments)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable("id") final String id,
                                              @RequestBody final CommentRequest request) {
        return repository.findById(id)
                .map(course -> {
                    var comment =  new Comment(
                            "",
                            request.getText()
                    );
                    course.getComments().add(comment);
                    repository.saveAndFlush(course);
                    return comment;
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
