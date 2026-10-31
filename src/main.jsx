
import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import keycloak from "./keycloak.js";
import "./index.css";

const root = ReactDOM.createRoot(
  document.getElementById("root")
);

keycloak
  .init({
    onLoad: "login-required",
    pkceMethod: "S256",
    checkLoginIframe: false,
  })
  .then((authenticated) => {
    if (!authenticated) {
      return keycloak.login();
    }

    root.render(
      <React.StrictMode>
        <App />
      </React.StrictMode>
    );
  })
  .catch((error) => {
    console.error("Error inicializando Keycloak:", error);

    root.render(
      <div>
        No fue posible iniciar sesión con Keycloak.
      </div>
    );
  });
