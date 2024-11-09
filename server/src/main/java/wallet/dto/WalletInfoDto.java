package wallet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class WalletInfoDto {
    @JsonProperty("id")
    private UUID id;
    private Double balance;
}
