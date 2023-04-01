package com.backend.ecommerce.services.interfaces;

import com.backend.ecommerce.entities.Command;
import com.backend.ecommerce.entities.CommandItem;

import java.util.List;

public interface CommandItemService {
    List<CommandItem> saveAllCommandItem(Command command);
    void updateCommandItemPrice(CommandItem commandItem);

}
