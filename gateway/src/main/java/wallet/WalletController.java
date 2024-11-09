package wallet;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final WalletClient walletClient;


    @PostMapping("/create")
    public WalletDto createWallet(@RequestBody @Valid WalletDto request) {
        return walletClient.createWallet(request);
    }

    @PostMapping()
    public WalletDto operation(@NotNull UUID walletId, @NotNull State state, @Positive Double amount) {
        return walletClient.operateWallet(walletId, state, amount);
    }

    @GetMapping("/{WALLET_UUID}")
    public Double getBalance(@PathVariable("WALLET_UUID") @NotNull UUID walletId) {
        return walletClient.getBalance(walletId);
    }
}