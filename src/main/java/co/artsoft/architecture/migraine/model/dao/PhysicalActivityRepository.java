package co.artsoft.architecture.migraine.model.dao;

import org.springframework.data.repository.CrudRepository;

import co.artsoft.architecture.migraine.model.entity.PhysicalActivity;

public interface PhysicalActivityRepository  extends CrudRepository<PhysicalActivity, Integer> {

}
