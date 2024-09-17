package bertcoscia.Epicode_W19D2.payloads;

import jakarta.validation.constraints.*;

public record NewDipendenteDTO(
        @NotEmpty(message = "Username obbligatorio")
                @NotBlank(message = "Lo username non può essere vuoto e/o contenere spazi")
                @Size(min = 3, max = 30, message = "Lo username deve essere compreso tra i 3 e i 30 caratteri")
        String username,
        @NotEmpty(message = "Nome obbligatorio")
        String nome,
        @NotEmpty(message = "Cognome obbligatorio")
        String cognome,
        @NotNull(message = "Email obbligatoria")
        @Email(message = "Email non valida")
        String email,
        @NotEmpty(message = "Password obbligatoria")
        String password
) {
}
