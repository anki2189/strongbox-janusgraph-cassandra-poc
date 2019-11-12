package org.carlspring.strongbox.janusgraph.reposiotries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import javax.inject.Inject;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.carlspring.strongbox.janusgraph.app.Application;
import org.carlspring.strongbox.janusgraph.domain.ArtifactCoordinates;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphTransaction;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.types.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class ArtifactCoordinatesReporitoryTest
{

    private static final Logger logger = LoggerFactory.getLogger(ArtifactCoordinatesReporitoryTest.class);

    @Inject
    private JanusGraph janusGraph;

    @Inject
    private Driver driver;
    
    @Inject
    private ArtifactCoordinatesRepository artifactCoordinatesRepository; 
    
    @Test
    public void cypherQueriesShouldWork()
    {
        Vertex artifactCoordinatesVertex = createArtifactCoordinatesVertex();
        
        try (Session session = driver.session())
        {

            StatementResult result = session.run("MATCH (ac:ArtifactCoordinates { path:\"org/carlspring/test-artifact.jar\" }) RETURN ac");
            Node node = result.single().get(0).asNode();

            assertEquals(artifactCoordinatesVertex.properties("path").next().value(), node.get("path").asString());
        }
    }

    protected Vertex createArtifactCoordinatesVertex()
    {
        Vertex artifactCoordinatesVertex;
        try (JanusGraphTransaction tx = janusGraph.newTransaction())
        {
            
            GraphTraversalSource g = tx.traversal();
            
            artifactCoordinatesVertex = g.addV(ArtifactCoordinates.class.getSimpleName())
                                         .property("uuid", UUID.randomUUID().toString())
                                         .property("path", "org/carlspring/test-artifact.jar")
                                         .property("version", "1.2.3")
                                         .next();
            tx.commit();
        }
        
        return artifactCoordinatesVertex;
    }

    @Test
    public void crudShouldWork() {
//        ArtifactCoordinates artifactCoordinates = new ArtifactCoordinates();
//        artifactCoordinates.setPath("org/carlspring/test-artifact.jar");
//        artifactCoordinates.setUuid(UUID.randomUUID().toString());
//        artifactCoordinates.setVersion("1.0.0");
//        
//        ArtifactCoordinates artifactCoordinatesSaved;
//        artifactCoordinatesSaved = artifactCoordinatesRepository.save(artifactCoordinates);
//        assertEquals(artifactCoordinates.getUuid(), artifactCoordinatesSaved.getUuid());
        
        createArtifactCoordinatesVertex();
        
        ArtifactCoordinates result = artifactCoordinatesRepository.findByPath("org/carlspring/test-artifact.jar");
        assertEquals("org/carlspring/test-artifact.jar", result.getPath());
    }
    
}