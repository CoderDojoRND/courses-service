package com.coderdojornd.mooc.courses.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    private String id;
    private String owner;
    private String name;
    private String description;
    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "course"))
    private List<String> tags;
    private String link;
    @ElementCollection
    @CollectionTable(name = "comments", joinColumns = @JoinColumn(name = "course"))
    private List<Comment> comments;
}
