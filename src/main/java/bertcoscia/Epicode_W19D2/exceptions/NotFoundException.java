package bertcoscia.Epicode_W19D2.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Non è stato possibile trovare la risorsa con id " + id);
    }

    public NotFoundException(String email) {
        super("Non è stato possibile trovare la risorsa con email " + email);
    }
}
