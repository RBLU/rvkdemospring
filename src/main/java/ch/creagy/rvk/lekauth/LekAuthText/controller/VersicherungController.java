package ch.creagy.rvk.lekauth.LekAuthText.controller;

import java.util.List;
import java.util.UUID;

import ch.creagy.rvk.lekauth.LekAuthText.model.Versicherung;
import ch.creagy.rvk.lekauth.LekAuthText.model.VersicherungRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class VersicherungController {

    private final VersicherungRepository repository;

    VersicherungController(VersicherungRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/Versicherung")
    List<Versicherung> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/Versicherung")
    Versicherung newVersicherung(@RequestBody Versicherung newVersicherung) {
        return repository.save(newVersicherung);
    }

    // Single item

    @GetMapping("/Versicherung/{id}")
    Versicherung one(@PathVariable UUID id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(id.toString()));
    }

    @PutMapping("/Versicherung/{id}")
    Versicherung replaceVersicherung(@RequestBody Versicherung newVersicherung, @PathVariable UUID id) {

        return repository.findById(id)
                .map(Versicherung -> {
                    Versicherung.setName(newVersicherung.getName());
                    return repository.save(Versicherung);
                })
                .orElseGet(() -> {
                    newVersicherung.setId(id);
                    return repository.save(newVersicherung);
                });
    }

    @DeleteMapping("/Versicherung/{id}")
    void deleteVersicherung(@PathVariable UUID id) {
        repository.deleteById(id);
    }
}