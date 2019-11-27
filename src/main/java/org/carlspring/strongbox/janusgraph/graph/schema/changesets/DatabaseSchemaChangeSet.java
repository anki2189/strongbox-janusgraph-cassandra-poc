package org.carlspring.strongbox.janusgraph.graph.schema.changesets;

import org.carlspring.strongbox.janusgraph.domain.DatabaseSchema;
import org.janusgraph.core.Multiplicity;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Przemyslaw Fusik
 */
@Component
public class DatabaseSchemaChangeSet
        implements ChangeSet
{

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSchemaChangeSet.class);

    @Override
    public int getOrder()
    {
        return 0;
    }

    @Override
    public Logger getLogger()
    {
        return logger;
    }

    public void applySchemaChanges(JanusGraphManagement jgm)
    {
        // Properties
        makePropertyKeyIfDoesNotExist(jgm, "order", Integer.class);
        makePropertyKeyIfDoesNotExist(jgm, "name", String.class);

        // Vertices
        makeVertexLabelIfDoesNotExist(jgm, DatabaseSchema.class.getSimpleName());
        makeVertexLabelIfDoesNotExist(jgm, org.carlspring.strongbox.janusgraph.domain.ChangeSet.class.getSimpleName());

        // Edges
        makeEdgeLabelIfDoesNotExist(jgm, DatabaseSchema.class.getSimpleName() + "_" +
                                         org.carlspring.strongbox.janusgraph.domain.ChangeSet.class.getSimpleName(),
                                    Multiplicity.ONE2MANY);
    }
}
