package ir.repository;

import ir.model.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>
{

    Optional<Category> findByName(String categoryName);
}


//public interface ProductRepository extends JpaRepository<Product, Long> {

//    List<Product> findByCategoryName(String categoryName);
//}



