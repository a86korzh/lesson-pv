package com.korzh86a.lessons.pv.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "student")
public class Student implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @Column (name = "FIRST_NAME")
    private String firstName;

    @Column (name = "SECOND_NAME")
    private String secondName;

    @Column (name = "BIRTH_DATE")
    private String birthDate;

    @Column (name = "ENTER_YEAR")
    private String enterYear;

    @OneToMany(mappedBy = "student", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<SubjectWithMarks> subjectsWithMarks;

    public Student() {
    }
    public Student(String firstName, String secondName, String birthDate, String enterYear) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDate = birthDate;
        this.enterYear = enterYear;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEnterYear() {
        return enterYear;
    }
    public void setEnterYear(String enterYear) {
        this.enterYear = enterYear;
    }

    public List<SubjectWithMarks> getSubjectsWithMarks() {
        return subjectsWithMarks;
    }
    public void setSubjectsWithMarks(List<SubjectWithMarks> subjectsWithMarks) {
        this.subjectsWithMarks = subjectsWithMarks;
    }

    public void addSubjectWithMarks(SubjectWithMarks subjectWithMarks) {

        if (subjectsWithMarks == null) {
            subjectsWithMarks = new ArrayList<>();
        }

        subjectsWithMarks.add(subjectWithMarks);
    }

    @Override
    public String toString() {
        return "Student{" + id +
                " firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", enterYear='" + enterYear + '\'' +
                ", subjectsWithMarks=" + subjectsWithMarks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student that = (Student) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(secondName, that.secondName) &&
                Objects.equals(birthDate, that.birthDate) &&
                Objects.equals(enterYear, that.enterYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, birthDate, enterYear);
    }
}