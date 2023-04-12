package com.backend.ecommerce.services.interfaces;

import com.backend.ecommerce.entities.Command;


public interface CommandService {

    Command addCommand(Command command);
    void deleteCommand(Long idCommand);
    Double getTotalPrice(Command command);

}
