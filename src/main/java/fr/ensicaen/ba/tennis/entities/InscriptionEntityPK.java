package fr.ensicaen.ba.tennis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class InscriptionEntityPK implements Serializable {
    @Column(name = "numeroadherent")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numeroadherent;
    @Column(name = "codetournoi")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codetournoi;

    public int getNumeroadherent() {
        return numeroadherent;
    }

    public void setNumeroadherent(int numeroadherent) {
        this.numeroadherent = numeroadherent;
    }

    public int getCodetournoi() {
        return codetournoi;
    }

    public void setCodetournoi(int codetournoi) {
        this.codetournoi = codetournoi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InscriptionEntityPK that = (InscriptionEntityPK) o;

        if (numeroadherent != that.numeroadherent) return false;
        if (codetournoi != that.codetournoi) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = numeroadherent;
        result = 31 * result + codetournoi;
        return result;
    }
}
