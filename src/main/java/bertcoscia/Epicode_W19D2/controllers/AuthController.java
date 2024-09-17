package bertcoscia.Epicode_W19D2.controllers;

import bertcoscia.Epicode_W19D2.exceptions.BadRequestException;
import bertcoscia.Epicode_W19D2.payloads.LoginDTO;
import bertcoscia.Epicode_W19D2.payloads.LoginRespDTO;
import bertcoscia.Epicode_W19D2.payloads.NewDipendenteDTO;
import bertcoscia.Epicode_W19D2.payloads.NewDipendenteRespDTO;
import bertcoscia.Epicode_W19D2.services.AuthService;
import bertcoscia.Epicode_W19D2.services.DipendentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService service;

    @Autowired
    DipendentiService dipendentiService;

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        return new LoginRespDTO(this.service.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public NewDipendenteRespDTO save(@RequestBody @Validated NewDipendenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining());
            throw new BadRequestException(messages);
        } else {
            return new NewDipendenteRespDTO(this.dipendentiService.save(body).getIdDipendente());
        }
    }

}
