package com.barbara.alucar.infrastructure.repository;

import com.barbara.alucar.domain.entity.Rental;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class RentalRepository implements PanacheMongoRepository<Rental> {

    @Inject
    private MongoClient mongoClient;

    @PostConstruct
    public void createIndex(){
        mongoClient.getDatabase("car_rental_db")
                .getCollection("rentals")
                .createIndex(Indexes.ascending("email"), new IndexOptions().unique(true));
    }
}
