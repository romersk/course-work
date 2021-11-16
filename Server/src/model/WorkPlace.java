package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class WorkPlace implements Serializable {

    private int idWorkPlace;
    private String name;
    private String address;

    public WorkPlace() {
        this.idWorkPlace = 0;
        this.name = "";
        this.address = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkPlace that = (WorkPlace) o;

        return Objects.equals(this.idWorkPlace, that.idWorkPlace) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idWorkPlace, this.name, this.address);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + idWorkPlace +
                ", name=" + name +
                ", address=" + address +
                '}';
    }
}
