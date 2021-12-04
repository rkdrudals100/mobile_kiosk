package com.graduate.mobilekiosk.web.option;


import com.graduate.mobilekiosk.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OptionRepository extends JpaRepository<Option, Long> {

    @Query("select o from Option o where o.id in (:options)")
    List<Option> findWithCheckOptionsById(@Param("options") List<Long> option);

}
