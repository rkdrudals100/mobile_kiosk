package com.graduate.mobilekiosk.web.item;

import com.graduate.mobilekiosk.domain.Category;
import com.graduate.mobilekiosk.domain.Item;
import com.graduate.mobilekiosk.domain.Member;
import com.graduate.mobilekiosk.domain.OptionGroup;
import com.graduate.mobilekiosk.web.item.form.*;
import com.graduate.mobilekiosk.web.member.MemberRepository;
import com.graduate.mobilekiosk.web.option.OptionGroupService;
import com.graduate.mobilekiosk.web.option.OptionService;
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
    private final OptionGroupService optionGroupService;
    private final OptionService optionService;
    private final FileStore fileStore;

    @GetMapping("")
    public String menu(Model model, Principal principal, @RequestParam(required = true, defaultValue = "") String current) {
        List<Category> categories = categoryRepository.findByUserName(principal.getName());
        model.addAttribute("current", current);
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
            Category save = categoryRepository.save(category);
            if (save.getId() != null) {
                return "redirect:/menus?add_category&current=" + save.getId();
            }
        }catch (Exception e) {
            return "redirect:/menus?overlap";
        }

        return "redirect:/menus?add_category&current=" + categoryDto.getCategoryName();
    }

    @DeleteMapping("/category/{categoryID}")
    public String categoryDelete(@PathVariable Long categoryID) {
        categoryRepository.deleteById(categoryID);
        return "redirect:/menus?delete_category";
    }

    @GetMapping("/menu")
    public String menuForm(Model model, @RequestParam String category, @RequestParam String categoryId) {
        model.addAttribute("category", category);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("item", new MenuSaveDto());
        return "seller/menu-form";
    }


    @PostMapping("/menu")
    public String menuAdd(@Validated @ModelAttribute MenuSaveDto menuSaveDto, BindingResult bindingResult, @RequestParam String categoryId) throws IOException {
        if (bindingResult.hasErrors()) {
            return "redirect:/menus/menu?fail&category=" + menuSaveDto.getCategoryName()+"&categoryId="+categoryId;
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
        return "redirect:/menus?current=" + categoryId;
    }

    @GetMapping("/{menuId}")
    public String editMenuForm(@PathVariable Long menuId, Model model, Principal principal, @RequestParam(defaultValue = "") String current) {
        Item item = itemRepository.findWithOptionById(menuId);

        if (!item.getCategory().getUserName().equals(principal.getName())) {
            return "redirect:/menus?authorize";
        }
        model.addAttribute("item", item);
        model.addAttribute("current", current);
        return "seller/menu-edit";
    }


    @PostMapping("/{menuId}")
    public String editMenu(@PathVariable Long menuId, @Validated @ModelAttribute MenuEditDto menuEditDto, BindingResult bindingResult, Model model
    , @RequestParam String categoryId) throws IOException {
        if (bindingResult.hasErrors()) {
            return "redirect:/menus/"+menuId+"?menu";
        }

        if (menuId != menuEditDto.getId() ) {
            return "redirect:/menus?menu&current=" + categoryId;
        }

        itemService.save(menuEditDto);
        return "redirect:/menus?change&current=" + categoryId;
    }

    @DeleteMapping("/{menuId}")
    public String deleteMenu(@PathVariable Long menuId, @RequestParam String categoryId) {
        itemRepository.deleteById(menuId);
        return "redirect:/menus?delete&current=" + categoryId;
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

    @PostMapping("/options")
    public String optionsAdd(@Validated @ModelAttribute OptionGroupDto optionGroupDto, BindingResult bindingResult) {
        if (optionGroupDto.isEssential() == true && optionGroupDto.isMultiple() == true) {
            return "redirect:/menus/" + optionGroupDto.getItemId() + "?fail_options";
        }
        if (bindingResult.hasErrors()) {
            return "redirect:/menus/" + optionGroupDto.getItemId() + "?fail_options";
        }

        OptionGroup save = optionGroupService.save(optionGroupDto);

        return "redirect:/menus/" + optionGroupDto.getItemId() + "?success_options&current=" + save.getId();
    }

    @PostMapping("/options/option")
    public String optionAdd(@Validated @ModelAttribute OptionDto optionDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/menus/" + optionDto.getItemId() + "?fail_option&current=" + optionDto.getOptionGroup();
        }

        optionService.save(optionDto);

        return "redirect:/menus/" + optionDto.getItemId() + "?success_option&current=" + optionDto.getOptionGroup();
    }

    @DeleteMapping("/options/option/{optionId}")
    public String optionDelete(@PathVariable Long optionId, @RequestParam String itemId, @RequestParam String currentOptionGroupId) {
        optionService.delete(optionId);

        return "redirect:/menus/" + itemId + "?delete_option&current=" + currentOptionGroupId;
    }

    @DeleteMapping("/options/{optionGroupId}")
    public String optionsDelete(@PathVariable Long optionGroupId, @RequestParam String itemId) {
        optionGroupService.delete(optionGroupId);

        return "redirect:/menus/" + itemId + "?delete_options";
    }
}

