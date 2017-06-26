package co.artsoft.architecture.migraine.model.dao;

import org.springframework.data.repository.CrudRepository;

import co.artsoft.architecture.migraine.model.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

}

