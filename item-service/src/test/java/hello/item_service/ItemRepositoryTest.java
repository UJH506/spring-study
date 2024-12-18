package hello.item_service;

import hello.item_service.domain.item.Item;
import hello.item_service.domain.item.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void clear() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        Item item = new Item("춘식이", 35000, 56);
        Item saveItem = itemRepository.save(item);
        Item findItem = itemRepository.findById(item.getId());
        assertThat(saveItem).isEqualTo(findItem);
    }

    @Test
    void findAll() {
        Item item1 = new Item("치이카와", 20000, 51);
        Item item2 = new Item("하치와레", 30000, 32);
        Item item3 = new Item("우사기", 15000, 54);
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        List<Item> findAll = itemRepository.findAll();
        assertThat(findAll.size()).isEqualTo(3);
        assertThat(findAll).contains(item1, item2, item3);
    }

    @Test
    void updateItem() {
        Item item = new Item("재도의 빵치즈", 4000, 23);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        Item updateItem = new Item("재도가다먹엇다", 10, 2);
        itemRepository.update(itemId, updateItem);

        Item findItem = itemRepository.findById(itemId);

        assertThat(findItem.getItemName()).isEqualTo("재도가다먹엇다");
        assertThat(findItem.getPrice()).isEqualTo(10);
        assertThat(findItem.getQuantity()).isEqualTo(2);
    }
}
