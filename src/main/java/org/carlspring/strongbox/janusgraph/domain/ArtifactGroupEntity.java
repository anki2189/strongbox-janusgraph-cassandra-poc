package org.carlspring.strongbox.janusgraph.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(ArtifactGroup.LABEL)
public class ArtifactGroupEntity extends DomainEntity implements ArtifactGroup
{

    @Relationship(type = Edges.ARTIFACTGROUP_ARTIFACT, direction = Relationship.OUTGOING)
    private Set<ArtifactEntity> artifacts = new HashSet<>();

    @Override
    public Set<? extends Artifact> getArtifacts()
    {
        return artifacts;
    }

    @Override
    public void setArtifacts(Set<? extends Artifact> artifactEntries)
    {
        this.artifacts = (Set<ArtifactEntity>) artifactEntries;
    }

}
