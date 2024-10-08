package com.barbara.alucar.domain.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "cars")
public class Car extends PanacheMongoEntity {

    private String placa;
    private String modelo;
    private String marca;
    private int ano;

    public Car() {
    }

    public Car(String placa, String modelo, String marca, int ano) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    //Copy constructor - construtor que gera uma cópia, setando com carBuilder
    private Car(CarBuilder carBuilder) {
        this.placa = carBuilder.placa;
        this.modelo = carBuilder.modelo;
        this.marca = carBuilder.marca;
        this.ano = carBuilder.ano;
    }
    //portal to start the builder
    public static CarBuilder builder() {
        return new CarBuilder();
    }
    //The builder
    public static class CarBuilder{
        //by default all attributes
        private String placa;
        private String modelo;
        private String marca;
        private int ano;
        //the setter methods
        public CarBuilder placa(String placa) {
            this.placa = placa;
            return this;
        }

        public CarBuilder modelo(String modelo) {
            this.modelo = modelo;
            return this;
        }

        public CarBuilder marca(String marca) {
            this.marca = marca;
            return this;
        }

        public CarBuilder ano(int ano) {
            this.ano = ano;
            return this;
        }
        //the portal to finish the builder and create the entity
        public Car build() {
            return new Car(this);
        }
    }
}
