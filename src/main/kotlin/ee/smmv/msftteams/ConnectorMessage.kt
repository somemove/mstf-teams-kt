package ee.smmv.msftteams

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ConnectorMessage(
	var summary : String? = null,
	var title : String? = null,
	var text : String? = null
)
