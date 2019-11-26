package org.carlspring.strongbox.janusgraph.domain;

import java.util.Set;

public interface ArtifactGroup extends DomainObject
{
    public static final String LABEL = "ArtifactGroup";
    
    Set<? extends Artifact> getArtifacts();

    void setArtifacts(Set<? extends Artifact> artifactEntries);

}