package ir.repository;


import ir.model.IdempotentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotentRequestRepository extends JpaRepository<IdempotentRequest, String> {
}
