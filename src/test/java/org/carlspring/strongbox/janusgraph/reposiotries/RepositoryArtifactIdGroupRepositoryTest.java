package org.carlspring.strongbox.janusgraph.reposiotries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.carlspring.strongbox.janusgraph.app.Application;
import org.carlspring.strongbox.janusgraph.domain.Artifact;
import org.carlspring.strongbox.janusgraph.domain.ArtifactCoordinates;
import org.carlspring.strongbox.janusgraph.domain.ArtifactEntity;
import org.carlspring.strongbox.janusgraph.domain.ArtifactGroup;
import org.carlspring.strongbox.janusgraph.domain.Edges;
import org.carlspring.strongbox.janusgraph.domain.RepositoryArtifactIdGroup;
import org.carlspring.strongbox.janusgraph.repositories.RepositoryArtifactIdGroupRepository;
import org.janusgraph.core.JanusGraph;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class RepositoryArtifactIdGroupRepositoryTest
{

    @Inject
    private JanusGraph janusGraph;

    @Inject
    private RepositoryArtifactIdGroupRepository repositoryArtifactIdGroupRepository;

    @Test
    public void findArtifactsByGroupIdShouldWork()
    {
        GraphTraversalSource g = janusGraph.traversal();

        String artifactGroupUuid = UUID.randomUUID().toString();
        g.addV(ArtifactCoordinates.LABEL)
         .property("uuid", UUID.randomUUID().toString())
         .property("path",
                   "org/carlspring/test/findArtifactsByGroupIdShouldWork.jar")
         .property("version",
                   "1.2.7")
         .as("adc1")
         .addV(Artifact.LABEL)
         .property("uuid", UUID.randomUUID().toString())
         .property("storageId", "storage0")
         .property("repositoryId", "releases")
         .as("ae1")
         .addE(Edges.ARTIFACT_ARTIFACTCOORDINATES)
         .to("adc1")
         .addV(ArtifactGroup.LABEL)
         .property("uuid", artifactGroupUuid)
         .property("groupId", "findArtifactsByGroupIdShouldWorkGroup")
         .as("ag1")
         .addE(Edges.ARTIFACTGROUP_ARTIFACT)
         .to("ae1")
         .addV(RepositoryArtifactIdGroup.LABEL)
         .property("uuid", artifactGroupUuid)
         .property("storageId", "storage0")
         .property("repositoryId", "releases")
         .addE(Edges.REPOSITORYARTIFACTIDGROUP_ARTIFACTGROUP)
         .to("ag1")
         .next();

        g.tx().commit();
        
        List<ArtifactEntity> result = repositoryArtifactIdGroupRepository.findArtifactsByGroupId("storage0", "releases",
                                                                                                 "findArtifactsByGroupIdShouldWorkGroup");
        assertEquals(1, result.size());
    }

}
