import { useEffect, useState } from 'react';
import LogoutButton from "../components/LogoutButton";


function EmpresaProyectoPage({ usuario }) {
  const [proyectos, setProyectos] = useState([]);
  const [nuevoProyecto, setNuevoProyecto] = useState({
    nombre: '',
    descripcion: '',
  });
  const [proyectoEditar, setProyectoEditar] = useState(null);

  // Cargar proyectos de la empresa al inicio
  useEffect(() => {
    fetch(`http://localhost:8083/proyectos/empresa/${usuario.id}`)
      .then((res) => res.json())
      .then((data) => setProyectos(data))
      .catch((err) => console.error('Error al cargar proyectos', err));
  }, [usuario.id]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const proyecto = {
      ...nuevoProyecto,
      empresaId: usuario.id,
    };

    fetch('http://localhost:8083/proyectos', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(proyecto),
    })
      .then((res) => res.json())
      .then((data) => {
        setProyectos([...proyectos, data]);
        setNuevoProyecto({ nombre: '', descripcion: '' });
        alert('Proyecto registrado exitosamente');
      })
      .catch((err) => console.error('Error al registrar proyecto', err));
  };

  const eliminarProyecto = (id) => {
    const confirmar = window.confirm('¿Estás seguro de que deseas eliminar este proyecto?');
    if (!confirmar) return;
  
    fetch(`http://localhost:8083/proyectos/${id}`, {
      method: 'DELETE',
    })
      .then(() => {
        setProyectos(proyectos.filter((p) => p.id !== id));
        alert('Proyecto eliminado correctamente');
      })
      .catch((err) => console.error('Error al eliminar proyecto', err));
  };
  

  const guardarEdicion = (e) => {
    e.preventDefault();
    fetch(`http://localhost:8083/proyectos/${proyectoEditar.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(proyectoEditar),
    })
      .then((res) => res.json())
      .then((actualizado) => {
        setProyectos(
          proyectos.map((p) => (p.id === actualizado.id ? actualizado : p))
        );
        setProyectoEditar(null);
        alert('Proyecto editado correctamente');
      })
      .catch((err) => console.error('Error al editar proyecto', err));
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Bienvenido, {usuario.nombre}</h2>

      <h3>Registrar nuevo proyecto</h3>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Nombre"
          value={nuevoProyecto.nombre}
          onChange={(e) => setNuevoProyecto({ ...nuevoProyecto, nombre: e.target.value })}
          required
        />
        <input
          type="text"
          placeholder="Descripción"
          value={nuevoProyecto.descripcion}
          onChange={(e) => setNuevoProyecto({ ...nuevoProyecto, descripcion: e.target.value })}
          required
        />
        <button type="submit">Guardar</button>
      </form>

      {proyectoEditar && (
        <form onSubmit={guardarEdicion} style={{ marginTop: '20px' }}>
          <h3>Editar proyecto</h3>
          <input
            type="text"
            value={proyectoEditar.nombre}
            onChange={(e) => setProyectoEditar({ ...proyectoEditar, nombre: e.target.value })}
          />
          <input
            type="text"
            value={proyectoEditar.descripcion}
            onChange={(e) => setProyectoEditar({ ...proyectoEditar, descripcion: e.target.value })}
          />
          <button type="submit">Guardar cambios</button>
          <button type="button" onClick={() => setProyectoEditar(null)} style={{ marginLeft: '10px' }}>Cancelar</button>
        </form>
      )}

      <h3>Mis proyectos</h3>
      <ul>
        {proyectos.map((p) => (
          <li key={p.id}>
            {p.nombre} - {p.estado}
            <button onClick={() => eliminarProyecto(p.id)} style={{ marginLeft: '10px' }}>
              Eliminar
            </button>
            <button onClick={() => setProyectoEditar(p)} style={{ marginLeft: '10px' }}>
              Editar
            </button>
          </li>
        ))}
      </ul>

      <LogoutButton />
    </div>
  );
}

export default EmpresaProyectoPage;
