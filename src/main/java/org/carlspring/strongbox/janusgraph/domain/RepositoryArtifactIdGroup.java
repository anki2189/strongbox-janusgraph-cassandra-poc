package org.carlspring.strongbox.janusgraph.domain;

public interface RepositoryArtifactIdGroup extends ArtifactGroup
{
    public static final String LABEL = "RepositoryArtifactIdGroup";

    String getStorageId();

    void setStorageId(String storageId);

    String getRepositoryId();

    void setRepositoryId(String repositoryId);

}