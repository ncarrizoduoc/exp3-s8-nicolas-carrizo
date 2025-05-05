/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exp3_s8_nicolas_carrizo;

/**
 *
 * @author nicol
 */

import java.util.Scanner;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;

public class Exp3_S8_Nicolas_Carrizo {

    /**
     * @param args the command line arguments
     */
    
    //Clase Reserva
    static class Reserva{
        String asiento, ubicacion, idCliente, idReserva;
        int precioBase, descuento, precioFinal;
        
        //Constructor de clase
        public Reserva(String[] datosReserva){
            this.asiento = datosReserva[0];
            this.ubicacion = datosReserva[1];
            this.idCliente = datosReserva[2];
            this.precioBase = Integer.parseInt(datosReserva[4]);
            this.descuento = Integer.parseInt(datosReserva[5]);
            this.precioFinal = Integer.parseInt(datosReserva[6]);
            this.idReserva = datosReserva[7];
            System.out.println("Su entrada se ha reservado correctamente");
            imprimirReserva();
        }
        
        //Metodo que imprime la informacion de una reserva
        private void imprimirReserva(){
            String separador = "-".repeat(32);
            System.out.println("\n" + separador + "\nDetalles de la reserva\n" + separador);
            System.out.println("ID de Reserva: " + this.idReserva);
            System.out.println("ID de cliente: " + this.idCliente);
            System.out.println(separador);
            System.out.println("Asiento reservado: " + this.asiento);
            System.out.println("Ubicacion del asiento: Sector " + this.ubicacion);
            System.out.println("Precio base de la entrada: $" + this.precioBase);
            System.out.println("Descuento aplicado: " + this.descuento + "%");
            System.out.println("El costo final es: $" + this.precioFinal);
            System.out.println(separador);
        }
    }
    
    //Clase Compra
    static class Compra{
        ArrayList<String> asientos;
        int idCompra, totalParcial, totalFinal, descuentos;
        
        public Compra(int idCompra, ArrayList<Reserva> listaReservas, int totalParcial, ArrayList<Integer> listaDescuentos){
            this.idCompra = idCompra;
            this.asientos = new ArrayList<>();
            for (Reserva reserva : listaReservas){
                this.asientos.add(reserva.asiento);
            }
            this.descuentos = 0;
            for (int descuento : listaDescuentos){
                this.descuentos += descuento;
            }
            this.totalParcial = totalParcial;
            this.totalFinal = totalParcial * (100 - this.descuentos) / 100;
        }
        
        public void imprimirCompra(String nombreTeatro){
            String separador = "-".repeat(32);
            System.out.printf("%s%n%s%n%s%n", separador, nombreTeatro, separador);
            System.out.println("ID de la compra: " + idCompra);
            System.out.println(separador);
            System.out.print("Asientos comprados: ");
            for (String asiento : this.asientos){
                System.out.print(asiento + " ");
            }
            System.out.println(); //Salto de linea
            System.out.println("Costo parcial: $" + this.totalParcial);
            System.out.printf("Descuento aplicado al total (segun promociones): %d%%%n", this.descuentos);
            System.out.println("Costo final: $" + this.totalFinal);
            System.out.println(separador);
        }
    }
    
    //Clase Teatro
    static class Teatro{
        String nombre;
        ArrayList<Reserva> listaReservas;
        ArrayList<Compra> listaCompras;
        ArrayList<Integer> listaDescuentos;
        
        static int totalReservas = 0;
        static int totalCompras = 0;
        static int cantidadCompras = 0;
        static int entradasVendidas = 0;
        static int comprasAnuladas = 0;
        
        public Teatro(String nombre){
            this.nombre = nombre;
            this.listaReservas = new ArrayList<>();
            this.listaCompras = new ArrayList<>();
            this.listaDescuentos = new ArrayList<>();
            this.listaDescuentos.add(0);
            this.listaDescuentos.add(0);
            System.out.println("Bienvenido al sistema de venta de entradas del " + this.nombre);
        }
        
