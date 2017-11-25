package com.rakuten.test;

import com.rakuten.model.core.Currency;
import com.rakuten.repository.core.CurrencyRepository;
import com.rakuten.service.core.CurrencyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Test
    public void testCurrencyConverted() {

        Currency currency = new Currency();
        currency.setCurrencyId(1);
        currency.setSymbol("EUR");
        Mockito.when(currencyRepository.findOne(1)).thenReturn(currency);

        CurrencyService currencyService = new CurrencyService(currencyRepository);
        final Currency valueForCurrency = currencyService.getValueForCurrency(1, "USD");

        Assert.assertNotNull(valueForCurrency);
        Assert.assertTrue(valueForCurrency.getCurrentValue().doubleValue() > new BigDecimal(0.2).doubleValue());
    }
}
