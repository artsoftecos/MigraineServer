package co.artsoft.architecture.migraine.model.dao;

import org.springframework.data.repository.CrudRepository;

import co.artsoft.architecture.migraine.model.entity.Doctor;

public interface DoctorRepository  extends CrudRepository<Doctor, String> {

}
