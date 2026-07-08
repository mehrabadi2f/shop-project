package ir.service;

import ir.model.product.Category;
import ir.model.product.Product;
import ir.repository.CategoryRepository;
import ir.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    public Product getProductById(Long poductId) {
        return productRepository.findById(poductId)
                .orElseThrow();
    }


    // * پیاده‌سازی ایجاد حساب جدید.
    // * هر حساب با یک شناسه‌ی یکتا (UUID) ذخیره می‌شود.


    @Transactional
    public  void createProduct(String name, String description, BigDecimal price, Integer stock, Integer reservedStock, String categoryName, Long version)
        {

            Category category1=
            categoryRepository.findByName(categoryName).orElseThrow();
            Product Product = new Product(name, description, price, stock, reservedStock, category1, version);
            productRepository.save(Product);
        }
    @Transactional
    public  void upQuantityProduct(Long poductId, int quantity)
    {
        Product p= productRepository.findById(poductId).orElseThrow();
        p.setStock(p.getStock() + quantity);
        // ایجاد شناسه یکتا برای حساب جدید
      //  String generatedId = "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
      //  Product Product = new Product(name, description, price, stock, reservedStock, category, version);
        productRepository.save(p);
    }
            // ایجاد شناسه یکتا
     //   Account account = new Account(generatedId, ownerName, initialBalance == null ? BigDecimal.ZERO : initialBalance);

    //    return accountRepository.save(account);


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

}
/*
 String name; String description; BigDecimal price; Integer stock;Integer reservedStock; Category category; Long version;

 */