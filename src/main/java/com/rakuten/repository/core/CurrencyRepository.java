package com.rakuten.repository.core;

import com.rakuten.model.core.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

}
