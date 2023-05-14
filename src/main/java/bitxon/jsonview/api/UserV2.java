package bitxon.jsonview.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public record UserV2(
    @JsonView(Views.Default.class)
    @JsonProperty("name")
    String name,

    @JsonView(Views.Internal.class)
    @JsonProperty("internalUuid")
    String internalUuid,

    @JsonView(Views.External.class)
    @JsonProperty("externalId")
    String externalId
) {
}