        //Metodo que actualiza los descuentos aplicables a una reserva segun la cantidad de entradas reservadas
        public void actualizarDescuentos(){
            //Por la compra de 3 o mas entradas se aplica un 5% de descuento
            if (this.listaReservas.size() >= 3){
                this.listaDescuentos.set(0, 5);
                //Por la compra de 6 o mas entradas se aplica un 10% de descuento
                if (this.listaReservas.size() >= 6){
                    this.listaDescuentos.set(1, 10);
                } else {
                    this.listaDescuentos.set(1,0);
                }
            } else {
                listaDescuentos.set(0,0);
            }
        }
        
        /*
        Metodo que anade una reserva a la lista de reservas actual y actualiza
        los descuentos segun la cantidad de entradas reservadas
        */
        public void anadirReserva(Reserva reserva){
            this.listaReservas.add(reserva);
            totalReservas += reserva.precioFinal;
            actualizarDescuentos();
        }
        
        //Metodo que imprime la informacion de todas las entradas en la reserva actual
        public void imprimirReservas(){
            if (!this.listaReservas.isEmpty()){
                System.out.println("Se imprime a continuacion el detalle de sus reservas:\n");
                for (Reserva reserva : this.listaReservas){
                    reserva.imprimirReserva();
                    //System.out.println(); //Salto de linea
                }
            } else {
                System.out.println("No hay entradas en reserva actualmente");
            }
        }
        
        /*
        Metodo que actualiza el estado de un asiento, ya sea cambiando su estado a
        desocupado o comprado
        */
        public void actualizarAsiento(String accion, String[][] mapa, String[][] mapaVentas, String asiento){
            int filas = mapa.length;
            int columnas  = mapa[0].length;
            for (int fila = 0; fila < filas; fila++){
                for (int columna = 0; columna < columnas; columna++){
                    if (mapa[fila][columna].equals(asiento)){
                        switch (accion){
                            case "desocupar" -> mapaVentas[fila][columna] = mapa[fila][columna];
                            case "comprar" -> mapaVentas[fila][columna] = "-C-";
                        }
                    }
                }
            }
        }
        
        /*
        Metodo que elimina una reserva usando su ID, si se encuentra dentro de la lista de reservas.
        Tambien actualiza el mapa de asientos para que el asiento reservado vuelva
        a estar disponible para reservar
        */
        public void eliminarReserva(Scanner scanner, String[][] mapa, String[][] mapaVentas){
            System.out.print("Ingrese el ID de la reserva que desea eliminar: ");
            String idAEliminar = scanner.nextLine();
            boolean reservaEncontrada = false;
            for (Reserva reserva : this.listaReservas){
                if (idAEliminar.equals(reserva.idReserva)){
                    //Se elimina la reserva de la lista de reservas
                    reservaEncontrada = true;
                    totalReservas -= reserva.precioFinal;
                    this.listaReservas.remove(reserva);
                    actualizarDescuentos();
                    System.out.println("Se ha eliminado la reserva con ID: " + idAEliminar);
                    //Se desocupa el asiento reservado
                    actualizarAsiento("desocupar", mapa, mapaVentas, reserva.asiento);
                    break;
                }
            }
            if (!reservaEncontrada){
                System.out.println("No se ha encontrado la reserva con ID: " + idAEliminar);
                System.out.println("Es posible que la reserva no exista o ya haya sido eliminada.");
            }
        }
        
        //Metodo que elimina una reserva de la lista de reservas usando su ID
        public void descartarReserva(String[][] mapa, String[][] mapaVentas){
            for (Reserva reserva : this.listaReservas){
                actualizarAsiento("desocupar", mapa, mapaVentas, reserva.asiento);
            }
            totalReservas = 0;
            this.listaReservas.clear();
            System.out.println("Se han descartado los asientos reservados");
        }
        
