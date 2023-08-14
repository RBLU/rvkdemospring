package ch.creagy.rvk.lekauth.LekAuthText.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImportdataRepository extends JpaRepository<Importdata, UUID> {
}