const pool = require('./db');

async function init() {
  try {
    await pool.query(`
      CREATE TABLE IF NOT EXISTS servicios (
        id SERIAL PRIMARY KEY,
        nombre VARCHAR(100) NOT NULL,
        descripcion TEXT NOT NULL,
        cupos_disponibles INTEGER NOT NULL DEFAULT 0,
        horario VARCHAR(120) NOT NULL
      );
    `);

    await pool.query(`
      CREATE TABLE IF NOT EXISTS turnos (
        id SERIAL PRIMARY KEY,
        nombre_cliente VARCHAR(120) NOT NULL,
        documento VARCHAR(50) NOT NULL,
        servicio_id INTEGER NOT NULL REFERENCES servicios(id),
        hora_solicitud TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
      );
    `);

    const countResult = await pool.query('SELECT COUNT(*)::int AS total FROM servicios');
    const total = countResult.rows[0].total;

    if (total === 0) {
      await pool.query(`
        INSERT INTO servicios (nombre, descripcion, cupos_disponibles, horario)
        VALUES
          ('Hamburguesas', 'Pedido y entrega de hamburguesas artesanales.', 12, '10:00 AM - 9:00 PM'),
          ('Perros Calientes', 'Turnos para preparar perros calientes especiales.', 8, '10:00 AM - 9:00 PM'),
          ('Salchipapas', 'Solicitud de pedidos de salchipapas tradicionales y especiales.', 5, '11:00 AM - 10:00 PM'),
          ('Arepas Rellenas', 'Atención para pedidos de arepas rellenas.', 10, '8:00 AM - 8:00 PM'),
          ('Jugos Naturales', 'Preparación de jugos naturales y batidos.', 15, '8:00 AM - 6:00 PM'),
          ('Almuerzos Ejecutivos', 'Solicitud de almuerzos del día.', 0, '12:00 PM - 3:00 PM');
      `);
      console.log('Servicios de ejemplo insertados.');
    }

    console.log('Base de datos inicializada correctamente.');
  } catch (error) {
    console.error('Error inicializando la base de datos:', error.message);
  } finally {
    await pool.end();
  }
}

init();
