package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
public class History implements Serializable {

    private int idHistory;
    private int idUser;
    private int idSalary;

    public History() {
        this.idHistory = 0;
        this.idUser = 0;
        this.idSalary = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        History that = (History) o;

        return Objects.equals(this.idHistory, that.idHistory) &&
                Objects.equals(this.idUser, that.idUser) &&
                Objects.equals(this.idSalary, that.idSalary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idHistory, this.idUser, this.idSalary);
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + idHistory +
                ", id_user=" + idUser +
                ", id_salary=" + idSalary +
                '}';
    }
}
