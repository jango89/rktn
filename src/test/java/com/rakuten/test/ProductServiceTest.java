package com.rakuten.test;

import com.google.common.collect.Lists;
import com.rakuten.model.core.Product;
import com.rakuten.repository.core.ProductRepository;
import com.rakuten.service.core.ProductService;
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
public class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void setUp() {

        productService = new ProductService(productRepository);

        List<Product> productsWithCategoryId2 = Lists.newLinkedList();

        productsWithCategoryId2.add(new Product("Jeans", 1));
        productsWithCategoryId2.add(new Product("Top", 1));

        List<Product> allProducts = Lists.newLinkedList();

        allProducts.addAll(productsWithCategoryId2);
        allProducts.add(new Product("Mixer", 2));

        Mockito.when(productRepository.findByCategoryId(1)).thenReturn(productsWithCategoryId2);
        Mockito.when(productRepository.findAll(new Sort(Sort.Direction.ASC, "categoryId"))).thenReturn(allProducts);

        Product product = new Product();
        product.setProductId(2);
        Mockito.when(productRepository.findOne(2)).thenReturn(product);
    }

    @Test
    public void getAllProductsByCategory() {
        final List<Product> allProducts = productService.getAllProducts(1);
        Assert.assertEquals(allProducts.size(), 2);
    }

    @Test
    public void getAllProducts() {
        final List<Product> allProducts = productService.getAllProducts();
        Assert.assertEquals(allProducts.size(), 3);
    }

    @Test
    public void getProductById() {
        Product product = productService.getProduct(2);
        Assert.assertNotNull(product);
    }

    @Test
    public void deleteProductById() {
        productService.deleteProduct(1);
    }

    @Test
    public void saveProduct() {
        Product product = new Product("tst", 1);
        product.setProductId(2);
        final Product saveProduct = productService.saveProduct(product);

        Assert.assertNotNull(saveProduct);
        Assert.assertSame(saveProduct.getCurrencyId(), 1);
    }
}
