package org.eclipse.californium.examples;

import java.io.IOException;
import java.util.Scanner;

public class Main{
	
  public static int Menu() {
		
    Scanner keyboard;
	keyboard = new Scanner ( System.in );
		
	System.out.println("\nObserver ---------- [1]");
	System.out.println("Get --------------- [2]");
	System.out.print("Informe um valor: ");
		
		return keyboard.nextInt();
  }
	
  public static void ErrorMsg() {
    System.out.println("Faltando uma URL");
	System.out.println("Modo de usar:");
	System.out.println("CoapObserver.jar + [URL]:5683");
  }
	/*
  public static void Functions(int opcao,String url){
		
	switch (opcao) {
	  case 1: 
	    Observer.Observer(url);
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
		
	int opcao;
		
	if (args.length==0) {
			
	  String url = "http://[aaaa::c30c:0:0:1]";
		
	  WgetJava.SetUrl(url);
	  WgetJava.sendGET();
	  RoutesMotes.FilterResponse();
	  RoutesMotes.ShowIPs();
	  RoutesMotes.ShowRotes();
			
	  //opcao=Menu();

	  //Functions(opcao,url);
	
    }else{ErrorMsg();}
  }
}