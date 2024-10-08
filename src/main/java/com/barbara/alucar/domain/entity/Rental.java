package com.barbara.alucar.domain.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@MongoEntity(collection = "rentals")
public class Rental extends PanacheMongoEntity {

    private LocalDateTime start;
    private LocalDateTime finish;
    private ObjectId carId;
    private ObjectId invoiceId;
    private String userName;
    private String email;

    public Rental() {

    }

    public Rental(LocalDateTime start, LocalDateTime finish, ObjectId carId, ObjectId invoiceId, String userName, String email) {
        this.start = start;
        this.finish = finish;
        this.carId = carId;
        this.invoiceId = invoiceId;
        this.userName = userName;
        this.email = email;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public ObjectId getCarId() {
        return carId;
    }

    public void setCarId(ObjectId carId) {
        this.carId = carId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ObjectId getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(ObjectId invoiceId) {
        this.invoiceId = invoiceId;
    }

    //Copy constructor - construtor que gera uma cópia, setando com rentalBuilder
    private Rental(RentalBuilder rentalBuilder) {
        this.start = rentalBuilder.start;
        this.finish = rentalBuilder.finish;
        this.carId = rentalBuilder.carId;
        this.invoiceId = rentalBuilder.invoiceId;
        this.userName = rentalBuilder.userName;
        this.email = rentalBuilder.email;
    }
    //portal to start the builder
    public static RentalBuilder builder(){
        return new RentalBuilder();
    }
    //The builder
    public static class RentalBuilder{
        //by default all attributes
        private LocalDateTime start;
        private LocalDateTime finish;
        private ObjectId carId;
        private ObjectId invoiceId;
        private String userName;
        private String email;
        //the setter methods
        public RentalBuilder start(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public RentalBuilder finish(LocalDateTime finish) {
            this.finish = finish;
            return this;
        }

        public RentalBuilder carId(ObjectId carId) {
            this.carId = carId;
            return this;
        }
        public RentalBuilder invoiceId(ObjectId invoiceId) {
            this.invoiceId = invoiceId;
            return this;
        }

        public RentalBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public RentalBuilder email(String email) {
            this.email = email;
            return this;
        }
        public Rental build(){
            return new Rental(this);
        }
    }
}

