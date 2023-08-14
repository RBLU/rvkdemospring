package ch.creagy.rvk.lekauth.LekAuthText.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VersicherungRepository extends JpaRepository<Versicherung, UUID> {

}
