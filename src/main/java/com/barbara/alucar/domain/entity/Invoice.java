package com.barbara.alucar.domain.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "invoices")
public class Invoice extends PanacheMongoEntity {

    private double basicPayment;
    private double tax;

    public Invoice() {
    }

    public Invoice(double basicPayment, double tax) {
        this.basicPayment = basicPayment;
        this.tax = tax;
    }

    public double getBasicPayment() {
        return basicPayment;
    }

    public void setBasicPayment(double basicPayment) {
        this.basicPayment = basicPayment;
    }

    public double getTotalPayment() {
        return basicPayment + tax;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "basicPayment=" + basicPayment +
                ", tax=" + tax +
                ", id=" + id +
                '}';
    }

    //Copy constructor - construtor que gera uma cópia
    private Invoice(InvoiceBuilder invoiceBuilder) {
        this.basicPayment = invoiceBuilder.basicPayment;
        this.tax = invoiceBuilder.tax;
    }
    //portal to start the builder
    public static InvoiceBuilder builder() {
        return new InvoiceBuilder();
    }
    //The builder
    public static class InvoiceBuilder{
        //by default all attributes
        private double basicPayment;
        private double tax;
        //the setter methods
        public InvoiceBuilder basicPayment(double basicPayment) {
            this.basicPayment = basicPayment;
            return this;
        }

        public InvoiceBuilder tax(double tax) {
            this.tax = tax;
            return this;
        }
        //the portal to finish the builder and create the entity
        public Invoice build() {
            return new Invoice(this);
        }
    }
}
