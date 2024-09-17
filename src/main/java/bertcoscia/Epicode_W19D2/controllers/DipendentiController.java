package bertcoscia.Epicode_W19D2.controllers;

import bertcoscia.Epicode_W19D2.entities.Dipendente;
import bertcoscia.Epicode_W19D2.exceptions.BadRequestException;
import bertcoscia.Epicode_W19D2.payloads.NewDipendenteDTO;
import bertcoscia.Epicode_W19D2.payloads.NewDipendenteRespDTO;
import bertcoscia.Epicode_W19D2.services.DipendentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {
    @Autowired
    private DipendentiService service;

    // GET http://localhost:3001/dipendenti
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Dipendente> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "cognome") String sortBy) {
        return this.service.findAll(page, size, sortBy);
    }

    @GetMapping("/me")
    public Dipendente getProfile(@AuthenticationPrincipal Dipendente currentAuthenticatedDipendente) {
        return this.service.findById(currentAuthenticatedDipendente.getIdDipendente());
    }

    @PutMapping("/me")
    public Dipendente updateProfile(@AuthenticationPrincipal Dipendente currentAuthenticatedDipendente, @RequestBody Dipendente body) {
        return this.service.findByIdAndUpdate(currentAuthenticatedDipendente.getIdDipendente(), body);
    }

    // GET http://localhost:3001/dipendenti/{idDipendente}
    @GetMapping("/{idDipendente}")
    public Dipendente findById(@PathVariable UUID idDipendente) {
        return this.service.findById(idDipendente);
    }

    // POST http://localhost:3001/dipendenti + body
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewDipendenteRespDTO save(@RequestBody @Validated NewDipendenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(messages);
        } else {
            return new NewDipendenteRespDTO(this.service.save(body).getIdDipendente());
        }
    }

    // PUT http://localhost:3001/dipendenti/{idDipendente} + body
    @PutMapping("/{idDipendente}")
    public Dipendente findByIdAndUpdate(@PathVariable UUID idDipendente, @RequestBody Dipendente body) {
        return this.service.findByIdAndUpdate(idDipendente, body);
    }

    // DELETE http://localhost:3001/dipendenti/{idDipendente}
    @DeleteMapping("/{idDipendente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID idDipendente) {
        this.service.findByIdAndDelete(idDipendente);
    }

    // POST http://localhost:3001/dipendenti/{idDipendente}/avatar
    @PostMapping("/{idDipendente}/avatar")
    public void uploadImage(@RequestParam("avatar") MultipartFile image, @PathVariable UUID idDipendente) throws IOException {
        this.service.uploadImage(image, idDipendente);
    }


}
