
import { useState } from "react";
import keycloak from "./keycloak.js";

const API_URL = "http://localhost:9092/api/ente";

export default function App() {
  const [result, setResult] = useState("");
  const [loading, setLoading] = useState(false);

  async function callGateway() {
    setLoading(true);
    setResult("");

    try {
      // Renueva el token si está próximo a expirar.
      await keycloak.updateToken(30);

      const response = await fetch(API_URL, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${keycloak.token}`,
          Accept: "application/json",
        },
      });

      const body = await response.text();

      if (!response.ok) {
        throw new Error(
          `HTTP ${response.status}: ${body}`
        );
      }

      setResult(body || "Petición exitosa.");
    } catch (error) {
      setResult(error.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <main style={{ padding: "2rem", fontFamily: "sans-serif" }}>
      <h1>React + Keycloak + API Gateway</h1>

      <p>
        Usuario: {keycloak.tokenParsed?.preferred_username}
      </p>

      <p>
        Cliente: react-bff
      </p>

      <button onClick={callGateway} disabled={loading}>
        {loading ? "Consultando..." : "Consultar Ente"}
      </button>

      <button
        onClick={() =>
          keycloak.logout({
            redirectUri: window.location.origin,
          })
        }
        style={{ marginLeft: "1rem" }}
      >
        Cerrar sesión
      </button>

      <h2>Respuesta del API Gateway</h2>

      <pre style={{ whiteSpace: "pre-wrap" }}>
        {result}
      </pre>
    </main>
  );
}
