package wallet;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class WalletDto {
    UUID id;
    @PositiveOrZero
    Double balance;
}
