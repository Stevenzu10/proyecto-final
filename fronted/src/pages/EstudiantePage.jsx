import { useEffect, useState } from 'react';
import LogoutButton from '../components/LogoutButton';

function EstudiantePage({ usuario }) {
  const [proyectos, setProyectos] = useState([]);
  const [postulaciones, setPostulaciones] = useState([]);
  const [mensaje, setMensaje] = useState('');

  // Cargar proyectos aprobados al inicio
  useEffect(() => {
    fetch('http://localhost:8083/proyectos/aprobados')
      .then(res => res.json())
      .then(data => setProyectos(data))
      .catch(err => console.error('Error al cargar proyectos aprobados', err));

    fetch(`http://localhost:8084/postulaciones/estudiante/${usuario.id}`)
      .then(res => res.json())
      .then(data => setPostulaciones(data))
      .catch(err => console.error('Error al cargar postulaciones', err));
  }, [usuario.id]);

  const postularse = (proyectoId) => {
    fetch('http://localhost:8084/postulaciones', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ estudianteId: usuario.id, proyectoId })
    })
      .then(res => {
        if (!res.ok) throw new Error('Ya estás postulado o hubo un error');
        return res.json();
      })
      .then((nueva) => {
        setPostulaciones([...postulaciones, nueva]);
        setMensaje('Postulación exitosa');
      })
      .catch(err => setMensaje(err.message));
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Bienvenido, {usuario.nombre}</h2>

      <h3>Proyectos disponibles</h3>
      <ul>
        {proyectos.map(p => (
          <li key={p.id}>
            {p.nombre} - {p.descripcion}
            <button onClick={() => postularse(p.id)} style={{ marginLeft: '10px' }}>
              Postularme
            </button>
          </li>
        ))}
      </ul>

      <h3>Mis postulaciones</h3>
      <ul>
        {postulaciones.map(pos => (
          <li key={pos.id}>
            Proyecto ID: {pos.proyectoId} - Estado: {pos.estado}
          </li>
        ))}
      </ul>

      {mensaje && <p style={{ color: 'green' }}>{mensaje}</p>}
      <LogoutButton />
    </div>
  );
}

export default EstudiantePage;
