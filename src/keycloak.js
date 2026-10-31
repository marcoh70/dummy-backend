
import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
  url: "http://localhost:9090",
  realm: "ne_realm",
  clientId: "react-bff",
});

export default keycloak;
