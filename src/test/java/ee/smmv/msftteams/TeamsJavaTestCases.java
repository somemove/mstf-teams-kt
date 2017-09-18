package ee.smmv.msftteams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;

@DisplayName("Microsoft Teams Client tests")
public class TeamsJavaTestCases {

	private Teams client;

	@BeforeEach()
	public void setupClient() {
		this.client = new Teams();
	}

	@Test()
	@DisplayName("Test mapping of a simple message")
	public void simpleMessageMapping() {
		ConnectorMessage message = new ConnectorMessage();
		message.setText("Test");

		String result = client.mapConnectorMessageToPayload(message);

		assertThatJson(result)
			.node("text").isPresent()
			.node("text").isEqualTo("Test")
			.node("title").isAbsent()
			.node("summary").isAbsent();
	}

	@Test()
	@DisplayName("Test mapping of a titled message")
	public void titledMessageMapping() {
		ConnectorMessage message = new ConnectorMessage();
		message.setText("Test");
		message.setTitle("Title");

		String result = client.mapConnectorMessageToPayload(message);

		assertThatJson(result)
			.node("text").isPresent()
			.node("text").isEqualTo("Test")
			.node("title").isPresent()
			.node("title").isEqualTo("Title")
			.node("summary").isAbsent();
	}

}
