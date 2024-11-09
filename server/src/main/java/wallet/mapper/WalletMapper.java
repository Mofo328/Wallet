package wallet.mapper;

import wallet.dto.WalletInfoDto;
import wallet.model.Wallet;

public class WalletMapper {

    public static Wallet toWallet(WalletInfoDto walletInfoDto) {
        return Wallet.builder()
                .id(walletInfoDto.getId())
                .balance(walletInfoDto.getBalance())
                .build();
    }

    public static WalletInfoDto toWalletInfoDto(Wallet wallet) {
        return WalletInfoDto.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .build();
    }
}
