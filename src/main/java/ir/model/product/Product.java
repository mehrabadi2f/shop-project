package ir.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 3000)
    private String description;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    private Integer reservedStock;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;

    @Version
    private Long version;

    public void reserve(int quantity) {

        if ((stock - reservedStock) < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        reservedStock += quantity;
    }

    public void commitReservation(int quantity) {

        reservedStock -= quantity;
        stock -= quantity;
    }
    public void releaseReservation(int quantity) {

        reservedStock -= quantity;
    }

    public  Product(String name, String description, BigDecimal price, Integer stock, Integer reservedStock, Category category, Long version) {
        {
        this.name = name;this.price = price;this.name=name;this.description=description;
       this.stock=stock;this.reservedStock=reservedStock;this.category=category;this.version=version;}
    }

    }
