package org.carlspring.strongbox.janusgraph.repositories;

import java.util.List;

import org.carlspring.strongbox.janusgraph.domain.ArtifactEntity;
import org.carlspring.strongbox.janusgraph.domain.RepositoryArtifactIdGroup;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryArtifactIdGroupRepository extends CrudRepository<RepositoryArtifactIdGroup, String>
{

    @Query("MATCH (ag:ArtifactGroup{groupId:$path})<-[]-(raig:RepositoryArtifactIdGroup{storageId:$storageId,repositoryId:$repositoryId}) " +
           "WITH ag " +
           "MATCH (ag)-[]->(a:Artifact)-[aeac]->(ac:ArtifactCoordinates) " +
           "RETURN a,aeac,ac")
     List<ArtifactEntity> findArtifactsByGroupId(String storageId, String repositoryId, String path);
    
}