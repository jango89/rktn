package com.rakuten.controller.admin;

import com.rakuten.service.CoreService;
import com.rakuten.service.core.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Api(value = "Admin API", description = "API gateway for Admin related operations")
public class AdminController {

    @Autowired
    private CoreService coreService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Admin home page is displayed via this API", produces = MediaType.TEXT_HTML_VALUE)
    @RequestMapping(value = "/admin/home", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView home() {
        ModelAndView modelAndView = coreService.getModelAndView();

        modelAndView.addObject("contents", categoryService.getAllCategories());

        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

}
