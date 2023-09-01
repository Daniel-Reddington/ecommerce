package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.entities.Command;
import com.backend.ecommerce.entities.CommandItem;
import com.backend.ecommerce.entities.Product;
import com.backend.ecommerce.exceptions.ProductNotFoundException;
import com.backend.ecommerce.repositories.CommandRepository;
import com.backend.ecommerce.services.interfaces.CommandItemService;
import com.backend.ecommerce.services.interfaces.CommandService;
import com.backend.ecommerce.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {

    private final CommandRepository commandRepository;
    private final CommandItemService commandItemService;
    private final ProductService productService;

    @Override
    public Command addCommand(Command command) {
        //Find all product id in commandItems to find there in the database
        List<Long> productIds = command.getCommandItems().stream()
                .map(commandItem -> commandItem.getProduct().getId())
                .collect(Collectors.toList());

        List<Product> products = productService.findAllProductByIds(productIds);

        if(command.getCommandItems().isEmpty() || products.size() != productIds.size()){
            throw new RuntimeException("Command is empty");
        }
        //Set products to command item
        command.getCommandItems().forEach(item -> {
            Product product = products.stream()
                    .filter(p -> p.getId().equals(item.getProduct().getId()))
                    .findFirst()
                    .orElseThrow(() -> new ProductNotFoundException("Product not found for CommandItem"));

            item.setProduct(product);
            commandItemService.updateCommandItemPrice(item);
        });

        if (command.getCommandItems().stream().anyMatch(item -> !productService.isProductQuantityAvailable(item))) {
            throw new RuntimeException("Product is not available");
        }

        command.setCommandDate(LocalDateTime.now());
        Command savedCommand = commandRepository.save(command);
        savedCommand.setCommandItems(commandItemService.saveAllCommandItem(savedCommand));
        savedCommand.setTotalPrice(this.getTotalPrice(savedCommand));

        return savedCommand;
    }

    @Override
    public void deleteCommand(Long idCommand) {
        commandRepository.deleteById(idCommand);
    }

    @Override
    public Double getTotalPrice(Command command) {
        return command.getCommandItems().stream()
                .mapToDouble(CommandItem::getPrice).sum();
    }

}
