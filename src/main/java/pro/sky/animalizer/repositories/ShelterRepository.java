package pro.sky.animalizer.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalizer.model.Shelter;
import pro.sky.animalizer.model.User;

/**
 * Интерфейс - репозиторий для работы с сущностью Shelter.
 */
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}
