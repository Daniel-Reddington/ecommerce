package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.entities.Command;
import com.backend.ecommerce.entities.CommandItem;
import com.backend.ecommerce.entities.Product;
import com.backend.ecommerce.repositories.CommandItemRepository;
import com.backend.ecommerce.repositories.ProductRepository;
import com.backend.ecommerce.services.interfaces.CommandItemService;
import com.backend.ecommerce.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandItemServiceImpl implements CommandItemService {

    private final CommandItemRepository commandItemRepository;
    private final ProductService productService;

    @Override
    public List<CommandItem> saveAllCommandItem(Command command) {

        command.getCommandItems().forEach(commandItem -> {
            commandItem.setCommand(command);
            productService.decrementStockQuantity(commandItem.getProduct().getId(),commandItem.getQuantity());
        });

        return commandItemRepository.saveAll(command.getCommandItems());
    }

}
