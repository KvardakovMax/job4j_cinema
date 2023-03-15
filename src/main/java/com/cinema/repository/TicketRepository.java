package com.cinema.repository;

import com.cinema.model.Ticket;

import java.util.Optional;

public interface TicketRepository {

    Optional<Ticket> save(Ticket ticket);

    Optional<Ticket> findById(int id);

    boolean deleteById(int id);

}