        //Metodo que completa la compra de un asiento reservado
        public void comprarReserva(String[][] mapa, String[][] mapaVentas){
            //Si el usuario no tiene entradas reservadas, se le informa
            if (this.listaReservas.isEmpty()){
                System.out.println("No tiene entradas reservadas.");
                System.out.println("No se ha realizado la compra.");
            } else {
                //Se crea una nueva compra
                Compra compra = new Compra(cantidadCompras + 1, this.listaReservas, totalReservas, this.listaDescuentos);
                listaCompras.add(compra);
                //Se actualizan las estadisticas del teatro
                totalCompras += compra.totalFinal;
                cantidadCompras++;
                entradasVendidas += compra.asientos.size();
                //Se actualiza el estado de cada asiento de reservado a comprado
                for (Reserva reserva : this.listaReservas){
                    actualizarAsiento("comprar", mapa, mapaVentas, reserva.asiento);
                }
                //Se reinicia la reserva actual
                totalReservas = 0;
                listaReservas.clear();
                //Mensaje de confirmacion
                System.out.println("Se ha completado la compra de las entradas reservadas.");
                System.out.println("Se imprime a continuacion la informacion de la compra: \n");
                compra.imprimirCompra(this.nombre);
            }
        }
        
        //Metodo que imprime los datos de todas las compras realizadas
        public void imprimirCompras(){
            if (this.listaCompras.isEmpty()){
                System.out.println("No hay compras para mostrar");
            } else {
                System.out.println("Se imprime a continuacion el detalle de sus compras:\n");
                for (Compra compra : this.listaCompras){
                    compra.imprimirCompra(this.nombre);
                    System.out.println();
                }
            }
        }
        
        //Metodo que anula una compra, reembolsando el dinero pagado y desocupando los asientos comprados
        public void anularCompra(Scanner scanner, String[][] mapa, String[][] mapaVentas){
            if (this.listaCompras.isEmpty()){
                System.out.println("No existen compras para anular");
            } else {
                imprimirCompras();
                System.out.print("Ingrese el ID de la compra que desea anular: ");
                String idAAnular = scanner.nextLine();
                boolean compraEncontrada = false;
                //Se itera sobre la compra para buscar la compra con el ID ingresado
                for (Compra compra : this.listaCompras){
                    if (String.valueOf(compra.idCompra).equals(idAAnular)){
                        compraEncontrada = true;
                        //Si se encuentra la compra, se desocupan los asientos comprados
                        for (String asiento : compra.asientos){
                            actualizarAsiento("desocupar", mapa, mapaVentas, asiento);
                        }
                        //Se actualizan las estadisticas globales del teatro
                        totalCompras -= compra.totalFinal;
                        entradasVendidas -= compra.asientos.size();
                        comprasAnuladas++;
                        //Mensaje de confirmacion
                        System.out.println("Se ha eliminado la compra con ID: " + idAAnular);
                        System.out.println("Se ha realizado el reembolso del valor pagado: $" + compra.totalFinal);
                        System.out.println("Se han desocupado los asientos comprados");
                        //Se elimina la compra de la entrada de compras
                        listaCompras.remove(compra);
                        break;
                    }
                }
                if (!compraEncontrada){
                    System.out.println("No se ha encontrado la compra con ID: " + idAAnular);
                    System.out.println("Puede que la compra no exista o que esta ya se haya anulado");
                }
            }
        }
        
        /*
        Metodo que imprime las estadisticas globales del proceso de compra
        (cantidad de entradas compradas y total pagado)
        */
        public void resumenCompras(){
            String separador = "-".repeat(32);
            System.out.printf("%s%n%s%n%s%n", separador, "Resumen de sus compras:", separador);
            int cantidadTotalCompras = cantidadCompras - comprasAnuladas;
            System.out.println("Cantidad de compras realizadas: " + cantidadTotalCompras);
            System.out.println("Cantidad de entradas compradas: " + entradasVendidas);
            System.out.println("Total pagado: $" + totalCompras);
            System.out.println(separador + "\n");
        }
        
    }
    
    /*
    Metodo que verifica si un String esta compuesto solo por numeros, letras,
    espacios y guiones y retorna true si es asi. De lo contrario retorna false
    */
    static boolean esAlfaNumerico(String texto){
        for (char letra : texto.toCharArray()){
            if (!Character.isLetterOrDigit(letra) && letra != '-' && letra != ' '){
                return false;
            }
        }
        return true;
    }
    
    //Metodo que retorna true si quedan asientos disponibles para reservar en el teatro y false si no
    static boolean hayAsientosDisponibles(String[][] mapa){
        for (String[] fila : mapa){
            for (String columna : fila){
                if (!columna.equals("-R-") || !columna.equals("-C-")){
                    return true;
                }
            }
        }
        return false;
    }
    
