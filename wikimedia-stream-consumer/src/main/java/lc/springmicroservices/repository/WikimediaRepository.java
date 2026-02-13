package lc.springmicroservices.repository;

import lc.springmicroservices.repository.entity.WikimediaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikimediaRepository extends JpaRepository<WikimediaData, Long> {
}
