package wallet.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wallet.dao.WalletRepository;
import wallet.dto.WalletInfoDto;
import wallet.exception.NotEnoughFunds;
import wallet.exception.NotFoundException;
import wallet.mapper.WalletMapper;
import wallet.model.State;
import wallet.model.Wallet;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    public WalletInfoDto create(WalletInfoDto walletInfoDto) {
        Wallet wallet = WalletMapper.toWallet(walletInfoDto);
        return WalletMapper.toWalletInfoDto(walletRepository.save(wallet));
    }

    @Transactional
    public WalletInfoDto operate(UUID walletId, State operationType, Double amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new NotFoundException("Wallet not found"));
        switch (operationType) {
            case DEPOSIT -> wallet.setBalance(wallet.getBalance() + amount);
            case WITHDRAW -> {
                if (wallet.getBalance() < amount) {
                    throw new NotEnoughFunds("Not enough funds");
                } else {
                    wallet.setBalance(wallet.getBalance() - amount);
                }
            }
            default -> {
                throw new NotFoundException("Unknown state");
            }
        }
        return WalletMapper.toWalletInfoDto(walletRepository.save(wallet));
    }

    @Transactional
    public Double getBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new NotFoundException("Wallet not found"));
        return WalletMapper.toWalletInfoDto(wallet).getBalance();
    }
}
