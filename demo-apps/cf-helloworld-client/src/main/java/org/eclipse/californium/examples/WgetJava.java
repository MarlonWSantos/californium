package org.eclipse.californium.examples;


/*     Wget Java is a non-interactive network downloader in Java
*      Copyright (c) 2020 Marlon W. Santos <marlon.santos.santos@icen.ufpa.br>
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*
*/


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WgetJava{
    
	private static String GET_URL;

	
	  //Armazena a URL do Border Router para buscar os IPs dos motes
	public static void SetUrl(String args) {
	  GET_URL = args;		
	}
	
	
      //Faz pedido ao Border Router pelo IPs da rede
	public static void sendGET() throws IOException {

      try{

          //Cria um objeto com a URL do Border Router
		URL obj = new URL(GET_URL);

          //Abre uma conexão HTTP com a URL indicada
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

          //Gera uma Exception se não houver conexão ou resposta do servidor
        con.setConnectTimeout(2000); //2segundos

          //Envia o pedido ao servidor
		con.setRequestMethod("GET");

          //Recebe e armazena o código de resposta dada pelo servidor
		int responseCode = con.getResponseCode();

          //Exibe o código de resposta recebida
        System.out.println("GET Response Code :: " + responseCode);


          //Se o código recebido for igual a HTTP_OK(200)
		if (responseCode == HttpURLConnection.HTTP_OK) { 

            //Cria buffer para armazenar a mensagem enviada pelo servidor com os IPs dos motes
		  BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		  String inputLine;

            //Cria buffer de String que vai receber o conteúdo do buffer
		  StringBuffer response = new StringBuffer();

            //Enquanto linha do buffer for diferente de null,guarda uma linha da mensagem
		  while ((inputLine = in.readLine()) != null) {

              //Adiciona ao buffer de String o conteúdo da linha da mensagem
			response.append(inputLine);
            response.append("\n");
		  }

            //Finaliza o buffer que recebeu a mensagem
		  in.close();

		    //Envia a mensagem para extrair as rotas e IPs  
		  RoutesMotes.SetResponse(response.toString());

        //Se o código recebido for diferente de HTTP_OK(200) exibe mensagem de erro
      }else{
			  System.out.println("Error 404: Not Found");
		  }

    }catch(Exception e){
      System.out.println("Servidor não responde ou demora no envio da resposta");
    }
  }

}
