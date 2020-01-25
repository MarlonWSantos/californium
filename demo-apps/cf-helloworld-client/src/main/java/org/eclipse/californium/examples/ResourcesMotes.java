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
import java.lang.reflect.Array;


public class ResourcesMotes{

  private String ip;
  private ArrayList<String> listResources;
  private List<String> listCoapIPs;
  private String[][] moteResource;

  
    //Armazena os IPs coap para busca de seus recursos  
  public void setIPs(List<String> listIPs){
	
	  //Cria lista para os IPs
	listCoapIPs = new ArrayList<>();
		
		  
	for(int i=0;i<listIPs.size();i++) {	  
		//Importa os IPs descobertos da rede
	  ip = listIPs.get(i);
	  
	    //Muda o prefixo fe80 do IP para aaaa e armazena
	  listCoapIPs.add(ip.replace("fe80", "coap://[aaaa").replace("\n","]"));
	}	  

  }
  
  
    //Exibe os Coap IPs dos motes
  public void showCoapIPs(){

    System.out.println("\nCoap IPs\n");

    int i=0;
      //Exibe enquanto houver IPs na lista
    while(i<listCoapIPs.size()){
      System.out.print(listCoapIPs.get(i)+"\n");
      i++;
    }
  }
  
  
    //Retorna a URL Well-kwown/core dos motes
  public String[] URLWellKnownCore() {
	  
	String[] listIPs = new String[listCoapIPs.size()];
	
	for(int i=0;i<listCoapIPs.size();i++) {
		//Anexa o sufixo URL Well-known/core a cada IP Coap
	  listIPs[i] = listCoapIPs.get(i)+":5683/.well-known/core";
	}
	  return listIPs;
  }


    //Armazena todas os IPs Coaps e seus respectivos recursos
  public void setAllResources(String[] listRes){
	  
	  
	listResources = new ArrayList<String>();
	
	ArrayList<String> resources = new ArrayList<String>();
	
	moteResource = new String[listRes.length][];
	
		
	  //Repete de acordo com o número de IPs Coaps existentes      
	for(int i=0;i<listCoapIPs.size();i++){
		
		  //Adiciona o IP Coap 
		listResources.add("\n"+getCoapIP(i));
						  	
	    //Define o padrão das tags dos recursos na mensagem
      Pattern res = Pattern.compile("</(.*?)>;");
	  
        //Busca dentro da mensagem o padrão dos recursos definido
	  Matcher matcherRes = res.matcher(listRes[i]);

	    //Enquanto encontrar padrões do recursos,adiciona na lista temporária
	  while (matcherRes.find()) {
	    resources.add(matcherRes.group().replace("<","").replace(">;",""));
	  }
	  
	    //Converte para array String a lista com os padrões de recursos e os armazena-os
	  moteResource[i] = resources.toArray(new String[resources.size()]);
	  
	 /*   
	  for(int j=0;j<resources.size();j++) {
		  System.out.println("lista String: "+moteResource[i][j]);
	  }*/
	  
	    //Adiciona os recursos da lista temporária para a lista definitava
	  listResources.addAll(resources);
	    //Lima a lista temporária
	  resources.clear();
	}
  } 
  
  
    //Mostra os recursos por mote
  public void showMoteResource(int mote) {
	  
	  System.out.println("\nRecursos do Mote\n");
	  
	    //Exibe o IP do mote
	  System.out.println(getCoapIP(mote));
	  
	  //Exibe os recursos do mote especificado
	for(int j=0;j<moteResource[mote].length;j++) {
	  System.out.println(moteResource[mote][j]);	
	}  
  }
  
  
    //Exibe todos os recursos dos motes
  public void showAllResources(){

    System.out.println("\nRecursos disponíveis\n");
    
      //Exibe os recursos de acordo com o tamanho da lista
    for(int i=0;i<listResources.size();i++) {
    	System.out.println(listResources.get(i));
    }        
  }  


    //Retorna o Coap IP do mote
  public String getCoapIP(int i){
    return listCoapIPs.get(i);
  }

 
  
}
