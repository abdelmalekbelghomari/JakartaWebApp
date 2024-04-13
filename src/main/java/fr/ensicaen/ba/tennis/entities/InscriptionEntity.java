package fr.ensicaen.ba.tennis.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "inscription", schema = "public", catalog = "tennis")
@IdClass(InscriptionEntityPK.class)
public class InscriptionEntity {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "numeroadherent")
    private int numeroadherent;
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "codetournoi")
    private int codetournoi;
    @Basic
    @Column(name = "dateinscription")
    private Date dateinscription;

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

    public Date getDateinscription() {
        return dateinscription;
    }

    public void setDateinscription(Date dateinscription) {
        this.dateinscription = dateinscription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InscriptionEntity that = (InscriptionEntity) o;

        if (numeroadherent != that.numeroadherent) return false;
        if (codetournoi != that.codetournoi) return false;
        if (dateinscription != null ? !dateinscription.equals(that.dateinscription) : that.dateinscription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = numeroadherent;
        result = 31 * result + codetournoi;
        result = 31 * result + (dateinscription != null ? dateinscription.hashCode() : 0);
        return result;
    }
}
