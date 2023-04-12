package com.backend.ecommerce.services.interfaces;

import com.backend.ecommerce.entities.Command;
import com.backend.ecommerce.repositories.CommandRepository;
import lombok.RequiredArgsConstructor;


public interface CommandService {

    Command addCommand(Command command);
    void deleteCommand(Long idCommand);
    Double getTotalPrice(Command command);

}
