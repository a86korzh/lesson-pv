package com.korzh86a.lessons.pv.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "subject")
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @Column (name = "subj_Name")
    private String subjectName;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<SubjectWithMarks> subjectsWithMarks;

    public Subject() {
    }
    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<SubjectWithMarks> getSubjectsWithMarks() {
        return subjectsWithMarks;
    }
    public void setSubjectsWithMarks(List<SubjectWithMarks> subjectsWithMarks) {
        this.subjectsWithMarks = subjectsWithMarks;
    }

    public void addSubjectsWithMarks(SubjectWithMarks subjectWithMarks) {

        if (subjectsWithMarks == null) {
            subjectsWithMarks = new ArrayList<>();
        }

        subjectsWithMarks.add(subjectWithMarks);
    }

    @Override
    public String toString() {
        return subjectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject that = (Subject) o;
        return subjectName.equals(that.subjectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectName);
    }
}

