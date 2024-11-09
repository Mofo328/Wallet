package wallet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import wallet.controller.WalletController;
import wallet.dto.WalletInfoDto;
import wallet.model.State;
import wallet.service.WalletService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Test
    public void testCreateWallet() throws Exception {
        UUID walletId = UUID.randomUUID();
        WalletInfoDto walletInfoDto = WalletInfoDto.builder()
                .id(walletId)
                .balance(100.0)
                .build();
        when(walletService.create(any(WalletInfoDto.class))).thenReturn(walletInfoDto);

        mockMvc.perform(post("/api/v1/wallet/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"balance\": 100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(100.0));
    }

    @Test
    public void testOperation() throws Exception {
        UUID walletId = UUID.randomUUID();
        Double amount = 50.0;
        State state = State.DEPOSIT;

        when(walletService.operate(eq(walletId), eq(state), eq(amount))).thenReturn(WalletInfoDto.builder()
                .id(walletId)
                .balance(100.0)
                .build());

        mockMvc.perform(post("/api/v1/wallet")
                        .param("walletId", walletId.toString())
                        .param("state", state.name())
                        .param("amount", amount.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(100.0));
    }

    @Test
    public void testGetBalance() throws Exception {
        UUID walletId = UUID.randomUUID();
        Double expectedBalance = 100.0;

        when(walletService.getBalance(walletId)).thenReturn(expectedBalance);

        mockMvc.perform(get("/api/v1/wallet/{WALLET_UUID}", walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedBalance));
    }
}