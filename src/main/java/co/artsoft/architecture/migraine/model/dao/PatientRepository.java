package co.artsoft.architecture.migraine.model.dao;

import org.springframework.data.repository.CrudRepository;

import co.artsoft.architecture.migraine.model.entity.Patient;

public interface PatientRepository  extends CrudRepository<Patient, String> {

}
