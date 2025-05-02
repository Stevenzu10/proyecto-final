function LogoutButton() {
    const handleLogout = () => {
      localStorage.removeItem('usuario');
      window.location.reload(); // Reinicia la app
    };
  
    return (
      <button onClick={handleLogout}>
        Cerrar sesión
      </button>
    );
  }
  
  export default LogoutButton;
  