package com.graduate.mobilekiosk.web.item;

import com.graduate.mobilekiosk.domain.Item;
import com.graduate.mobilekiosk.web.item.form.MenuEditDto;
import com.graduate.mobilekiosk.web.item.form.MenuSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    public void save(MenuEditDto menuEditDto) throws IOException {
        Item item = itemRepository.findById(menuEditDto.getId()).get();

        item.setName(menuEditDto.getName());
        item.setShortDescription(menuEditDto.getShortDescription());
        item.setDescription(menuEditDto.getDescription());
        item.setVisible(menuEditDto.isVisible());
        item.setPrice(menuEditDto.getPrice());

        if (!menuEditDto.getImage().isEmpty()) {
            fileStore.deleteFile(item.getImage());
            item.setImage(fileStore.storeFile(menuEditDto.getImage()));
        }
    }

    public void deleteImage(Long menuId) {
        Item item = itemRepository.findById(menuId).get();
        fileStore.deleteFile(item.getImage());
        item.setImage(null);
    }
}
