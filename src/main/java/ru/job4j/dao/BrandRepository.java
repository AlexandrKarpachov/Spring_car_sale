package ru.job4j.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ru.job4j.domain.Brand;

public interface BrandRepository extends CrudRepository<Brand, Long> {

	@Query("SELECT b FROM Brand b JOIN FETCH b.models WHERE b.id = (:id)")
    public Brand findByIdAndFetchModelsEagerly(@Param("id") Long id);
	
}
