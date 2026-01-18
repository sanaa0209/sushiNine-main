package com.groupnine.sushi9.repositories;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


/**
 * Base repository interface extending JpaRepository to provide CRUD operations.
 *
 * @param <T>  the type of the entity
 * @param <ID> the type of the entity's identifier
 */

@NoRepositoryBean
public interface BaseRepository <T,ID> extends JpaRepository<@NonNull T,@NonNull ID>{

}
