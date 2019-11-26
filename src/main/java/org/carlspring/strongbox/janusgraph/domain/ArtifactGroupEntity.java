package org.carlspring.strongbox.janusgraph.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(ArtifactGroup.LABEL)
public class ArtifactGroupEntity extends DomainEntity implements ArtifactGroup
{

    private String groupId;
    @Relationship(type = Edges.ARTIFACTGROUP_ARTIFACT, direction = Relationship.OUTGOING)
    private Set<ArtifactEntity> artifacts = new HashSet<>();

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    @Override
    public Set<? extends Artifact> getArtifacts()
    {
        return artifacts;
    }

    @Override
    public void setArtifacts(Set<? extends Artifact> artifact)
    {
        this.artifacts = new HashSet<ArtifactEntity>(
                artifacts.stream().map(ArtifactEntity.class::cast).collect(Collectors.toSet()));
    }

}
