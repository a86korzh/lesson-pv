package com.korzh86a.lessons_pv.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "marks")
public class Mark implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;
    @Column (name = "student_Id")
    private int studentId;
    @Column (name = "subject_Id")
    private int subjectId;
    @Column (name = "mark")
    private int mark;

    public Mark(int studentId, int subjectId, int mark) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.mark = mark;
    }

    public Mark() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return mark + "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark = (Mark) o;
        return studentId == mark.studentId &&
                subjectId == mark.subjectId &&
                this.mark == mark.mark;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, subjectId, mark);
    }
}
