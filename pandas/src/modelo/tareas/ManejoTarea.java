/*package modelo.tareas;

import modelo.extra.Receta;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class ManejoTarea {
     private HashMap<String, HashSet<Tarea>> mapaTarea;
    private HashSet<Tarea> tareas;

    private final String archivoTareas = "tareas.dat";


    public ManejoTarea() {
        mapaTarea = new HashMap<>();
        tareas = new HashSet<>();

    }


    public void entradaTarea() throws IOException {
        FileInputStream fileInputStream = null;
        DataInputStream dataInputStream = null;
        try {
            fileInputStream = new FileInputStream(archivoTareas);
            dataInputStream = new DataInputStream(fileInputStream);

            try {
                while (dataInputStream.available() > 0) {
                    Tarea tarea = leerTarea(dataInputStream);

                    if (tarea != null) {
                        tareas.add(tarea);
                        mapaTarea.put(tarea.getTitulo(), tareas);
                    }
                }
            } catch (EOFException e) {
            // Si llegamos al final del archivo, salimos del bucle
        }
            } finally {
                // Cerramos los streams en el bloque finally para garantizar que se cierren
                // independientemente de si hubo una excepción o no
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        System.err.println("Error al cerrar el DataInputStream: " + e.getMessage());
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        System.err.println("Error al cerrar el FileInputStream: " + e.getMessage());
                    }
                }
            }
        }

        //Lee tareas desde el archivo archivoTareas y las agrega al HashSet tareas y al Map mapaTareas.
    /*public void entradaTarea() throws Exception
    {
        FileInputStream fileInputStream = null;
        DataInputStream dataInputStream = null;
        try {
            fileInputStream = new FileInputStream(archivoTareas);
            dataInputStream = new DataInputStream(fileInputStream);

            while (true) {
                try {
                    Tarea tarea = leerTarea(dataInputStream);
                    if(tarea != null)
                    {
                        tareas.add(tarea);
                        mapaTarea.put(tarea.getTitulo(), tareas);
                    }


                } catch (EOFException e) {
                    throw new EOFException("No se pudieron cargar los datos del archivo al set");
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("No encontrado");
        } catch (IOException e) {
            throw new IOException("Error en la lectura");
        } finally {
            try {
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                throw new IOException("Problema en la apertura");
            }
        }
    }


        public void salidaTareas () throws IOException {
            try (FileOutputStream fileOutputStream = new FileOutputStream(archivoTareas);
                 DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {

                for (Tarea tarea : tareas) {
                    if (tarea instanceof SeccionTrabajo) {
                        dataOutputStream.writeUTF("SeccionTrabajo");
                        SeccionTrabajo seccionTrabajo = (SeccionTrabajo) tarea;
                        dataOutputStream.writeUTF(seccionTrabajo.getTitulo());
                        dataOutputStream.writeUTF(seccionTrabajo.getObjetivo());
                        dataOutputStream.writeUTF(seccionTrabajo.getCodigo());
                        dataOutputStream.writeInt(seccionTrabajo.getTiempoTranscurrido());
                        dataOutputStream.writeInt(seccionTrabajo.getCalificacion());
                        dataOutputStream.writeUTF(seccionTrabajo.getRetroalimentacion());
                        dataOutputStream.writeUTF(seccionTrabajo.getFecha());
                        dataOutputStream.writeUTF(seccionTrabajo.getSector());
                        dataOutputStream.writeUTF(seccionTrabajo.getFechaLimite());
                    } else if (tarea instanceof SeccionEstudio) {
                        dataOutputStream.writeUTF("SeccionEstudio");
                        SeccionEstudio seccionEstudio = (SeccionEstudio) tarea;
                        dataOutputStream.writeUTF(seccionEstudio.getTitulo());
                        dataOutputStream.writeUTF(seccionEstudio.getObjetivo());
                        dataOutputStream.writeUTF(seccionEstudio.getCodigo());
                        dataOutputStream.writeInt(seccionEstudio.getTiempoTranscurrido());
                        dataOutputStream.writeInt(seccionEstudio.getCalificacion());
                        dataOutputStream.writeUTF(seccionEstudio.getRetroalimentacion());
                        dataOutputStream.writeUTF(seccionEstudio.getFecha());
                        dataOutputStream.writeUTF(seccionEstudio.getCategoria());
                        dataOutputStream.writeUTF(seccionEstudio.getMateria());
                        dataOutputStream.writeUTF(seccionEstudio.getUnidad());
                    } else if (tarea instanceof SeccionDeporte) {
                        dataOutputStream.writeUTF("SeccionDeporte");
                        SeccionDeporte seccionDeporte = (SeccionDeporte) tarea;
                        dataOutputStream.writeUTF(seccionDeporte.getTitulo());
                        dataOutputStream.writeUTF(seccionDeporte.getObjetivo());
                        dataOutputStream.writeUTF(seccionDeporte.getCodigo());
                        dataOutputStream.writeInt(seccionDeporte.getTiempoTranscurrido());
                        dataOutputStream.writeInt(seccionDeporte.getCalificacion());
                        dataOutputStream.writeUTF(seccionDeporte.getRetroalimentacion());
                        dataOutputStream.writeUTF(seccionDeporte.getFecha());
                        dataOutputStream.writeUTF(seccionDeporte.getEjercicios());
                        dataOutputStream.writeDouble(seccionDeporte.getCaloriasQuemadas());
                    } else if (tarea instanceof SeccionCocina) {
                        dataOutputStream.writeUTF("SeccionCocina");
                        SeccionCocina seccionCocina = (SeccionCocina) tarea;
                        dataOutputStream.writeUTF(seccionCocina.getTitulo());
                        dataOutputStream.writeUTF(seccionCocina.getObjetivo());
                        dataOutputStream.writeUTF(seccionCocina.getCodigo());
                        dataOutputStream.writeInt(seccionCocina.getTiempoTranscurrido());
                        dataOutputStream.writeInt(seccionCocina.getCalificacion());
                        dataOutputStream.writeUTF(seccionCocina.getRetroalimentacion());
                        dataOutputStream.writeUTF(seccionCocina.getFecha());
                        Receta receta = seccionCocina.getReceta();
                        dataOutputStream.writeUTF(receta.getIngredientes());
                        dataOutputStream.writeUTF(receta.getPasoAPaso());
                    }
                }
            } catch (IOException e) {
                throw new IOException("Error en la escritura del archivo", e);
            }
        }

        //Escribe todas las tareas del HashSet tareas al archivo archivoTareas
    /*public void salidaTareas()
    {
        FileOutputStream fileOutputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("archivoTareas");
            dataOutputStream = new DataOutputStream(fileOutputStream);

            for (Tarea tarea : tareas) {
                if (tarea instanceof SeccionTrabajo) {
                    dataOutputStream.writeUTF("SeccionTrabajo");
                    SeccionTrabajo seccionTrabajo = (SeccionTrabajo) tarea;
                    dataOutputStream.writeUTF(seccionTrabajo.getTitulo());
                    dataOutputStream.writeUTF(seccionTrabajo.getObjetivo());
                    dataOutputStream.writeUTF(seccionTrabajo.getCodigo());
                    dataOutputStream.writeInt(seccionTrabajo.getTiempoTranscurrido());
                    dataOutputStream.writeInt(seccionTrabajo.getCalificacion());
                    dataOutputStream.writeUTF(seccionTrabajo.getRetroalimentacion());
                    dataOutputStream.writeUTF(seccionTrabajo.getFecha());
                    dataOutputStream.writeUTF(seccionTrabajo.getSector());
                    dataOutputStream.writeUTF(seccionTrabajo.getFechaLimite());
                } else if (tarea instanceof SeccionEstudio) {
                    dataOutputStream.writeUTF("SeccionEstudio");
                    SeccionEstudio seccionEstudio = (SeccionEstudio) tarea;
                    dataOutputStream.writeUTF(seccionEstudio.getTitulo());
                    dataOutputStream.writeUTF(seccionEstudio.getObjetivo());
                    dataOutputStream.writeUTF(seccionEstudio.getCodigo());
                    dataOutputStream.writeInt(seccionEstudio.getTiempoTranscurrido());
                    dataOutputStream.writeInt(seccionEstudio.getCalificacion());
                    dataOutputStream.writeUTF(seccionEstudio.getRetroalimentacion());
                    dataOutputStream.writeUTF(seccionEstudio.getFecha());
                    dataOutputStream.writeUTF(seccionEstudio.getCategoria());
                    dataOutputStream.writeUTF(seccionEstudio.getMateria());
                    dataOutputStream.writeUTF(seccionEstudio.getUnidad());
                } else if (tarea instanceof SeccionDeporte) {
                    dataOutputStream.writeUTF("SeccionDeporte");
                    SeccionDeporte seccionDeporte = (SeccionDeporte) tarea;
                    dataOutputStream.writeUTF(seccionDeporte.getTitulo());
                    dataOutputStream.writeUTF(seccionDeporte.getObjetivo());
                    dataOutputStream.writeUTF(seccionDeporte.getCodigo());
                    dataOutputStream.writeInt(seccionDeporte.getTiempoTranscurrido());
                    dataOutputStream.writeInt(seccionDeporte.getCalificacion());
                    dataOutputStream.writeUTF(seccionDeporte.getRetroalimentacion());
                    dataOutputStream.writeUTF(seccionDeporte.getFecha());
                    dataOutputStream.writeUTF(seccionDeporte.getEjercicios());
                    dataOutputStream.writeDouble(seccionDeporte.getCaloriasQuemadas());
                } else if (tarea instanceof SeccionCocina) {
                    dataOutputStream.writeUTF("SeccionCocina");
                    SeccionCocina seccionCocina = (SeccionCocina) tarea;
                    dataOutputStream.writeUTF(seccionCocina.getTitulo());
                    dataOutputStream.writeUTF(seccionCocina.getObjetivo());
                    dataOutputStream.writeUTF(seccionCocina.getCodigo());
                    dataOutputStream.writeInt(seccionCocina.getTiempoTranscurrido());
                    dataOutputStream.writeInt(seccionCocina.getCalificacion());
                    dataOutputStream.writeUTF(seccionCocina.getRetroalimentacion());
                    dataOutputStream.writeUTF(seccionCocina.getFecha());
                    Receta receta = seccionCocina.getReceta();
                    dataOutputStream.writeUTF(receta.getIngredientes());
                    dataOutputStream.writeUTF(receta.getPasoAPaso());
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


        //Para escribir las tareas en el archivo, incluyendo un identificador para cada tipo de tarea y sus atributos:
        public void guardarTarea (Tarea tarea, DataOutputStream dataOutputStream) throws IOException {
            if (tarea instanceof SeccionTrabajo) {
                dataOutputStream.writeUTF("SeccionTrabajo");
                SeccionTrabajo seccionTrabajo = (SeccionTrabajo) tarea;
                dataOutputStream.writeUTF(seccionTrabajo.getTitulo());
                dataOutputStream.writeUTF(seccionTrabajo.getObjetivo());
                dataOutputStream.writeUTF(seccionTrabajo.getCodigo());
                dataOutputStream.writeInt(seccionTrabajo.getTiempoTranscurrido());
                dataOutputStream.writeInt(seccionTrabajo.getCalificacion());
                dataOutputStream.writeUTF(seccionTrabajo.getRetroalimentacion());
                dataOutputStream.writeUTF(seccionTrabajo.getFecha());
                dataOutputStream.writeUTF(seccionTrabajo.getSector());
                dataOutputStream.writeUTF(seccionTrabajo.getFechaLimite());
            } else if (tarea instanceof SeccionEstudio) {
                dataOutputStream.writeUTF("SeccionEstudio");
                SeccionEstudio seccionEstudio = (SeccionEstudio) tarea;
                dataOutputStream.writeUTF(seccionEstudio.getTitulo());
                dataOutputStream.writeUTF(seccionEstudio.getObjetivo());
                dataOutputStream.writeUTF(seccionEstudio.getCodigo());
                dataOutputStream.writeInt(seccionEstudio.getTiempoTranscurrido());
                dataOutputStream.writeInt(seccionEstudio.getCalificacion());
                dataOutputStream.writeUTF(seccionEstudio.getRetroalimentacion());
                dataOutputStream.writeUTF(seccionEstudio.getFecha());
                dataOutputStream.writeUTF(seccionEstudio.getCategoria());
                dataOutputStream.writeUTF(seccionEstudio.getMateria());
                dataOutputStream.writeUTF(seccionEstudio.getUnidad());
            } else if (tarea instanceof SeccionDeporte) {
                dataOutputStream.writeUTF("SeccionDeporte");
                SeccionDeporte seccionDeporte = (SeccionDeporte) tarea;
                dataOutputStream.writeUTF(seccionDeporte.getTitulo());
                dataOutputStream.writeUTF(seccionDeporte.getObjetivo());
                dataOutputStream.writeUTF(seccionDeporte.getCodigo());
                dataOutputStream.writeInt(seccionDeporte.getTiempoTranscurrido());
                dataOutputStream.writeInt(seccionDeporte.getCalificacion());
                dataOutputStream.writeUTF(seccionDeporte.getRetroalimentacion());
                dataOutputStream.writeUTF(seccionDeporte.getFecha());
                dataOutputStream.writeUTF(seccionDeporte.getEjercicios());
                dataOutputStream.writeDouble(seccionDeporte.getCaloriasQuemadas());
            } else if (tarea instanceof SeccionCocina) {
                dataOutputStream.writeUTF("SeccionCocina");
                SeccionCocina seccionCocina = (SeccionCocina) tarea;
                dataOutputStream.writeUTF(seccionCocina.getTitulo());
                dataOutputStream.writeUTF(seccionCocina.getObjetivo());
                dataOutputStream.writeUTF(seccionCocina.getCodigo());
                dataOutputStream.writeInt(seccionCocina.getTiempoTranscurrido());
                dataOutputStream.writeInt(seccionCocina.getCalificacion());
                dataOutputStream.writeUTF(seccionCocina.getRetroalimentacion());
                dataOutputStream.writeUTF(seccionCocina.getFecha());
                Receta receta = seccionCocina.getReceta();
                dataOutputStream.writeUTF(receta.getIngredientes());
                dataOutputStream.writeUTF(receta.getPasoAPaso());
            }

        }

        //Lee una tarea del DataInputStream y la devuelve como un objeto de una subclase de Tarea.
        public Tarea leerTarea (DataInputStream dataInputStream) throws IOException {
            String tipoTarea = dataInputStream.readUTF();
            Tarea tarea = null;

            if (tipoTarea.equals("SeccionTrabajo")) {
                String titulo = dataInputStream.readUTF();
                String objetivo = dataInputStream.readUTF();
                String codigo = dataInputStream.readUTF();
                int tiempoTranscurrido = dataInputStream.readInt();
                int calificacion = dataInputStream.readInt();
                String retroalimentacion = dataInputStream.readUTF();
                String fecha = dataInputStream.readUTF();
                String sector = dataInputStream.readUTF();
                String fechaLimite = dataInputStream.readUTF();

                // tarea = new SeccionTrabajo(titulo, objetivo, sector, fechaLimite, codigo);
            } else if (tipoTarea.equals("SeccionEstudio")) {
                String titulo = dataInputStream.readUTF();
                String objetivo = dataInputStream.readUTF();
                String codigo = dataInputStream.readUTF();
                int tiempoTranscurrido = dataInputStream.readInt();
                int calificacion = dataInputStream.readInt();
                String retroalimentacion = dataInputStream.readUTF();
                String fecha = dataInputStream.readUTF();
                String categoria = dataInputStream.readUTF();
                String materia = dataInputStream.readUTF();
                String unidad = dataInputStream.readUTF();

                tarea = new SeccionEstudio(titulo, objetivo, categoria, materia, unidad, codigo);
            } else if (tipoTarea.equals("SeccionDeporte")) {
                String titulo = dataInputStream.readUTF();
                String objetivo = dataInputStream.readUTF();
                String codigo = dataInputStream.readUTF();
                int tiempoTranscurrido = dataInputStream.readInt();
                int calificacion = dataInputStream.readInt();
                String retroalimentacion = dataInputStream.readUTF();
                String fecha = dataInputStream.readUTF();
                String ejercicios = dataInputStream.readUTF();
                double caloriasQuemadas = dataInputStream.readDouble();

                // tarea = new SeccionDeporte(titulo, objetivo, ejercicios, caloriasQuemadas, codigo);
            } else if (tipoTarea.equals("SeccionCocina")) {
                String titulo = dataInputStream.readUTF();
                String objetivo = dataInputStream.readUTF();
                String codigo = dataInputStream.readUTF();
                int tiempoTranscurrido = dataInputStream.readInt();
                int calificacion = dataInputStream.readInt();
                String retroalimentacion = dataInputStream.readUTF();
                String fecha = dataInputStream.readUTF();
                String ingredientes = dataInputStream.readUTF();
                String pasoAPaso = dataInputStream.readUTF();

                Receta receta = new Receta(ingredientes, pasoAPaso);

                tarea = new SeccionCocina(receta);
            }

            return tarea; // O lanza una excepción si el tipo de tarea no es reconocido
        }


        public void altaTarea (Tarea tarea)
        {
            mapaTarea.put(tarea.getTitulo(), tareas);
        }
        public String mostrarTareas () {
            StringBuilder tareasString = new StringBuilder();
            for (Tarea tarea : tareas) {
                tareasString.append(tarea.toString()).append("\n");
            }
            return tareasString.toString();
        }


    }
*/