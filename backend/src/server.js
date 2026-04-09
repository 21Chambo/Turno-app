const express = require('express');
const cors = require('cors');
const pool = require('./db');
require('dotenv').config();

const app = express();
app.use(cors());
app.use(express.json());

app.get('/', (_req, res) => {
  res.json({
    app: 'TurnoFácil API',
    status: 'ok',
    endpoints: [
      'GET /api/servicios',
      'GET /api/servicios/:id',
      'POST /api/turnos'
    ]
  });
});

app.get('/api/servicios', async (_req, res) => {
  try {
    const result = await pool.query('SELECT * FROM servicios ORDER BY id ASC');
    res.json(result.rows);
  } catch (error) {
    res.status(500).json({ error: 'Error al obtener los servicios.' });
  }
});

app.get('/api/servicios/:id', async (req, res) => {
  try {
    const { id } = req.params;
    const result = await pool.query('SELECT * FROM servicios WHERE id = $1', [id]);

    if (result.rows.length === 0) {
      return res.status(404).json({ error: 'Servicio no encontrado.' });
    }

    res.json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: 'Error al obtener el detalle del servicio.' });
  }
});

app.post('/api/turnos', async (req, res) => {
  const { nombre, documento, servicioId, hora } = req.body;

  if (!nombre || !documento || !servicioId) {
    return res.status(400).json({
      error: 'Los campos nombre, documento y servicioId son obligatorios.'
    });
  }

  try {
    const servicioResult = await pool.query('SELECT * FROM servicios WHERE id = $1', [servicioId]);

    if (servicioResult.rows.length === 0) {
      return res.status(404).json({ error: 'El servicio no existe.' });
    }

    const servicio = servicioResult.rows[0];

    if (servicio.cupos_disponibles <= 0) {
      return res.status(400).json({ error: 'Sin cupos disponibles para este servicio.' });
    }

    const insertResult = await pool.query(
      `INSERT INTO turnos (nombre_cliente, documento, servicio_id, hora_solicitud)
       VALUES ($1, $2, $3, COALESCE($4::timestamp, CURRENT_TIMESTAMP))
       RETURNING id, nombre_cliente, documento, servicio_id, hora_solicitud`,
      [nombre, documento, servicioId, hora || null]
    );

    await pool.query(
      'UPDATE servicios SET cupos_disponibles = cupos_disponibles - 1 WHERE id = $1',
      [servicioId]
    );

    res.status(201).json({
      mensaje: 'Turno creado correctamente.',
      turno: insertResult.rows[0]
    });
  } catch (error) {
    res.status(500).json({ error: 'Error al crear el turno.' });
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en http://localhost:${PORT}`);
});
