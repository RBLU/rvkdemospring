package ch.creagy.rvk.lekauth.LekAuthText.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import ch.creagy.rvk.lekauth.LekAuthText.model.Dataset;
import ch.creagy.rvk.lekauth.LekAuthText.model.DatasetRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DatasetController {

    private final DatasetRepository repository;

    DatasetController(DatasetRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/Dataset")
    List<Dataset> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/Dataset")
    Dataset newDataset(@RequestBody Dataset newDataset) {
        newDataset.setImportiertAm(new Timestamp(new Date().getTime()));
        return repository.save(newDataset);
    }

    // Single item

    @GetMapping("/Dataset/{id}")
    Dataset one(@PathVariable UUID id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(id.toString()));
    }

    @PutMapping("/Dataset/{id}")
    Dataset replaceDataset(@RequestBody Dataset newDataset, @PathVariable UUID id) {

        return repository.findById(id)
                .map(Dataset -> {
                    Dataset.setGeladenAm(new Timestamp(new Date().getTime()));
                    return repository.save(Dataset);
                })
                .orElseGet(() -> {
                    newDataset.setId(id);
                    return repository.save(newDataset);
                });
    }

    @DeleteMapping("/Dataset/{id}")
    void deleteDataset(@PathVariable UUID id) {
        repository.deleteById(id);
    }
}