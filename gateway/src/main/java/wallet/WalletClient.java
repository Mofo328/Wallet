package wallet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletClient {

    private final RestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:8080/api/v1/wallet";

    public WalletDto createWallet(WalletDto walletDto) {
        return restTemplate.postForObject(BASE_URL + "/create", walletDto, WalletDto.class);
    }

    public WalletDto operateWallet(UUID walletId, State state, Double amount) {
        String url = BASE_URL + "?walletId=" + walletId + "&state=" + state + "&amount=" + amount;
        return restTemplate.postForObject(url, null, WalletDto.class);
    }

    public Double getBalance(UUID walletId) {
        return restTemplate.getForObject(BASE_URL + "/" + walletId, Double.class);
    }
}