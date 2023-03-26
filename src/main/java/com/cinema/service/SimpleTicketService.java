package com.cinema.service;

import com.cinema.model.Ticket;
import com.cinema.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {

    private final TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository sql2oTicketRepository) {
        this.ticketRepository = sql2oTicketRepository;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> findById(int id) {
        return ticketRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        return ticketRepository.deleteById(id);
    }
}
