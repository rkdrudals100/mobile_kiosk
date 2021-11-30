package com.graduate.mobilekiosk.web.item;

import com.graduate.mobilekiosk.domain.Category;
import com.graduate.mobilekiosk.domain.Item;
import com.graduate.mobilekiosk.domain.Member;
import com.graduate.mobilekiosk.web.item.form.MenuEditDto;
import com.graduate.mobilekiosk.web.member.MemberRepository;
import com.graduate.mobilekiosk.web.item.form.CategoryDto;
import com.graduate.mobilekiosk.web.item.form.MenuSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/menus")
@RequiredArgsConstructor
public class ItemController {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final FileStore fileStore;

    @GetMapping("")
    public String menu(Model model, Principal principal) {
        List<Category> categories = categoryRepository.findByUserName(principal.getName());
        model.addAttribute("categories", categories);
        return "seller/menu-management.html";
    }


    @PostMapping("/category")
    public String categoryAdd(@Validated @ModelAttribute CategoryDto categoryDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/menus?fail_category";
        }

        Member member = memberRepository.findByUserId(principal.getName());

        Category category = Category.builder()
                .member(member)
                .name(categoryDto.getCategoryName())
                .userName(principal.getName())
                .build();

        try {
            categoryRepository.save(category);
        }catch (Exception e) {
            return "redirect:/menus?overlap";
        }

        return "redirect:/menus?add_category";
    }

    @DeleteMapping("/category/{categoryID}")
    public String categoryDelete(@PathVariable Long categoryID) {
        categoryRepository.deleteById(categoryID);
        return "redirect:/menus?delete_category";
    }

    @GetMapping("/menu")
    public String menuForm(Model model, @RequestParam String category) {
        model.addAttribute("category", category);
        model.addAttribute("item", new MenuSaveDto());
        return "seller/menu-form";
    }


    @PostMapping("/menu")
    public String menuAdd(@Validated @ModelAttribute MenuSaveDto menuSaveDto, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "redirect:/menus/menu?fail&category=" + menuSaveDto.getCategoryName();
        }

        Category findCategory = categoryService.findByCategoryName(menuSaveDto.getCategoryName());
        String image = fileStore.storeFile(menuSaveDto.getImage());
        Item item = Item.builder()
                .category(findCategory)
                .name(menuSaveDto.getName())
                .shortDescription(menuSaveDto.getShortDescription())
                .description(menuSaveDto.getDescription())
                .price(menuSaveDto.getPrice())
                .image(image)
                .visible(menuSaveDto.isVisible())
                .build();

        itemRepository.save(item);
        return "redirect:/menus";
    }

    @GetMapping("/{menuId}")
    public String editMenuForm(@PathVariable Long menuId, Model model) {
        Item item = itemRepository.findById(menuId).get();
        model.addAttribute("item", item);
        return "seller/menu-edit";
    }


    @PostMapping("/{menuId}")
    public String editMenu(@PathVariable Long menuId, @Validated @ModelAttribute MenuEditDto menuEditDto, Model model) throws IOException {
        if (menuId != menuEditDto.getId()) {
            return "redirect:/menus?menu";
        }

        itemService.save(menuEditDto);
        return "redirect:/menus?add";
    }

    @DeleteMapping("/{menuId}")
    public String deleteMenu(@PathVariable Long menuId) {
        itemRepository.deleteById(menuId);
        return "redirect:/menus?delete";
    }

    @DeleteMapping("/{menuId}/image")
    public String editMenu(@PathVariable Long menuId) throws IOException {
        itemService.deleteImage(menuId);
        return "redirect:/menus/" + menuId;
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

}

