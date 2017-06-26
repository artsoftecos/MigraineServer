package co.artsoft.architecture.migraine.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import co.artsoft.architecture.migraine.model.entity.Episode;
import co.artsoft.architecture.migraine.model.entity.User;

public interface EpisodeRepository extends CrudRepository<Episode, Integer> {
	List<Episode> findByUser(User user);
	List<Episode> findByUserAndDiagnosticsNotNullOrderByDateDesc(User user);
}
