package wallet;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wallet.dao.WalletRepository;
import wallet.dto.WalletInfoDto;
import wallet.exception.NotEnoughFunds;
import wallet.exception.NotFoundException;
import wallet.mapper.WalletMapper;
import wallet.model.State;
import wallet.model.Wallet;
import wallet.service.WalletService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Transactional
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WalletServiceTest {
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    WalletService walletService;

    private Wallet walletCreated;

    @BeforeEach
    void setUp() {
        walletCreated = new Wallet();
        walletCreated.setBalance(100.0);
        walletRepository.save(walletCreated);
    }

    @Test
    void create() {
        WalletInfoDto walletInfoDto = WalletInfoDto.builder()
                .balance(200.00).build();
        WalletInfoDto wallet = walletService.create(walletInfoDto);
        assertThat(wallet).isNotNull();
        assertThat(wallet.getBalance()).isEqualTo(200.00);
    }

    @Test
    void operateStateDeposit() {
        WalletInfoDto wallet = walletService.operate(walletCreated.getId(), State.DEPOSIT, 2000.0);
        assertThat(wallet).isNotNull();
        assertThat(wallet.getBalance()).isEqualTo(2100.0);
        assertThat(wallet.getId()).isEqualTo(walletCreated.getId());
    }

    @Test
    void operateStateWithdraw() {
        WalletInfoDto wallet = walletService.operate(walletCreated.getId(), State.WITHDRAW, 50.0);
        assertThat(wallet).isNotNull();
        assertThat(wallet.getBalance()).isEqualTo(50.0);
        assertThat(wallet.getId()).isEqualTo(walletCreated.getId());
    }

    @Test
    void operateStateUnknown() {
        assertThrows(NotFoundException.class, () ->
                walletService.operate(walletCreated.getId(), State.UNKNOWN, 50.0));
    }

    @Test
    void operateUuidFail() {
        assertThrows(NotFoundException.class, () ->
                walletService.operate(UUID.randomUUID(), State.DEPOSIT, 50.0));
    }

    @Test
    void operateNotEnoughFunds() {
        assertThrows(NotEnoughFunds.class, () ->
                walletService.operate(walletCreated.getId(), State.WITHDRAW, 1000.00));
    }

    @Transactional
    public Double getBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new NotFoundException("Wallet not found"));
        return WalletMapper.toWalletInfoDto(wallet).getBalance();
    }

    @Test
    void getBalance() {
        Double balance = walletService.getBalance(walletCreated.getId());
        assertThat(balance).isPositive();
        assertThat(balance).isNotNull();
        assertThat(balance).isEqualTo(100.00);
    }

    @Test
    void getBalanceUuidFail() {
        assertThrows(NotFoundException.class, () ->
                walletService.getBalance(UUID.randomUUID()));
    }
}
