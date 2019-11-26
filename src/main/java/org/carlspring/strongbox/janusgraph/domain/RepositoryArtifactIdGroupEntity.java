package org.carlspring.strongbox.janusgraph.domain;

import java.util.Set;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(RepositoryArtifactIdGroup.LABEL)
public class RepositoryArtifactIdGroupEntity extends DomainEntity implements RepositoryArtifactIdGroup
{
    private String storageId;
    private String repositoryId;
    @Relationship(type = "RepositoryArtifactIdGroupEntity_ArtifactGroupEntity", direction = Relationship.OUTGOING)
    private ArtifactGroupEntity artifactGroup;

    @Override
    public String getStorageId()
    {
        return storageId;
    }

    @Override
    public void setStorageId(String storageId)
    {
        this.storageId = storageId;
    }

    @Override
    public String getRepositoryId()
    {
        return repositoryId;
    }

    @Override
    public void setRepositoryId(String repositoryId)
    {
        this.repositoryId = repositoryId;
    }

    @Override
    public Set<? extends Artifact> getArtifacts()
    {
        return artifactGroup.getArtifacts();
    }

    @Override
    public void setArtifacts(Set<? extends Artifact> artifactEntries)
    {
        artifactGroup.setArtifacts(artifactEntries);
    }

}
