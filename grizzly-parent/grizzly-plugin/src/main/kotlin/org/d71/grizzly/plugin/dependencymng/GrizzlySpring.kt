package org.d71.grizzly.plugin.dependencymng

import org.d71.grizzly.plugin.model.GrizzlyFeature

class GrizzlySpring {
    val GRAPHQL_SPRING_BOOT_VERSION = "5.7.3"
    val GRAPHQL_JAVA_TOOL_VERSION = "5.5.2"

    fun dependencyList(features: Set<GrizzlyFeature>) = listOf(
            // core
            listOf(
                    "org.springframework.boot:spring-boot-starter-logging",
                    "org.springframework.boot:spring-boot-starter-actuator",
                    "org.springframework.boot:spring-boot-devtools",
                    "commons-io:commons-io",
                    "org.springframework.cloud:spring-cloud-starter-openfeign"
            ),

            // web
            if (features.contains(GrizzlyFeature.WEB))
                listOf(
                        "org.springframework.boot:spring-boot-starter-web",
                        "io.swagger.core.v3:swagger-annotations:2.0.8"
                )
            else
                listOf(),


            // cache
            if (features.contains(GrizzlyFeature.CACHE))
                listOf(
                        "org.springframework.boot:spring-boot-starter-cache",
                        "org.ehcache:ehcache",
                        "javax.cache:cache-api",
                        "org.terracotta:management-model:2.3.0"
                )
            else
                listOf(),

            // gcp
            if (features.contains(GrizzlyFeature.GCP))
                listOf(
                        "org.springframework.cloud:spring-cloud-starter-kubernetes-config",
                        "org.springframework.cloud:spring-cloud-gcp-starter",
                        "org.springframework.cloud:spring-cloud-gcp-starter-trace",
                        "org.springframework.cloud:spring-cloud-gcp-starter-logging",
                        "org.springframework.cloud:spring-cloud-gcp-starter-storage"
                )
            else
                listOf(),

            // graphql
            if (features.contains(GrizzlyFeature.GRAPHQL))
                listOf(
                        "com.graphql-java-kickstart:graphql-spring-boot-starter:$GRAPHQL_SPRING_BOOT_VERSION",
                        "com.graphql-java-kickstart:graphiql-spring-boot-starter:jar:$GRAPHQL_SPRING_BOOT_VERSION",
                        "com.graphql-java-kickstart:voyager-spring-boot-starter:jar:$GRAPHQL_SPRING_BOOT_VERSION",
                        "com.graphql-java-kickstart:graphql-java-tools:jar:$GRAPHQL_JAVA_TOOL_VERSION"
                )
            else
                listOf()
    ).flatten()
}