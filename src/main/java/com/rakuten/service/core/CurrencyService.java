package com.rakuten.service.core;

import com.rakuten.model.core.Currency;
import com.rakuten.repository.core.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyService.class);

    private final String API_URL = "http://api.fixer.io/latest?base={BASE}&symbols={TOVALUE}";

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public Currency getValueForCurrency(int currencyId, String toSymbol) {

        final Currency currency = currencyRepository.findOne(currencyId);

        final String urlToInvoke = API_URL.replace("{BASE}", currency.getSymbol())
                .replace("{TOVALUE}", toSymbol);

        try {

            RestTemplate restTemplate = new RestTemplate();
            final Map forObject = restTemplate.getForObject(urlToInvoke, Map.class);

            Currency finalCurrency = new Currency();

            finalCurrency.setCurrentValue(BigDecimal.valueOf(Double.valueOf(((Map)forObject.get("rates")).get("USD").toString())));

            return finalCurrency;
        } catch (RestClientException re) {
            LOGGER.warn("Exception occurred while computing the money value", re);
            throw new RuntimeException("Exception occurred while computing the money value");
        }
    }
}
