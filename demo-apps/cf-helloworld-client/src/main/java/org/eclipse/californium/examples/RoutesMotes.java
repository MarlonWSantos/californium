package org.eclipse.californium.examples;


/*     Routes Motes - It filter and store the routes and IPS of the motes
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
import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class RoutesMotes{

  private String response;
  private String[] ip;
  private String[] route;
  private List<String> listRoutes;
  private List<String> listIPs;


    //Armazena as informações dos IPs e das rotas dos motes
  public void setResponse(String args){
    response = args;
  }


    //Filtra e elimina os ruídos das informações dos IPs e das Rotas
  public void filterResponse(){

      //Define o padrão de rotas
    Pattern route = Pattern.compile("aaaa:.*s");

      //Define o padrão de IPs
    Pattern ip = Pattern.compile("fe80:.*:.*:.*:[0-9]\n");

      //Busca dentra da mensagem o padrão da rota definido
    Matcher matcherRoute = route.matcher(response);

      //Busca dentro da mensagem o padrão de IPs definido
    Matcher matcherIP = ip.matcher(response);

      //Cria lista para armazenar as rotas
    listRoutes = new ArrayList<>();

      //Cria lista para armazenar os IPs
    listIPs = new ArrayList<>();

      //Enquanto encontrar padrões da rota,adiciona na lista
    while (matcherRoute.find()) {
        listRoutes.add(matcherRoute.group());
    }

      //Enquanto encontrar padrões de IP,adiciona na lista
    while(matcherIP.find()){
      listIPs.add(matcherIP.group());
    }
  }


    //Exiba as rotas armazenadas
  public void showRotes(){

    System.out.println("\nRotas\n");

    int i=0;
      //Exibe enquanto houver rotas na lista
    while(i<listRoutes.size()){
      System.out.println(listRoutes.get(i));
      i++;
    }
  }


    //Exibe os IPs armazenados
  public void showIPs(){

    System.out.println("\nIPs\n");

    int i=0;
      //Exibe enquanto houver IPs na lista
    while(i<listIPs.size()){
      System.out.print(listIPs.get(i));
      i++;
    }
  }


    //Retorna o IP do mote
  public String getIP(int i){
    return listIPs.get(i);
  }


    //Retorna a rota do mote
  public String getRoute(int i){
    return listRoutes.get(i);
  }
  
    //Retorna o número de IPs na lista
  public int sizeListIPs() {
    return listIPs.size();	  
	  
  }
  
    //Retorna o número de rotas na lista
  public int sizeListRoutes() {
    return listRoutes.size();
  }
  
    //Retorna a lista de IPs
  public List<String> getListIPs(){
	  return listIPs;
  }

}
