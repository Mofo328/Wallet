package wallet.service;

import wallet.dto.WalletInfoDto;
import wallet.model.State;

import java.util.UUID;

public interface WalletService {
    WalletInfoDto create(WalletInfoDto walletInfoDto);

    WalletInfoDto operate(UUID walletId, State operationType, Double amount);

    Double getBalance(UUID walletId);
}
