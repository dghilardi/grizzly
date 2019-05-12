package org.d71.grizzly.plugin

import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.artifacts.DependencyResolutionListener
import org.gradle.api.artifacts.ResolvableDependencies
import io.spring.gradle.dependencymanagement.dsl.ImportsHandler
import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import org.d71.grizzly.plugin.dependencymng.GrizzlySpring
import org.d71.grizzly.plugin.model.GrizzlyFeature
import org.d71.grizzly.plugin.model.GrizzlyFlavour
import org.gradle.api.Action

open class GrizzlyExt(
        var flavour: GrizzlyFlavour = GrizzlyFlavour.SPRING,
        var features: Array<GrizzlyFeature> = arrayOf()
)

open class GrizzlyPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val grizzlyExt = extensions.create("grizzlyPlugin", GrizzlyExt::class.java)
            val compileDeps = project.configurations.getByName("compile").dependencies

            gradle.addListener(object : DependencyResolutionListener {
                override fun beforeResolve(dependencies: ResolvableDependencies) {
                    println("config: '${grizzlyExt.flavour}' - ${grizzlyExt.features.asList()}")

                    val deps = GrizzlySpring().dependencyList(grizzlyExt.features.toSet())

                    deps.forEach { compileDeps.add(project.dependencies.create(it)) }

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