    //Metodo que imprime el mapa del teatro
    static void imprimirMapa(String[][] mapa){
        System.out.println("Se imprime a continuacion el mapa del teatro:\n");
        System.out.println("[       Escenario       ]");
        System.out.println("-".repeat(25));
        for (String[] fila : mapa) {
            for (String columna : fila) {
                System.out.printf("[%s]", columna);
            }
            System.out.println(); //Salto de linea
        }
        System.out.println();
        System.out.println("""
                           *Los asientos en reserva se representan como [-R-]
                           *Los asientos comprados se representan como [-C-]
                           
                           Sectores:
                           1. VIP (asientos V01 a V10)\tPrecio base: $30000
                           2. Platea (asientos P01 a P10)\tPrecio base: $15000
                           3. Balcon (asientos B01 a B10)\tPrecio base: $20000""");
        System.out.println();
    }
    
    /*
    Metodo que solicita al usuario el numero del asiento que desea reservar y retorna
    el numero de asiento y el sector de la entrada (VIP, Platea o Balcon) en un arreglo
    */
    static String[] solicitarUbicacion(String[][] mapaVentas, Scanner scanner){
        String ubicacion;
        String sector = "";
        imprimirMapa(mapaVentas);
        boolean ubicacionValida = false;
        int filas = mapaVentas.length;
        int columnas = mapaVentas[0].length;
        while(!ubicacionValida){
            System.out.print("Ingrese el numero de asiento que desea reservar (Por ejemplo: V03 o B10): ");
            ubicacion = scanner.nextLine().toUpperCase();
            for (int fila = 0; fila < filas; fila++){
                for (int columna = 0; columna < columnas; columna++) {
                    String asiento = mapaVentas[fila][columna];
                    if (asiento.equals(ubicacion) && !asiento.equals("-R-") && !asiento.equals("-C-")) {
                        ubicacionValida = true;
                        switch(ubicacion.charAt(0)){
                            case 'V' -> sector = "VIP";
                            case 'P' -> sector = "Platea";
                            case 'B' -> sector = "Balcon";
                        }
                        mapaVentas[fila][columna] = "-R-";
                        System.out.println("Ha reservado el asiento " + ubicacion + " en el sector: " + sector);
                        String[] arregloRetorno = {ubicacion, sector};
                        return arregloRetorno;
                    }
                }
            }
            System.out.println("No se ha podido reservar el asiento " + ubicacion);
            System.out.println("Es posible que se encuentre reservado, haya sido comprado o que no corresponda a un asiento valido. Reintente");
        }
        return null;
    }
    
    /*
    Metodo que registra el arreglo con los datos de los clientes para determinar
    si un cliente esta registrado (usando su ID). Si esta registrado, el metodo
    retorna el indice del arreglo en que se ubica el cliente. Si no, retorna -1
    */
    static int buscarCliente(String[][] clientes, String idCliente){
        int largoClientes = clientes.length;
        for (int i = 0; i < largoClientes; i++){
            if (idCliente.equals(clientes[i][0])){
                return i;
            }
        }
        return -1;
    }
    
    /*
    Metodo que anade el ID y edad de un nuevo cliente al arreglo de clientes.
    Para ello, crea un nuevo arreglo de largo igual al largo del arreglo de clientes
    original +1, anade al cliente y retorna el nuevo arreglo
    */
    static String[][] anadirCliente(String[][]clientes, String idCliente, String edad){
        if (clientes[0][0] == null){
            clientes[0][0] = idCliente;
            clientes[0][1] = edad;
            return clientes;
        } else {
            String[][] nuevoArreglo = new String[clientes.length + 1][2];
            System.arraycopy(clientes, 0, nuevoArreglo, 0, clientes.length);
            nuevoArreglo[nuevoArreglo.length - 1][0] = idCliente;
            nuevoArreglo[nuevoArreglo.length - 1][1] = edad;
            return nuevoArreglo;
        }
    }
    
