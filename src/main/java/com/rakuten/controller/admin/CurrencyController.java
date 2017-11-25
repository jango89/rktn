package com.rakuten.controller.admin;

import com.rakuten.service.core.CurrencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
@Api("Currency controller helps to do currency related operations ")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @ApiOperation(value = "Currency value get operation", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "/currency/value/{currencyId}/{toCurrency}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateCurrency(@PathVariable("currencyId") int currencyId, @PathVariable("toCurrency") String toCurrency) {
        return new ResponseEntity<>(currencyService.getValueForCurrency(currencyId, toCurrency), HttpStatus.ACCEPTED);
    }
}
