import LogoutButton from './components/LogoutButton';

function EstudiantePage({ usuario }) {
  return (
    <div>
      <h2>Bienvenido, {usuario.nombre || 'Estudiante'}</h2>
      <p>Esta es la vista de estudiante.</p>
      <LogoutButton />
    </div>
  );
}

export default EstudiantePage;
