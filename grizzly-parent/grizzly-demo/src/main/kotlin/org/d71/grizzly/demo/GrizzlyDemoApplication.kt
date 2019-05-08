package org.d71.grizzly.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GrizzlyDemoApplication

fun main(args: Array<String>) {
	runApplication<GrizzlyDemoApplication>(*args)
}
