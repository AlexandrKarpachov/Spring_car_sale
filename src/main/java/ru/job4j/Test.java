package ru.job4j;

import ru.job4j.dao.BrandStorage;
import ru.job4j.domain.Brand;

public class Test {
	public static void main(String[] args) {
		BrandStorage store = new BrandStorage(); 
		
		Brand br = store.getById(1L);
		System.out.println(br.getModels());
		
	}
}
