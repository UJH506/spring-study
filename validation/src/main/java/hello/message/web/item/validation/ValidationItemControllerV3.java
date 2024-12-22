package hello.message.web.item.validation;

import hello.message.domain.item.Item;
import hello.message.domain.item.ItemRepository;
import hello.message.domain.item.SaveCheck;
import hello.message.domain.item.UpdateCheck;
import hello.message.web.validation.ItemValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v3/items")
public class ValidationItemControllerV3 {

    private ItemRepository itemRepository;
    private ItemValidator itemValidator;

    @Autowired
    public ValidationItemControllerV3(ItemRepository itemRepository, ItemValidator itemValidator) {
        this.itemRepository = itemRepository;
        this.itemValidator = itemValidator;
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v3/addForm";
    }

    /* Bean Validator를 사용하기 위해 주석처리 (중복) */
    @InitBinder
    public void init(WebDataBinder dataBinder) {
        log.info("init binder {}", dataBinder);
        dataBinder.addValidators(itemValidator);
    }

    @PostMapping("/add")
    public String addItemV6(@Validated(SaveCheck.class) @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v3/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/editForm";
    }

    /*
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId, @Validated @ModelAttribute Item item, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v3/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }
    */

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId, @Validated(UpdateCheck.class) @ModelAttribute Item item, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v3/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }
}

