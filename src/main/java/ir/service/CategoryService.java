
package ir.service;

import ir.model.product.Category;
import ir.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRep) {
        this.categoryRepository = categoryRep;
    }


    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }


    // * پیاده‌سازی ایجاد حساب جدید.
    // * هر حساب با یک شناسه‌ی یکتا (UUID) ذخیره می‌شود.


    @Transactional
    public  void createCategory(String name)
    {// ایجاد شناسه یکتا برای حساب جدید

       Category category = new Category();category.setName(name);
       categoryRepository.save(category);

    }
    public List<Category> getAllCategorys() {
        return categoryRepository.findAll();
    }
    /*
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
*/
}
/*
 String name; String description; BigDecimal price; Integer stock;Integer reservedStock; Category category; Long version;

 */
