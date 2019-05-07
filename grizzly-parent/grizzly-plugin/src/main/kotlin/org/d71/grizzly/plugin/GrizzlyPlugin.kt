package org.d71.grizzly.plugin

import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.artifacts.DependencyResolutionListener
import org.gradle.api.artifacts.ResolvableDependencies
import io.spring.gradle.dependencymanagement.dsl.ImportsHandler
import com.sun.javafx.scene.CameraHelper.project
import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import org.gradle.api.Action


open class MyExt(
        val toolVersion: String = "xxx"
)

open class GrizzlyPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val myExt = extensions.create("grizzlyPlugin", MyExt::class.java)
            val compileDeps = project.configurations.getByName("compile").dependencies

            gradle.addListener(object : DependencyResolutionListener {
                override fun beforeResolve(dependencies: ResolvableDependencies) {
                    // core
                    compileDeps.add(project.dependencies.create("org.springframework.boot:spring-boot-starter-logging"))
                    compileDeps.add(project.dependencies.create("org.springframework.boot:spring-boot-starter-actuator"))
                    compileDeps.add(project.dependencies.create("org.springframework.boot:spring-boot-devtools"))
                    compileDeps.add(project.dependencies.create("commons-io:commons-io"))
                    compileDeps.add(project.dependencies.create("org.springframework.cloud:spring-cloud-starter-openfeign"))

                    // web
                    compileDeps.add(project.dependencies.create("org.springframework.boot:spring-boot-starter-web"))
                    compileDeps.add(project.dependencies.create("io.swagger.core.v3:swagger-annotations:2.0.8"))

                    // cache
                    // compileDeps.add(project.dependencies.create("org.springframework.boot:spring-boot-starter-cache"))
                    // compileDeps.add(project.dependencies.create("org.ehcache:ehcache"))
                    // compileDeps.add(project.dependencies.create("javax.cache:cache-api"))
                    // compileDeps.add(project.dependencies.create("org.terracotta:management-model:2.3.0"))

                    // gcp
                    // compileDeps.add(project.dependencies.create("org.springframework.cloud:spring-cloud-starter-kubernetes-config"))
                    // compileDeps.add(project.dependencies.create("org.springframework.cloud:spring-cloud-gcp-starter"))
                    // compileDeps.add(project.dependencies.create("org.springframework.cloud:spring-cloud-gcp-starter-trace"))
                    // compileDeps.add(project.dependencies.create("org.springframework.cloud:spring-cloud-gcp-starter-logging"))
                    // compileDeps.add(project.dependencies.create("org.springframework.cloud:spring-cloud-gcp-starter-storage"))

                    println("myConfig=${compileDeps}")
                    gradle.removeListener(this)
                }

                override fun afterResolve(dependencies: ResolvableDependencies) {}
            })

            pluginManager.apply("org.springframework.boot")
            pluginManager.apply("io.spring.dependency-management")


            plugins.apply(DependencyManagementPlugin::class.java)
            extensions
                    .findByType(DependencyManagementExtension::class.java)!!
                    .imports(object : Action<ImportsHandler> {

                        override fun execute(importsHandler: ImportsHandler) {
                            val springCloudVersion = "Greenwich.SR1"
                            importsHandler.mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
                        }

                    })

        }
    }
}

