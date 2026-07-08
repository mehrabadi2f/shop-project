package ir.repository;

import ir.model.role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<role, Long> {
    role findByName(String name);
}
