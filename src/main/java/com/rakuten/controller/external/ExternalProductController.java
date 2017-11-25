package com.rakuten.controller.external;

import com.rakuten.service.core.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Api("Get all products page by default")
public class ExternalProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "All products are displayed for everyone")
    @RequestMapping(value = {"/", "/external/product/all"}, method = RequestMethod.GET)
    public ModelAndView getAllProducts() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("contents", productService.getAllProducts());
        modelAndView.setViewName("external/producthome");
        return modelAndView;
    }
}
