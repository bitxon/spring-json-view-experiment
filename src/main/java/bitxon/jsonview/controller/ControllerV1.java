package bitxon.jsonview.controller;

import bitxon.jsonview.api.Views;
import bitxon.jsonview.api.UserV1;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ControllerV1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerV1.class);


    @GetMapping("/internal")
    @JsonView(Views.Internal.class)
    public UserV1 getInternal() {
        return new UserV1("Mike", "internalValue", "externalValue");
    }

    @GetMapping("/external")
    @JsonView(Views.External.class)
    public UserV1 getExternal() {
        return new UserV1("Mike", "internalValue", "externalValue");
    }

    @PostMapping(path = "/internal", consumes = {"application/json"})
    public UserV1 postInternal(@RequestBody @JsonView(Views.Internal.class) final UserV1 user) {
        LOGGER.info("User Data: {}", user);
        requireNull(user.externalId(), "'externalId' must be null for /internal endpoint");
        return user;
    }

    @PostMapping(path = "/external", consumes = {"application/json"})
    public UserV1 postExternal(@RequestBody @JsonView(Views.External.class) final UserV1 user) {
        LOGGER.info("User Data: {}", user);
        requireNull(user.internalUuid(), "'internalUuid' must be null for /external endpoint");
        return user;
    }


    private static void requireNull(Object obj, String message) {
        if (obj != null) {
            throw new NullPointerException(message);
        }
    }
}
