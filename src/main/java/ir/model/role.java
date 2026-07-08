package ir.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    public role() {}

    public role(String name) {
        this.name = name;
    }

    // getters setters
    public String getName() {
        return  name;
    }
    public void setName(String name) { this.name = name; }
}