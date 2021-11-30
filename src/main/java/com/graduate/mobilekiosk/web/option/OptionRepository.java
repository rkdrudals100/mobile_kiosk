package com.graduate.mobilekiosk.web.option;


import com.graduate.mobilekiosk.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OptionRepository extends JpaRepository<Option, Long> {
}
