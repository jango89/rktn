package com.rakuten.test;

import com.google.common.collect.Lists;
import com.rakuten.model.core.Category;
import com.rakuten.repository.core.CategoryRepository;
import com.rakuten.service.core.CategoryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {

        categoryService = new CategoryService(categoryRepository);

        List<Category> categories = Lists.newLinkedList();

        categories.add(new Category(1, "Fashion"));
        categories.add(new Category(2, "Kitchen"));


        Mockito.when(categoryRepository.findAll(new Sort(Sort.Direction.ASC, "name"))).thenReturn(categories);

        Category category = new Category();
        category.setCategoryId(2);
        Mockito.when(categoryRepository.findOne(2)).thenReturn(category);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);
    }

    @Test
    public void getAllCategories() {
        final List<Category> categories = categoryService.getAllCategories();
        Assert.assertEquals(categories.size(), 2);
    }

    @Test
    public void getCategoryById() {
        Category category = categoryService.getCategory(2);
        Assert.assertNotNull(category);
    }

    @Test
    public void deleteCategoryById() {
        categoryService.deleteCategory(1);
    }

    @Test
    public void saveCategory() {
        Category category = new Category(2, "tst");
        final Category categorySaved = categoryService.saveCategory(category);

        Assert.assertNotNull(categorySaved);
    }
}
