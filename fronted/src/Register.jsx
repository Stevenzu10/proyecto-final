import { useState } from "react";

function Register({ onRegisterSuccess }) {
  const [nombre, setNombre] = useState("");
  const [correo, setCorreo] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [rol, setRol] = useState("ESTUDIANTE"); // Rol por defecto
  const [error, setError] = useState("");

  const handleRegister = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:8082/usuarios", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre, correo, contrasena, rol }),
      });

      if (!response.ok) {
        throw new Error("Error al registrar usuario");
      }

      const usuario = await response.json();
      onRegisterSuccess(usuario); // Ir directo al login o sesión activa
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Registro</h1>
      <form onSubmit={handleRegister}>
        <input
          type="text"
          placeholder="Nombre"
          value={nombre}
          onChange={(e) => setNombre(e.target.value)}
          required
        />
        <input
          type="email"
          placeholder="Correo"
          value={correo}
          onChange={(e) => setCorreo(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Contraseña"
          value={contrasena}
          onChange={(e) => setContrasena(e.target.value)}
          required
        />
        <select value={rol} onChange={(e) => setRol(e.target.value)}>
          <option value="EMPRESA">Empresa</option>
          <option value="ESTUDIANTE">Estudiante</option>
          <option value="COORDINADOR">Coordinador</option>
        </select>
        <button type="submit">Registrarse</button>
      </form>
      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
}

export default Register;
