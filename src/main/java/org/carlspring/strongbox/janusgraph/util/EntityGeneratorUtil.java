package org.carlspring.strongbox.janusgraph.util;

import org.carlspring.strongbox.janusgraph.domain.ArtifactCoordinates;
import org.carlspring.strongbox.janusgraph.domain.ArtifactDependency;
import org.carlspring.strongbox.janusgraph.domain.ArtifactEntry;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class EntityGeneratorUtil
{

    public static ArtifactEntry createRandomArtifact()
    {
        ArtifactEntry entry = new ArtifactEntry();
        entry.setStorageId(RandomStringUtils.randomAlphanumeric(24));
        entry.setRepositoryId(RandomStringUtils.randomAlphanumeric(24));
        entry.setSizeInBytes(RandomUtils.nextLong(10000L, 1000000000L));
        entry.setCreated(new Date(RandomUtils.nextLong(820454400000L, new Date().getTime())));
        entry.setTags(new HashSet<>(Arrays.asList("release", "stable")));
        entry.setArtifactCoordinates(createRandomArtifactCoordinates());
        entry.setUuid(UUID.randomUUID().toString());
        return entry;
    }

    public static ArtifactDependency createArtifactDependency(ArtifactEntry from, ArtifactEntry to)
    {
        ArtifactDependency dependency = new ArtifactDependency();
        dependency.setDependency(to.getArtifactCoordinates());
        dependency.setSubject(from);
        dependency.setUuid(UUID.randomUUID().toString());
        return dependency;
    }

    public static ArtifactCoordinates createRandomArtifactCoordinates()
    {
        ArtifactCoordinates coordinates = new ArtifactCoordinates();
        coordinates.setUuid(UUID.randomUUID().toString());
        coordinates.setPath(RandomStringUtils.randomAlphanumeric(24));
        coordinates.setVersion(RandomStringUtils.random(16, "0123456789-."));
        return coordinates;
    }

}
