import java.net.*;
import java.io.*;

public class UF3ACT4 {
	
	public static void main (String[] args) throws Exception {
		
		//FLUX PER A ENTRADA ESTÀNDARD
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		//Socket client
		DatagramSocket clientSocket = new DatagramSocket();
		byte[] enviats = new byte[1024];
		byte[] rebuts = new byte[1024];		
		
		//DADES DEL SERVIDOR al qual s'envia el missatge
		InetAddress IPServidor = InetAddress.getLocalHost();
		int port = 9800;
		boolean tempsNoSuperat = true;
		
		while (tempsNoSuperat == true) {
			
			//INTRODUIR DADES PEL TECLAT
			System.out.print("Introdueix missatge: ");
			String cadena = in.readLine();
			enviats = cadena.getBytes();
			
			//ENVIANT DATAGRAMA AL SERVIDOR
			System.out.println("Enviant "+enviats.length+"bytes al servidor.");
			DatagramPacket enviament = new DatagramPacket(enviats, enviats.length, IPServidor, port);
			clientSocket.send(enviament);	
			
			//REBENT DATAGRAMA DEL SERVIDOR
			DatagramPacket rebut = new DatagramPacket(rebuts, rebuts.length);
			System.out.println("Esperant datagrama...");
			
			// Comprobem que el temps no es superi el temps indicat
			clientSocket.setSoTimeout(5000);
			try{
			 //espera de les dades
				clientSocket.receive(rebut);
				//processament de les dades rebudes i obtenció de la resposta
			 }catch(SocketTimeoutException e){
				 // Si es supera el temps d'espera es mostra el seguent missatge i es cambia el valor
				 // de tempsNoSuperat a false per finalitzar el programa
				 System.out.println("Temps d'espera superat, programa finalitzat");
				 tempsNoSuperat = false;
			 }
			
			if (tempsNoSuperat == true) {
				
				String majuscula = new String(rebut.getData());
				
				//ACONSEGUINT INFORMACIÓ DEL DATAGRAMA
				InetAddress IPOrigen = rebut.getAddress();
				int portOrigen = rebut.getPort();
				System.out.println("\tProcedent de: "+IPOrigen+":"+portOrigen);
				System.out.println("\tDades: "+majuscula.trim());
			}
			
			
			if (tempsNoSuperat == false) {
				//Tanca el socket
				clientSocket.close();	
			}
				
		}
		
	}

}