    //Metodo que solicita al usuario su edad y la retorna solo si es un numero entero positivo mayor a 0
    static String solicitarEdad(Scanner scanner){
        boolean edadValida = false;
        int edadNumero;
        String edad = "";
        
        while (!edadValida){
            System.out.print("Ingrese su edad (debe ser un numero entero mayor a 0): ");
            edad = scanner.nextLine();
            try{
                edadNumero = Integer.parseInt(edad);
                if (edadNumero > 0){
                    edadValida = true;
                    return edad;
                } else {
                    System.out.println("La edad ingresada no es valida. Debe ser mayor a 0. Reintente");
                }
            } catch (NumberFormatException e) {
                System.out.println("La edad ingresada no corresponde a un numero entero. Reintente.");
            }
        }
        return edad;
    }
    
    //Metodo que solicita al usario ingresar su ID (nombre de usuario) y retorna un ID valido
    static String solicitarId(Scanner scanner){
        String idCliente;
        boolean idValido = false;
        
        while(!idValido){
            System.out.print("Ingrese su nombre de usuario (debe tener entre 4 y 20 caracteres y contener solo letras y numeros): ");
            idCliente = scanner.nextLine();
            if (idCliente.length() < 4 || idCliente.length() > 20 || !esAlfaNumerico(idCliente)){
                System.out.println("El nombre de usuario ingresado no es valido. Reintente.");
            } else {
                idValido = true;
                return idCliente;
            }
        }
        return null;
    }
    
