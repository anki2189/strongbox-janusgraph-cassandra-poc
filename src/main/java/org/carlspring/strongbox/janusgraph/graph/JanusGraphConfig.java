package org.carlspring.strongbox.janusgraph.graph;

import java.io.IOException;

import org.apache.cassandra.service.CassandraDaemon;
import org.carlspring.strongbox.janusgraph.cassandra.CassandraEmbeddedProperties;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphFactory.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Przemyslaw Fusik
 */
@Configuration
@ComponentScan
public class JanusGraphConfig
{

    @Bean
    public Builder janusGraphFactory(CassandraDaemon cassandra,
                                     CassandraEmbeddedProperties cassandraEmbeddedProperties)
    {
        return JanusGraphFactory.build()
                                .set("storage.backend", "cql")
                                .set("storage.hostname", "127.0.0.1")
                                .set("storage.port", cassandraEmbeddedProperties.getPort())
                                .set("gremlin.graph", "org.janusgraph.core.JanusGraphFactoryGraphTraversalSource");
    }

}