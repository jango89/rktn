package com.rakuten.controller.admin;

import com.rakuten.exception.DuplicateNameException;
import com.rakuten.model.core.Category;
import com.rakuten.service.CoreService;
import com.rakuten.service.core.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@Controller
@RequestMapping("/admin")
@Api("CRUD operations for category entity")
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CoreService coreService;

    @ApiOperation(value = "Get category page is displayed via this API", produces = MediaType.TEXT_HTML_VALUE)
    @RequestMapping(value = "/category/get/{categoryId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getCategoryPage(@PathVariable("categoryId") int categoryId) {
        ModelAndView modelAndView = coreService.getModelAndView();
        final Category category = categoryService.getCategory(categoryId);
        modelAndView.addObject("category", category == null ? new Category() : category);
        modelAndView.setViewName("admin/addcategory");
        return modelAndView;
    }

    @ApiOperation(value = "Save category is done via this API", produces = MediaType.TEXT_HTML_VALUE, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    @RequestMapping(value = "/category/add", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView addCategory(@Valid Category category, BindingResult bindingResult) {

        ModelAndView modelAndView = coreService.getModelAndView();

        try {
            modelAndView.addObject("content", categoryService.saveCategory(category));
        } catch (IllegalArgumentException illExp) {
            bindingResult.rejectValue("name", "error.category", illExp.getMessage());
        } catch (DuplicateNameException exp) {
            bindingResult.rejectValue("name", "error.category", exp.getMessage());
        } catch (Exception exp) {
            LOGGER.error("Exception occurred ", exp);
        }

        if (!bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Category has been registered successfully");
        }

        modelAndView.setViewName("admin/addcategory");
        return modelAndView;
    }

    @ApiOperation(value = "Delete category is done via this API", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "/category/delete/{categoryId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteCategory(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }
}
