package co.artsoft.architecture.migraine.model.dao;

import org.springframework.data.repository.CrudRepository;

import co.artsoft.architecture.migraine.model.entity.Medicine;

public interface MedicineRepository extends CrudRepository<Medicine, Integer> {

}
