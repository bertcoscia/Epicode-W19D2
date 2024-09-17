package bertcoscia.Epicode_W19D2.payloads;

import java.time.LocalDateTime;

public record ErrorsRespDTO(String message, LocalDateTime timestamp) {
}
