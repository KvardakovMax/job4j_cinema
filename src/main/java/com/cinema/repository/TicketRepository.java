package com.cinema.repository;

import com.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

public interface TicketRepository {

    Optional<Ticket> save(Ticket ticket);

    Optional<Ticket> findById(int id);

    Collection<Ticket> findAll();

    boolean deleteById(int id);

}
