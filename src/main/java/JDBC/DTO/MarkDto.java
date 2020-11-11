package JDBC.DTO;

import java.util.Objects;

public class MarkDto {
    private int id;
    private int studentId;
    private int subjectId;
    private int mark;

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
        MarkDto markDto = (MarkDto) o;
        return studentId == markDto.studentId &&
                subjectId == markDto.subjectId &&
                mark == markDto.mark;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, subjectId, mark);
    }
}
