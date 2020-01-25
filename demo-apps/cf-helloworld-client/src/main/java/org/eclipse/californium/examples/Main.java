package org.eclipse.californium.examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
	
	
  public static int menu() {
		
    Scanner keyboard;
	keyboard = new Scanner ( System.in );
		
	System.out.println("\nObserver ---------- [1]");
	System.out.println("Get --------------- [2]");
	System.out.print("Informe um valor: ");
		
		return keyboard.nextInt();
  }
	
  public static void errorMsg() {
    System.out.println("Faltando uma URL");
	System.out.println("Modo de usar:");
	System.out.println("CoapObserver.jar + [URL]:5683");
  }
	/*
  public static void Functions(int opcao){
		
	switch (opcao) {
	  case 1: 
	    Observer.Observer(ResourcesMotes.GroupCoap());
		break;
	  case 2:
		GETClient.Get(url[0]);
		break;
	  default:
	    System.out.println("Número inválido");
		break;			
	}
  }*/

	
  public static void main(String[] args) throws IOException {
    		
	  //Usuário insere URL Border Router
	String url = "http://[aaaa::c30c:0:0:1]";
	  
	    
    WgetJava obj = new WgetJava();
    
      //Armazena  URL do Border Router
    obj.setUrl(url);
    
      //Faz o pedido ao Border Router da informação e armazena
    obj.sendGET();
    
      //Exibe a informação dado pelo Border Router
    System.out.println(obj.getResponse());
    
    
    RoutesMotes routes = new RoutesMotes();
    
      //Armazena as informações das Rotas e IP
    routes.setResponse(obj.getResponse());
    
      //Filtra e separa os IPs das Rotas
    routes.filterResponse();
    
      //Exibe os IPs da rede
    routes.showIPs();
    
       //Exibe as Rotas da rede
    routes.showRotes();
    
    
    ResourcesMotes res = new ResourcesMotes();
    
      //Armazena os IPs no formato COAP
    res.setIPs(routes.getListIPs());
    
      //Exibe os IPs no formato COAP
    res.showCoapIPs();
    
    
    GETClient client = new GETClient();
  
       //Solicita e armazena os recursos dos motes
     res.setAllResources(client.discover(res.URLWellKnownCore()));
     
       //Exibe todos os recursos de todos os motes
    res.showAllResources();
    
      //Exibe os recursos do mote defindo pelo index
    res.showMoteResource(1);
     
          
  }
}







