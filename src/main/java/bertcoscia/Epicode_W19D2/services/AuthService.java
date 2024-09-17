package bertcoscia.Epicode_W19D2.services;

import bertcoscia.Epicode_W19D2.entities.Dipendente;
import bertcoscia.Epicode_W19D2.exceptions.UnauthorizedException;
import bertcoscia.Epicode_W19D2.payloads.LoginDTO;
import bertcoscia.Epicode_W19D2.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendentiService dipendentiService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        Dipendente found = this.dipendentiService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            return this.jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Email e/o password sbagliata");
        }
    }
}
