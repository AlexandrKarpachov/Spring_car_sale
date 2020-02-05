package ru.job4j.dao;

import java.util.List;

import ru.job4j.domain.CarFilter;

public interface CustomizedCarRepo<T> {
	List<T> findByCarFilter(CarFilter filter);
	
	Long getPagesCount(CarFilter filter);
}
