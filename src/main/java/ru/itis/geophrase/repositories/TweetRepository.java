package ru.itis.geophrase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.geophrase.model.Message;

import java.util.List;

public interface TweetRepository extends JpaRepository<Message, String> {
    String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(t.lat)) *" +
            " cos(radians(t.lon) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(t.lat))))";

    @Query("select t from Message t where " + HAVERSINE_FORMULA + " < :distance ")
    List<Message> findAllInRadius(@Param("latitude") Double lat, @Param("longitude") Double lon, @Param("distance") Double distance);
}
