package com.barbara.alucar.infrastructure.repository;

import com.barbara.alucar.domain.entity.Invoice;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InvoiceRepository implements PanacheMongoRepository<Invoice> {
}
