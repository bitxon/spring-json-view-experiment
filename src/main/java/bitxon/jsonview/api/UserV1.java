package bitxon.jsonview.api;

import com.fasterxml.jackson.annotation.JsonView;

public record UserV1(
    @JsonView(Views.Default.class)
    String name,

    @JsonView(Views.Internal.class)
    String internalUuid,

    @JsonView(Views.External.class)
    String externalId
) {
}
