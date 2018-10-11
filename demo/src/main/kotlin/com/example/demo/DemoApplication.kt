package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurerComposite
import org.springframework.web.reactive.config.WebFluxConfigurer



@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
  runApplication<DemoApplication>(*args)
}

@Configuration
class WebFluxConfig {

  @Bean
  fun corsConfigurer(): WebFluxConfigurer {
    return object : WebFluxConfigurerComposite() {

      override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedOrigins("*")
          .allowedMethods("*")
      }
    }
  }
}
@RestController
open class Test {
  @GetMapping(value = ["test"], produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
  open fun test(): Flux<Task> {
    return Flux.fromStream(List(100) {
      Task(
        "1",
        "4f17bcdf-55c0-4027-90a5-63843b1ba0ea",
        "155491032",
        "2018-09-27T17:27:11.018",
        "Task lifecycle state changed from PLANNED to AVAILABLE",
        "1",
        "fc09510b-0ae9-4780-b32f-cb2b1da85117",
        "AVAILABLE",
        "false",
        "true",
        Id("4f17bcdf-55c0-4027-90a5-63843b1ba0ea", "155491032")
      )
    }.stream()).delayElements(Duration.ofSeconds(1));
  }
}

data class Task(
  val entityVersion: String,
  val entityId: String,
  val ehrHash: String,
  val time: String,
  val description: String,
  val entityOrder: String,
  val mTaskId: String,
  val lifecycleState: String,
  val preconditionsSatisfied: String,
  val waitConditionsSatisfied: String,
  val id: Id
)

data class Id(
  val entityId: String,
  val ehrHash: String
)
