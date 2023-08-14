package ch.creagy.rvk.lekauth.LekAuthText.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import javax.xml.crypto.Data;

@Entity
public class Dataset {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;

    private String role;

    @ManyToOne(optional = false)
    @JoinColumn(name = "versicherung_id", nullable = false)
    private Versicherung versicherung;

    @Column(name = "importiert_am", nullable = false)
    private Timestamp importiertAm;

    @Column(name = "geladen_am")
    private Timestamp geladenAm;

    public Timestamp getGeladenAm() {
        return geladenAm;
    }

    public void setGeladenAm(Timestamp geladenAm) {
        this.geladenAm = geladenAm;
    }

    public Timestamp getImportiertAm() {
        return importiertAm;
    }

    public void setImportiertAm(Timestamp importiertAm) {
        this.importiertAm = importiertAm;
    }

    public Versicherung getVersicherung() {
        return versicherung;
    }

    public void setVersicherung(Versicherung versicherung) {
        this.versicherung = versicherung;
    }

    Dataset() {}


    public UUID getId() {
        return this.id;
    }


    public void setId(UUID id) {
        this.id = id;
    }
}