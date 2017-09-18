package ee.smmv.msftteams

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.nio.charset.StandardCharsets

class Teams {

	companion object {
		@JvmStatic() private val log : Logger by lazy { LoggerFactory.getLogger(Teams::class.java) }
	}

	private val restTemplate : RestTemplate
	private val mapper : ObjectMapper = jacksonObjectMapper()

	private lateinit var webhookUrl : String

	constructor() {
		restTemplate = RestTemplate(mutableListOf<org.springframework.http.converter.HttpMessageConverter<*>>(StringHttpMessageConverter(StandardCharsets.UTF_8)))
	}

	constructor(restTemplate : RestTemplate) {
		this.restTemplate = restTemplate
	}

	fun with(webhookUrl : String) : Teams {
		this.webhookUrl = webhookUrl
		return this
	}

	fun publish(message : ConnectorMessage) = publish(message, HttpHeaders())

	fun publish(message : ConnectorMessage, headers : HttpHeaders) = publish(mapConnectorMessageToPayload(message), headers)

	fun publish(payload : JSONObject) = publish(payload, HttpHeaders())

	fun publish(payload : JSONObject, headers : HttpHeaders) = publish(payload.toString(), headers)

	/**
	 * @param payload JSON payload
	 * @param headers HTTP headers
	 * @throws RuntimeException
	 */
	fun publish(payload : String, headers : HttpHeaders) : Boolean {
		try {
			headers.contentType = MediaType.APPLICATION_JSON

			val webhookUri = URI.create(webhookUrl)
			val requestEntity : HttpEntity<String> = HttpEntity(payload, headers)
			val responseEntity : ResponseEntity<String> = restTemplate.postForEntity(webhookUri, requestEntity, String::class.java)

			log.debug("HTTP ${responseEntity.statusCode.value()}  ${responseEntity.statusCode.reasonPhrase}")

			return responseEntity.statusCode.is2xxSuccessful
		} catch (e : HttpServerErrorException) {
			log.error("Teams Webhook did not publish the message", e)
			log.debug(e.responseBodyAsString)

			throw RuntimeException(e)
		} catch (e : HttpClientErrorException) {
			log.error("Teams Client did not send the message", e)
			log.debug(e.responseBodyAsString)

			throw RuntimeException(e)
		}
	}

	fun mapConnectorMessageToPayload(message : ConnectorMessage) : String {
		return mapper.writeValueAsString(message)
	}

}
