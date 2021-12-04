package com.graduate.mobilekiosk.web.option;

import com.graduate.mobilekiosk.domain.Item;
import com.graduate.mobilekiosk.domain.OptionGroup;
import com.graduate.mobilekiosk.web.item.ItemRepository;
import com.graduate.mobilekiosk.web.item.form.OptionGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OptionGroupService {

    private final ItemRepository itemRepository;
    private final OptionGroupRepository optionGroupRepository;

    public OptionGroup save(OptionGroupDto optionGroupDto) {
        Item item = itemRepository.findById(optionGroupDto.getItemId()).get();

        OptionGroup optionGroup = OptionGroup.builder()
                .item(item)
                .name(optionGroupDto.getOptionGroupName())
                .essential(optionGroupDto.isEssential())
                .multiple(optionGroupDto.isMultiple())
                .build();

        return optionGroupRepository.save(optionGroup);

    }

    public void delete(Long optionGroupId) {
        optionGroupRepository.deleteById(optionGroupId);
    }
}
