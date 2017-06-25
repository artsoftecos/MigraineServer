package co.artsoft.architecture.migraine.model.dao;

import org.springframework.data.repository.CrudRepository;

import co.artsoft.architecture.migraine.model.entity.Location;

public interface LocationRepository extends CrudRepository<Location, Integer> {

}
