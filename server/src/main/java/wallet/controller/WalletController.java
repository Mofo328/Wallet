package wallet.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import wallet.dto.WalletInfoDto;
import wallet.model.State;
import wallet.service.WalletService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final WalletService walletService;


    @PostMapping("/create")
    public WalletInfoDto createWallet(@RequestBody WalletInfoDto request) {
        log.info("Wallet created with body {}", request);
        return walletService.create(request);
    }

    @PostMapping()
    public WalletInfoDto operation(UUID walletId, State state, Double amount) {
        log.info("Wallet {} operated with state {}, and amount {}", walletId, state, amount);
        return walletService.operate(walletId, state, amount);
    }

    @GetMapping("/{WALLET_UUID}")
    public Double getBalance(@PathVariable("WALLET_UUID") UUID walletId) {
        log.info("Wallet {} get balance", walletId);
        return walletService.getBalance(walletId);
    }
}