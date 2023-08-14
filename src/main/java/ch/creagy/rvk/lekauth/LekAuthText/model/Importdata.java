package ch.creagy.rvk.lekauth.LekAuthText.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Importdata {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dataset_id", nullable = false)
    private Dataset dataset;

    @ManyToOne(optional = false)
    @JoinColumn(name = "versicherung_id", nullable = false)
    private Versicherung versicherung;

    public Versicherung getVersicherung() {
        return versicherung;
    }

    public void setVersicherung(Versicherung versicherung) {
        this.versicherung = versicherung;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}