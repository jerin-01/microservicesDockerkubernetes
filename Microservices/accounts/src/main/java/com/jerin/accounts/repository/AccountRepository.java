package com.jerin.accounts.repository;

import com.jerin.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Accounts,Long> {
    Optional<Accounts> findBycustomerId(Long customerId);
}
