package org.carlspring.strongbox.janusgraph.reposiotries;

import java.util.Optional;

import org.carlspring.strongbox.janusgraph.domain.DatabaseSchema;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Przemyslaw Fusik
 */
public interface DatabaseSchemaRepository
        extends CrudRepository<DatabaseSchema, String>
{

    @Query("MATCH (databaseSchema:DatabaseSchema {uuid:$uuid}) RETURN databaseSchema")
    @Override
    Optional<DatabaseSchema> findById(String uuid);

    @Query("MATCH (databaseSchema:DatabaseSchema) RETURN databaseSchema")
    @Override
    Iterable<DatabaseSchema> findAll();

    @Query("MATCH (databaseSchema:DatabaseSchema {uuid:$uuid}) DETACH DELETE databaseSchema")
    @Override
    void deleteById(String uuid);
}
