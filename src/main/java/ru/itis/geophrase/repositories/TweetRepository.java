package ru.itis.geophrase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.geophrase.model.Tweet;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, String> {
    String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(t.lat)) *" +
            " cos(radians(t.lon) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(t.lat))))";

    @Query("select t from Tweet t where " + HAVERSINE_FORMULA + " < :distance ")
    List<Tweet> findAllInRadius(@Param("latitude") Double lat,@Param("longitude") Double lon,@Param("distance") Double distance);
}
