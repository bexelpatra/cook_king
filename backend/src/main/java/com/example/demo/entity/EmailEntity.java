package com.example.demo.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "email", schema = "mydb")
@NoArgsConstructor
public class EmailEntity {
    private int id;
    private String email;
    private String number;
    private Date regDate;
    private boolean check;

    @Builder
    public EmailEntity(int id, String email, String number, Date regDate,boolean check) {
        this.id = id;
        this.email = email;
        this.number = number;
        this.regDate = regDate;
        this.check = check;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "numbers")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "reg_date")
    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Basic
    @Column(name = "checks")
    public boolean isCheck() { return check; }
    public void setCheck(boolean check) { this.check = check; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailEntity that = (EmailEntity) o;
        return id == that.id &&
                Objects.equals(email, that.email) &&
                Objects.equals(number, that.number) &&
                Objects.equals(regDate, that.regDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, number, regDate);
    }

    @Override
    public String toString() {
        return "EmailEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", regDate=" + regDate +
                ", check=" + check +
                '}';
    }
}
