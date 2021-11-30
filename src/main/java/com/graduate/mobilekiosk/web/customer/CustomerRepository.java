package com.graduate.mobilekiosk.web.customer;

import com.graduate.mobilekiosk.domain.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
