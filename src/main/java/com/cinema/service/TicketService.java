package com.cinema.service;

import com.cinema.model.Ticket;

import java.util.Optional;

public interface TicketService {

    Optional<Ticket> save(Ticket ticket);

    Optional<Ticket> findById(int id);

    boolean deleteById(int id);

}
