package org.carlspring.strongbox.janusgraph.rest.controller;

import org.carlspring.strongbox.janusgraph.domain.ArtifactDependency;
import org.carlspring.strongbox.janusgraph.domain.ArtifactEntry;
import org.carlspring.strongbox.janusgraph.reposiotries.ArtifactCoordinatesRepository;
import org.carlspring.strongbox.janusgraph.reposiotries.ArtifactEntryRepository;
import org.carlspring.strongbox.janusgraph.rest.request.EntityPopulationRequest;
import org.carlspring.strongbox.janusgraph.util.EntityGenerator;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entities")
public class EntityPopulationController
{

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityPopulationController.class);

    @Inject
    private ArtifactCoordinatesRepository artifactCoordinatesRepository;
    @Inject
    private ArtifactEntryRepository artifactEntryRepository;
    @Inject
    private SessionFactory sessionFactory;

    @PostMapping
    @Transactional
    public ResponseEntity createEntities(@RequestBody EntityPopulationRequest requestBody)
    {
        int artifactsToCreate = requestBody.getArtifacts();
        int dependenciesToCreate = requestBody.getDependencies();

        if (artifactsToCreate < 0 || dependenciesToCreate < 0)
        {
            return ResponseEntity.unprocessableEntity().build();
        }

        List<ArtifactEntry> artifactEntries = new ArrayList<>(artifactsToCreate);
        List<ArtifactDependency> artifactDependencies = new ArrayList<>(dependenciesToCreate);

        for (int i = 0; i < artifactsToCreate; ++i)
        {
            artifactEntries.add(EntityGenerator.createRandomArtifact());
        }

        for (int j = 0; j < dependenciesToCreate; ++j)
        {
            int toArtifact, fromArtifact = RandomUtils.nextInt(0, artifactsToCreate);
            do
            {
                toArtifact = RandomUtils.nextInt(0, artifactsToCreate);
            } while (fromArtifact != toArtifact);
            ArtifactDependency dependency = EntityGenerator.createArtifactDependency(
                    artifactEntries.get(fromArtifact), artifactEntries.get(toArtifact));
            artifactDependencies.add(dependency);
        }

        for (int k = 0; k < artifactEntries.size(); ++k)
        {
            LOGGER.info("Saving entry {}", k);
            artifactEntryRepository.save(artifactEntries.get(k));
        }

        Session session = sessionFactory.openSession();
        for (ArtifactDependency dependency : artifactDependencies)
        {
            session.save(dependency);
        }

        return ResponseEntity.ok().build();
    }
}
