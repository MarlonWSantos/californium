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

  private static String response;
  private static String[] ip;
  private static String[] route;
  private static List<String> ListRoutes;
  private static List<String> ListIPs;
  private static int i;


    //Armazena as informações dos IPs e das rotas dos motes
  public static void SetResponse(String args){
    response = args;
  }


    //Filtra e elimina os ruídos das informações dos IPs e das Rotas
  public static void FilterResponse(){

      //Define o padrão de rotas
    Pattern route = Pattern.compile("aaaa:.*s");

      //Define o padrão de IPs
    Pattern ip = Pattern.compile("fe80:.*:.*:.*:[0-9]\n");

      //Busca dentra da mensagem o padrão da rota definido
    Matcher matcherRoute = route.matcher(response);

      //Busca dentro da mensagem o padrão de IPs definido
    Matcher matcherIP = ip.matcher(response);

      //Cria lista para armazenar as rotas
    ListRoutes = new ArrayList<>();

      //Cria lista para armazenar os IPs
    ListIPs = new ArrayList<>();

      //Enquanto encontrar padrões da rota,adiciona na lista
    while (matcherRoute.find()) {
        ListRoutes.add(matcherRoute.group());
    }

      //Enquanto encontrar padrões de IP,adiciona na lista
    while(matcherIP.find()){
      ListIPs.add(matcherIP.group());
    }
  }


    //Exiba as rotas armazenadas
  public static void ShowRotes(){

    System.out.println("\nRotas\n");

      //Exibe enquanto houver rotas na lista
    while(i<ListRoutes.size()){
      System.out.println(ListRoutes.get(i));
      i++;
    }
    i=0;
  }


    //Exibe os IPs armazenados
  public static void ShowIPs(){

    System.out.println("\nIPs\n");

      //Exibe enquanto houver IPs na lista
    while(i<ListIPs.size()){
      System.out.print(ListIPs.get(i));
      i++;
    }
    i=0;
  }


    //Retorna o IP do mote
  public static String GetIP(int i){
    return ListIPs.get(i);
  }


    //Retorna a rota do mote
  public static String GetRoute(int i){
    return ListRoutes.get(i);
  }

}
