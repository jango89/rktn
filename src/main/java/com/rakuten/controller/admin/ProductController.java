package com.rakuten.controller.admin;

import com.rakuten.exception.DuplicateNameException;
import com.rakuten.model.core.Product;
import com.rakuten.service.CoreService;
import com.rakuten.service.core.CurrencyService;
import com.rakuten.service.core.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
@Api("CRUD operations for product entity")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CoreService coreService;

    @Autowired
    private CurrencyService currencyService;

    @ApiOperation(value = "Add product page is displayed via this API")
    @RequestMapping(value = "/product/get/{categoryId}/{productId}", method = RequestMethod.GET)
    public ModelAndView addProductPage(@PathVariable("categoryId") int categoryId, @PathVariable("productId") int productId) {
        ModelAndView modelAndView = new ModelAndView();
        Product product = productService.getProduct(productId);
        if (null == product) {
            product = new Product();
            product.setCategoryId(categoryId);
        }
        modelAndView.addObject("product", product);
        modelAndView.setViewName("admin/addproduct");
        modelAndView.addObject("currencies", currencyService.getAllCurrencies());
        return modelAndView;
    }

    @ApiOperation(value = "Save product is done via this API")
    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    public Object addProduct(@Valid Product product, BindingResult bindingResult) {
        ModelAndView modelAndView = coreService.getModelAndView();

        try {
            final Product savedProduct = productService.saveProduct(product);
            modelAndView.addObject("product", savedProduct );
        } catch (IllegalArgumentException illExp) {
            bindingResult.rejectValue("name", "error.product", illExp.getMessage());
        } catch (DuplicateNameException exp) {
            bindingResult.rejectValue("name", "error.product", exp.getMessage());
        } catch (Exception exp) {
            LOGGER.error("Exception occurred ", exp);
        }

        if (!bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Product has been registered successfully");
        }
        modelAndView.addObject("currencies", currencyService.getAllCurrencies());

        modelAndView.setViewName("admin/addproduct");
        return modelAndView;
    }

    @ApiOperation(value = "Delete product is done via this API")
    @ResponseBody
    @RequestMapping(value = "/product/delete/{productId}", method = RequestMethod.DELETE)
    public Object deleteProduct(@PathVariable("productId") int productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "All products for a selected category is displayed via this API")
    @RequestMapping(value = "/product/all/{categoryId}", method = RequestMethod.GET)
    public ModelAndView getAllProducts(@PathVariable("categoryId") Integer categoryId) {
        ModelAndView modelAndView = coreService.getModelAndView();

        modelAndView.addObject("contents", productService.getAllProducts(categoryId));
        modelAndView.addObject("addUrl", "/admin/product/get/".concat(categoryId.toString()).concat("/").concat("0"));
        modelAndView.setViewName("admin/producthome");
        return modelAndView;
    }
}
