package com.graduate.mobilekiosk.web.option;

import com.graduate.mobilekiosk.domain.Option;
import com.graduate.mobilekiosk.domain.OptionGroup;
import com.graduate.mobilekiosk.web.item.form.OptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;
    private final OptionGroupRepository optionGroupRepository;

    public void save(OptionDto optionDto) {
        OptionGroup optionGroup = optionGroupRepository.findById(optionDto.getOptionGroup()).get();

        Option option = Option.builder()
                .optionGroup(optionGroup)
                .name(optionDto.getOptionName())
                .price(optionDto.getOptionPrice())
                .build();

        optionRepository.save(option);
    }

    public void delete(Long optionId) {
        optionRepository.deleteById(optionId);
    }
}
