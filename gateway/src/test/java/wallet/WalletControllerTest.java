package wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenCreateWalletWithInvalidData() throws Exception {
        String invalidJson = "{ \"balance\": -100.0 }";

        mockMvc.perform(post("/api/v1/wallet/create")
                        .content(invalidJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testOperationValidation() throws Exception {
        String requestWithoutWalletId = "{\"id\":null,\"state\":\"DEPOSIT\",\"amount\":100.0}";
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestWithoutWalletId))
                .andExpect(status().isBadRequest());

        String requestWithoutState = "{\"walletId\":\"12345\",\"state\":null,\"amount\":100.0}";
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestWithoutState))
                .andExpect(status().isBadRequest());

        String requestWithNegativeAmount = "{\"walletId\":\"12345\",\"state\":\"DEPOSIT\",\"amount\":-100.0}";
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestWithNegativeAmount))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetValidation() throws Exception {
        mockMvc.perform(get("/api/v1/wallet/null")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}