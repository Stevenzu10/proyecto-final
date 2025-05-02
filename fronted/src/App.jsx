import { useState } from "react";
import Login from "./Login";
import Register from "./Register";
import EmpresaProyectoPage from './pages/EmpresaProyectoPage';
import EstudiantePage from "./pages//EstudiantePage";
import CoordinadorProyectoPage from './pages/CoordinadorProyectoPage';


function App() {
  const [usuario, setUsuario] = useState(
    JSON.parse(localStorage.getItem("usuario")) || null
  );
  const [vista, setVista] = useState("login"); // login | register

  const handleLoginSuccess = (usuarioLogueado) => {
    localStorage.setItem("usuario", JSON.stringify(usuarioLogueado));
    setUsuario(usuarioLogueado);
  };

  const handleRegisterSuccess = (usuarioRegistrado) => {
    setVista("login");
    alert("Usuario registrado exitosamente");
  };

  if (!usuario) {
    return (
      <>
        {vista === "login" ? (
          <>
            <Login onLoginSuccess={handleLoginSuccess} />
            <p>
              ¿No tienes cuenta?{" "}
              <button onClick={() => setVista("register")}>
                Registrarse
              </button>
            </p>
          </>
        ) : (
          <>
            <Register onRegisterSuccess={handleRegisterSuccess} />
            <p>
              ¿Ya tienes cuenta?{" "}
              <button onClick={() => setVista("login")}>Iniciar sesión</button>
            </p>
          </>
        )}
      </>
    );
  }

  if (usuario.rol === "EMPRESA") {
    return <EmpresaProyectoPage usuario={usuario} />;
  } else if (usuario.rol === "ESTUDIANTE") {
    return <EstudiantePage usuario={usuario} />;
  } else if (usuario.rol === "COORDINADOR") {
    return <CoordinadorProyectoPage usuario={usuario} />;
  } else {
    return <div>Rol desconocido</div>;
  }
}

export default App;
