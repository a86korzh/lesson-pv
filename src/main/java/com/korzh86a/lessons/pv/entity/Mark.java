package com.korzh86a.lessons.pv.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "mark")
public class Mark implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @Column (name = "mark")
    private int mark;

    @ManyToOne(fetch=FetchType.EAGER,
            cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="subject_with_marks_id")
    private SubjectWithMarks subjectWithMarks;

    public Mark() {
    }
    public Mark(int mark) {
        this.mark = mark;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }
    public void setMark(int mark) {
        this.mark = mark;
    }

    public SubjectWithMarks getSubjectWithMarks() {
        return subjectWithMarks;
    }
    public void setSubjectWithMarks(SubjectWithMarks subjectWithMarks) {
        this.subjectWithMarks = subjectWithMarks;
    }

    @Override
    public String toString() {
        return "mark= " + mark;
    }
}
