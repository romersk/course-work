package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Salary implements Serializable {

    private int idSalary;
    private double size;
    private String method;

    public Salary() {
        this.idSalary = 0;
        this.size = 0;
        this.method = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Salary that = (Salary) o;

        return Objects.equals(this.idSalary, that.idSalary) &&
                Objects.equals(this.size, that.size) &&
                Objects.equals(this.method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idSalary, this.size, this.method);
    }

    @Override
    public String toString() {
        return "Salary{" +
                "id=" + idSalary +
                ", size=" + size +
                ", method=" + method +
                '}';
    }
}
