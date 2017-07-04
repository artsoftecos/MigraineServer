package co.artsoft.architecture.migraine.model.dao;

import org.springframework.data.repository.CrudRepository;

import co.artsoft.architecture.migraine.model.entity.Doctor;
import co.artsoft.architecture.migraine.model.entity.User;

public interface DoctorRepository  extends CrudRepository<Doctor, String> {
	Doctor findByUser(User user);
}
