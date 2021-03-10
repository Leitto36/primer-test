package com.primer.demo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.restassured.RestAssured
import com.primer.demo.util.log
import java.io.File
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.client.MockServerClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.output.Slf4jLogConsumer

@ActiveProfiles("test")
@Tag("integration-test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
abstract class AbstractIntegrationTest {

    @Autowired
    private lateinit var mapper: ObjectMapper

    companion object {
        @JvmStatic
        val mockServerClient: MockServerClient

        @JvmStatic
        val mockServerPort: Int

        private val slf4jLogConsumer = Slf4jLogConsumer(LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME))

        private val dockerContainers = KDockerComposeContainer(
            File("src/test/resources/docker-compose.yml")
        )
            .withLocalCompose(true)
            .withExposedService(Service("mockserver.port", "mockserver", 1080))
            .withLogConsumer("mockserver", slf4jLogConsumer)

        init {
            log.info("Start container")
            dockerContainers.start()
            dockerContainers.updatePortsOnSystemProperties()

            mockServerPort = Integer.parseInt(System.getProperty("mockserver.port"))
            mockServerClient = MockServerClient("localhost", mockServerPort)
        }
    }

    @LocalServerPort
    protected val serverPort: Int = 0

    @BeforeEach
    internal fun intTestSetup() {
        RestAssured.baseURI = "http://localhost:$serverPort"
        mockServerClient.reset()
    }

    fun jsonTree(value: String): JsonNode = mapper.readTree(value)
}

data class Service(
    val portProperty: String,
    val service: String,
    val port: Int
)

class KDockerComposeContainer(file: File) : DockerComposeContainer<KDockerComposeContainer>(file) {
    private val exposedServices = mutableListOf<Service>()

    fun withExposedService(service: Service): KDockerComposeContainer {
        exposedServices.add(service)

        return this.withExposedService(service.service, service.port)
    }

    fun updatePortsOnSystemProperties() {
        exposedServices.forEach {
            System.setProperty(it.portProperty, this.getServicePort(it.service, it.port).toString())
        }
    }
}