    /*
    Metodo que calcula el costo base, descuento aplicado y costo final de la entrada
    segun la ubicacion y edad del usuario y los retorna dentro de un arreglo
    */
    static String[] costosYDescuento(String ubicacion, String edad, HashMap<String, Integer> tablaValores){
        String[] listaRetorno = new String[3];
        int numEdad, costoBase, descuento, costoFinal;
        
        //Se obtiene el precio base segun la ubicacion
        costoBase = tablaValores.get(ubicacion);
        //Se determina si el usuario tiene descuento segun su edad y se informa si es asi
        numEdad = Integer.parseInt(edad);
        if (numEdad <= 18){
            descuento = 10;
            System.out.println("Por su edad accede a un descuento del " + descuento + "% (Estudiante)");
        } else if (numEdad >= 60){
            descuento = 15;
            System.out.println("Por su edad accede a un descuento del " + descuento + "% (Tercera edad)");
        } else {
            descuento = 0;
        }
        
        //Se calcula el precio final
        costoFinal = costoBase * (100 - descuento) / 100;
        
        //Se añaden los costos y el descuento al arreglo y se retorna el arreglo
        listaRetorno[0] = String.valueOf(costoBase);
        listaRetorno[1] = String.valueOf(descuento);
        listaRetorno[2] = String.valueOf(costoFinal);
        return listaRetorno;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        int indiceCliente;
        String opcion, opcionSubmenu, idCliente, edad;
        boolean salirPrograma, opcionValida;
        String[] costosYDcto;
        String[][] mapaVentas;
        String separador = "-".repeat(32);
        Reserva reserva;
        
        Scanner scanner = new Scanner(System.in); //Se crea el objeto Scanner
        
        Teatro teatro = new Teatro("Teatro Moro"); //Se crea el objeto Teatro
        
        //Se crea el arreglo que contiene los asientos numerados del teatro
        String[][] mapa = {
            {"V01", "V02", "V03", "V04", "V05"},
        {"V06", "V07", "V08", "V09", "V10"},
        {"P01", "P02", "P03", "P04", "P05"},
        {"P06", "P07", "P08", "P09", "P10"},
        {"B01", "B02", "B03", "B04", "B05"},
        {"B06", "B07", "B08", "B09", "B10"}};
        
        /*
        Se crea una copia independiente del arreglo con los asientos del teatro
        que se usara para modificar el estado de los asientos segun vayan siendo
        reservados o comprados
        */
        mapaVentas = new String[mapa.length][mapa[0].length];
        for (int fila = 0; fila < mapa.length; fila++){
            for (int columna = 0; columna < mapa[fila].length; columna++){
                mapaVentas[fila][columna] = mapa[fila][columna];
            }
        }
        
        //Hashmap que guarda el costo base de una entrada para las diferentes ubicaciones
        HashMap<String, Integer> tablaValores = new HashMap<>();
        tablaValores.put("VIP", 30000);
        tablaValores.put("Platea", 15000);
        tablaValores.put("Balcon", 20000);
        
        /*
        Arreglo que contendra los datos de cada reserva (ID del cliente, edad del cliente,
        numero y ubicacion del asiento, precio base, descuento aplicado y precio final)
        */
        String[] datosReserva = new String[8]; 
        
        //ID de la venta, cada venta tiene un numero unico
        int idReserva = 1;
        
        /*
        Matriz que guardara el ID (nombre de usuario) y edad de cada cliente. Cada vez
        que se registre un cliente con un nuevo ID, se anadira a la matriz, 
        creando una nueva matriz con largo igual al de la matriz original + 1
        */
        String[][] clientes = new String[1][2];
        
        salirPrograma = false;
        while (!salirPrograma){
            System.out.println("Menu principal:\n");
            System.out.println("""
                               Que desea hacer?
                               1. Reservar entrada
                               2. Modificar reserva de entrada
                               3. Comprar entradas reservadas
                               4. Ver compras realizadas
                               5. Anular compra
                               6. Consultar promociones
                               7. Salir del programa""");
            System.out.print("Ingrese el numero correspondiente a la opcion deseada (1/2/3/4/5/6/7): ");
            opcion = scanner.nextLine();
            switch(opcion){
                //Reservar de entrada
                case "1":
                    System.out.println("Ha seleccionado la opcion: Reservar entrada\n");
                    if (hayAsientosDisponibles(mapaVentas)){
                        //Se reinicia el arreglo que contenia los datos de la reserva anterior
                        Arrays.fill(datosReserva, null);
                        //Se solicita la ubicacion de la entrada al usuario y 
                        //una vez obtenida se anade al arreglo con los datos de la venta
                        String[] infoUbicacion = solicitarUbicacion(mapaVentas, scanner);
                        datosReserva[0] = infoUbicacion[0]; //Numero de asiento
                        datosReserva[1] = infoUbicacion[1]; // Sector del asiento
                        idCliente = solicitarId(scanner);//Se solicita el ID del cliente
                        datosReserva[2] = idCliente; //ID del cliente
                        /*
                        Se verifica si el cliente ya esta registrado. Si es asi,
                        se usa la edad ya registrada en el arreglo 'clientes' para
                        la reserva de la entrada. Si el cliente es nuevo, se solicita
                        su edad y se registran su ID y edad en el arreglo
                        */
                        indiceCliente = buscarCliente(clientes, idCliente);
                        if (indiceCliente == -1){
                            System.out.println("El nombre de usuario ingresado no se encuentra en nuestros registros.");
                            System.out.println("Su nombre de usuario ha sido anadido a nuestros registros.");
                            edad = solicitarEdad(scanner);
                            clientes = anadirCliente(clientes, idCliente, edad);
                            System.out.println("Su edad ha sido anadida a nuestros registros.");
                        } else {
                            edad = clientes[indiceCliente][1];
                            System.out.println("Su nombre de usuario ya se encuentra registrado.");
                            System.out.println("De acuerdo a nuestros registros su edad es: " + edad);
                        }
                        datosReserva[3] = edad; //Edad del ciente
                        //Se calcula el precio base, descuento y precio final de la entrada
                        costosYDcto = costosYDescuento(datosReserva[1], datosReserva[3], tablaValores);
                        datosReserva[4] = costosYDcto[0]; //Costo base
                        datosReserva[5] = costosYDcto[1]; //Descuento aplicado
                        datosReserva[6] = costosYDcto[2]; //Costo final
                        datosReserva[7] = String.valueOf(idReserva); //ID de la venta
                        reserva = new Reserva(datosReserva); //Se crea una nueva reserva con los datos ingresados
                        idReserva++;
                        teatro.anadirReserva(reserva);
                    } else {
                        System.out.println("No hay asientos disponibles para reservar");
                    }
                    break;
                case "2":
                    //Modificar reserva de entradas
                    System.out.println("Ha seleccionado la opcion: Modificar reserva de entradas\n");
                    if (!teatro.listaReservas.isEmpty()){
                        teatro.imprimirReservas();
                        System.out.println(); //Salto de linea
                        System.out.println("""
                                           Que desea hacer?
                                           1. Eliminar una entrada reservada
                                           2. Volver al menu principal""");
                        opcionValida = false;
                        while (!opcionValida){
                            System.out.print("Ingrese el numero correspondiente a la opcion deseada (1/2): ");
                            opcionSubmenu = scanner.nextLine();
                            switch(opcionSubmenu){
                                case "1":
                                    opcionValida = true;
                                    System.out.println("Ha seleccionado la opcion: Eliminar una entrada reservada\n");
                                    teatro.eliminarReserva(scanner, mapa, mapaVentas);
                                    break;
                                case "2":
                                    opcionValida = true;
                                    System.out.println("Ha seleccionado la opcion: Volver al menu principal");
                                    break;
                                default:
                                    System.out.println("La opcion ingresada no es valida. Reintente");
                            }
                        }
                    } else {
                        System.out.println("No tiene entradas en reserva actualmente");
                    }
                    break;
                case "3":
                    //Comprar entradas reservadas
                    System.out.println("Ha seleccionado la opcion: Comprar entradas reservadas\n");
                    teatro.comprarReserva(mapa, mapaVentas);
                    break;
                case "4":
                    //Ver compras realizadas
                    System.out.println("Ha seleccionado la opcion: Ver compras realizadas\n");
                    teatro.imprimirCompras();
                    break;
                case "5":
                    //Anular compra
                    System.out.println("Ha seleccionado la opcion: Anular compra\n");
                    teatro.anularCompra(scanner, mapa, mapaVentas);
                    break;
                case "6":
                    //Consultar promociones
                    System.out.println("Consultar promociones\n");
                    System.out.println("""
                                       Las siguientes promociones se encuentran vigentes:
                                       1. Descuento del 10% para estudiantes (Edad: 18 o menos)
                                       2. Descuento del 15% para tercera edad (Edad: 60 o mas)
                                       3. Descuento de 5% al total por la compra de 3 o mas entradas*
                                       4. Descuento adicional de 10% al total por la compra de 6 o mas entradas*
                                       * Las promociones se aplican al completar la compra de las entradas reservadas""");
                    break;
                case "7":
                    //Salir del programa
                    System.out.println("Ha seleccionado la opcion: Salir del programa\n");
                    //Si el usuario tiene entradas reservadas sin comprar se le
                    //pregunta que quiere hacer con ellas
                    if (!teatro.listaReservas.isEmpty()){
                        System.out.println("""
                                           Tiene entradas en reserva sin comprar.
                                           Que desea hacer?
                                           1. Comprar entradas reservadas
                                           2. Descartar la reserva actual""");
                        opcionValida = false;
                        while (!opcionValida){
                            System.out.print("Ingrese el numero correspondiente a la opcion deseada (1/2): ");
                            opcionSubmenu = scanner.nextLine();
                            switch (opcionSubmenu){
                                case "1":
                                    //Comprar entradas reservadas
                                    System.out.println("Ha seleccionado la opcion: Comprar entradas reservadas\n");
                                    teatro.comprarReserva(mapa, mapaVentas);
                                    System.out.println(); //Salto de linea
                                    opcionValida = true;
                                    break;
                                case "2":
                                    //Descartar entradas reservadas
                                    System.out.println("Ha seleccionado la opcion: Descartar la reserva actual\n");
                                    teatro.descartarReserva(mapa, mapaVentas);
                                    opcionValida = true;
                                    break;
                                default:
                                    System.out.println("La opcion ingresada no es valida reintente");
                            }
                        }
                    }
                    //Se el resumen global de las compras
                    teatro.resumenCompras();
                    //Mensaje de despedida
                    System.out.println(separador + "\nHa salido del sistema de ventas\n" + separador);
                    System.out.println("Gracias por su compra");
                    salirPrograma = true;
                    break;
                default:
                    System.out.println("La opcion seleccionada no es valida. Reintente");
            }
            System.out.println("\n*****\n"); //Separador
        }
        scanner.close(); //Se cierra el objeto Scanner
    }
}
