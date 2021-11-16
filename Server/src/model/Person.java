package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Person implements Serializable {
    private int idPerson;
    private int idUser;
    private int idWorkPlace;
    private String firstName;
    private String lastName;
    private String address;

    public Person() {
        this.idPerson = 0;
        this.idUser = 0;
        this.idWorkPlace = 0;
        this.firstName = "";
        this.lastName = "";
        this.address = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person that = (Person) o;

        return Objects.equals(this.idUser, that.idUser) &&
                Objects.equals(this.idPerson, that.idPerson) &&
                Objects.equals(this.idWorkPlace, that.idWorkPlace) &&
                Objects.equals(this.firstName, that.firstName) &&
                Objects.equals(this.lastName, that.lastName) &&
                Objects.equals(this.address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idUser, this.idPerson, this.idWorkPlace,
                this.firstName, this.lastName, this.address);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + idPerson +
                ", id_user=" + idUser +
                ", id_work_place=" + idWorkPlace +
                ", first_name=" + firstName +
                ", last_name=" + lastName +
                ", address=" + address +
                '}';
    }
}
