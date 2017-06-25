package co.artsoft.architecture.migraine.model.dao;

import org.springframework.data.repository.CrudRepository;
import co.artsoft.architecture.migraine.model.entity.Episode;

public interface EpisodeRepository extends CrudRepository<Episode, Integer> {

}
