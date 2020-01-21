package org.eclipse.californium.examples;


/*     Resources Motes - It store the resources of each mote
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

import java.util.regex.*;

import javax.management.StringValueExp;
import javax.swing.text.StringContent;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.io.*;


public class ResourcesMotes{

  private static String resource;
  private static String ip;
  private static String response;
  private static List<String> ListResources;
  private static List<String> ListCoapIPs;

  
    //Armazena os IPs coap para busca de seus recursos  
  public static void SetIPs(){
	
	  //Cria lista para os IPs
	ListCoapIPs = new ArrayList<>();
		  
	for(int i=0;i<RoutesMotes.SizeListIPs();i++) {	  
		//Importa os IPs descobertos da rede
	  ip = RoutesMotes.GetIP(i);
	    //Muda o prefixo fe80 do IP para aaaa e armazena
	  ListCoapIPs.add(ip.replace("fe80", "coap://[aaaa").replace("\n","]"));
	    //Adiciona uma linha para melhor visualizão do IPs	  
	  ListCoapIPs.add("\n");
	}
  }
  
  public static void SetResources(){
	
	  //Lista para os recursos
	ListResources = new ArrayList<>();

	for(int i=0;i<ListCoapIPs.size();i+=2){
		
		  //Adiciona o IP Coap na lista antes de seus recursos
		ListResources.add("\n"+GetCoapIP(i));
		
	    //Faz uma consulta coap dos recursos através do URL COAP
      response = GETClient.Discover(GetWellKnownCore(i));
		
	    //Define o padrão das tags dos recursos na mensagem
      Pattern Res = Pattern.compile("</(.*?)>;");
	  
        //Busca dentro da mensagem o padrão dos recursos definido
	  Matcher matcherRes = Res.matcher(response);

	    //Enquanto encontrar padrões do recursos,adiciona na lista
	  while (matcherRes.find()) {
	    ListResources.add(matcherRes.group());
	  }
	}
  }
  
 
    //Exiba os recursos dos motes
  public static void ShowResources(){

    System.out.println("\nRecursos disponíveis\n");
    int i=0;
      //Exibe enquanto houver recursos na lista
    while(i<ListResources.size()){
      System.out.println(ListResources.get(i));
      i++;
    }
  }
  
    //Exibe os Coap IPs dos motes
  public static void ShowCoapIPs(){

    System.out.println("\nCoap IPs\n");

    int i=0;
      //Exibe enquanto houver IPs na lista
    while(i<ListCoapIPs.size()){
      System.out.print(ListCoapIPs.get(i));
      i++;
    }
  }

    //Retorna o Coap IP do mote
  public static String GetCoapIP(int i){
    return ListCoapIPs.get(i);
  }


    //Retorna o recurso do mote
  public static String GetResource(int i){
    return ListResources.get(i);
  }
  
    //Retorna a lista inteira de IPs COAP
  public static String[] GroupCoap(){
	    //Coverte ListString para array de String
	  String[] GroupIPs = ListCoapIPs.toArray(new String[0]);
	  return GroupIPs;
  }
  
  public static String GetWellKnownCore(int i) {
	return GetCoapIP(i)+":5683/.well-known/core/";  
  }

}
