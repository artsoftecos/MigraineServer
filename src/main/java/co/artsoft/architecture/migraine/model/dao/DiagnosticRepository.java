package co.artsoft.architecture.migraine.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.artsoft.architecture.migraine.model.entity.Diagnostic;
import co.artsoft.architecture.migraine.model.entity.Doctor;

public interface DiagnosticRepository extends CrudRepository<Diagnostic, Integer> {
	List<Diagnostic> findByDoctor(Doctor doctor);
}
