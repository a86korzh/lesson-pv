package com.korzh86a.lessons.pv.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "subject_with_marks")
public class SubjectWithMarks implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @ManyToOne(fetch=FetchType.LAZY,
            cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="subject_id")
    private Subject subject;

    @ManyToOne(fetch=FetchType.LAZY,
            cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="student_id")
    private Student student;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "subjectWithMarks", cascade = CascadeType.ALL)
    private List<Mark> marks;

    public SubjectWithMarks() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Mark> getMarks() {
        return marks;
    }
    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public void addMarks(Mark mark) {

        if (marks == null) {
            marks = new ArrayList<>();
        }

        marks.add(mark);
    }

    @Override
    public String toString() {
        return subject + ": "
                + marks;
    }
}
