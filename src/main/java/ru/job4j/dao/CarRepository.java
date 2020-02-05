package ru.job4j.dao;

import org.springframework.data.repository.CrudRepository;

import ru.job4j.domain.Car;


public interface CarRepository extends CrudRepository<Car, Long>, CustomizedCarRepo<Car>{

}
