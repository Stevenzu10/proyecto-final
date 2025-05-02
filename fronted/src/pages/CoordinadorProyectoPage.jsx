import { useEffect, useState } from 'react';
import LogoutButton from "../components/LogoutButton";


function CoordinadorProyectoPage() {
  const [proyectosPendientes, setProyectosPendientes] = useState([]);
  const [proyectosAprobados, setProyectosAprobados] = useState([]);
  const [postulaciones, setPostulaciones] = useState([]);

  // Cargar proyectos pendientes y aprobados
  useEffect(() => {
    fetch('http://localhost:8083/proyectos/pendientes')
      .then(res => res.json())
      .then(data => setProyectosPendientes(data))
      .catch(err => console.error('Error al cargar proyectos pendientes', err));

    fetch('http://localhost:8083/proyectos/aprobados')
      .then(res => res.json())
      .then(data => setProyectosAprobados(data))
      .catch(err => console.error('Error al cargar proyectos aprobados', err));
  }, []);

  const actualizarEstadoProyecto = (id, nuevoEstado) => {
    fetch(`http://localhost:8083/proyectos/${id}/estado`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: nuevoEstado

    })
      .then(res => res.json())
      .then(() => {
        setProyectosPendientes(proyectosPendientes.filter(p => p.id !== id));
        alert(`Proyecto ${nuevoEstado.toLowerCase()} exitosamente`);
      })
      .catch(err => console.error('Error al actualizar estado de proyecto', err));
  };

  const cargarPostulacionesPendientes = (proyectoId) => {
    fetch(`http://localhost:8084/postulaciones/proyecto/${proyectoId}/pendientes`)
      .then(res => res.json())
      .then(data => setPostulaciones(data))
      .catch(err => console.error('Error al cargar postulaciones', err));
  };

  const actualizarEstadoPostulacion = (id, nuevoEstado) => {
    fetch(`http://localhost:8084/postulaciones/${id}/estado`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(nuevoEstado),
    })
      .then(res => res.json())
      .then(() => {
        setPostulaciones(postulaciones.filter(p => p.id !== id));
        alert(`Postulación ${nuevoEstado.toLowerCase()} correctamente`);
      })
      .catch(err => console.error('Error al actualizar postulación', err));
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Proyectos pendientes de aprobación</h2>
      <ul>
        {proyectosPendientes.map(p => (
          <li key={p.id}>
            <strong>{p.nombre}</strong> - {p.descripcion}
            <button onClick={() => actualizarEstadoProyecto(p.id, 'APROBADO')} style={{ marginLeft: '10px' }}>
              Aprobar
            </button>
            <button onClick={() => actualizarEstadoProyecto(p.id, 'RECHAZADO')} style={{ marginLeft: '10px' }}>
              Rechazar
            </button>
          </li>
        ))}
      </ul>

      <h2>Proyectos aprobados</h2>
      <ul>
        {proyectosAprobados.map(p => (
          <li key={p.id}>
            <strong>{p.nombre}</strong>
            <button onClick={() => cargarPostulacionesPendientes(p.id)} style={{ marginLeft: '10px' }}>
              Ver postulaciones pendientes
            </button>
          </li>
        ))}
      </ul>

      {postulaciones.length > 0 && (
        <>
          <h3>Postulaciones pendientes</h3>
          <ul>
            {postulaciones.map(pos => (
              <li key={pos.id}>
                Estudiante ID: {pos.estudianteId}
                <button onClick={() => actualizarEstadoPostulacion(pos.id, 'APROBADO')} style={{ marginLeft: '10px' }}>
                  Aprobar
                </button>
                <button onClick={() => actualizarEstadoPostulacion(pos.id, 'RECHAZADO')} style={{ marginLeft: '10px' }}>
                  Rechazar
                </button>
              </li>
            ))}
          </ul>
        </>
      )}

      <LogoutButton />
    </div>
  );
}

export default CoordinadorProyectoPage;
