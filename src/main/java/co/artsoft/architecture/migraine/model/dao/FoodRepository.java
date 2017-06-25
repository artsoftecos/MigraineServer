package co.artsoft.architecture.migraine.model.dao;

import org.springframework.data.repository.CrudRepository;
import co.artsoft.architecture.migraine.model.entity.Food;

public interface FoodRepository extends CrudRepository<Food, Integer> {

}
