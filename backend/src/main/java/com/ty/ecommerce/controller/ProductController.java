package com.ty.ecommerce.controller;

import com.ty.ecommerce.entity.Product;
import com.ty.ecommerce.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private final ProductRepository productRepository;
    private final com.ty.ecommerce.service.ExternalProductService externalProductService;

    public ProductController(ProductRepository productRepository,
            com.ty.ecommerce.service.ExternalProductService externalProductService) {
        this.productRepository = productRepository;
        this.externalProductService = externalProductService;
    }

    @PostMapping("/import")
    public List<Product> importProducts() {
        return externalProductService.importProducts();
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return productRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) {
        productRepository.deleteById(id);
        return "Product deleted successfully";
    }
}
