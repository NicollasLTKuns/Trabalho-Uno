package Uno;

import javax.swing.*;
import java.awt.*;

public class Main {

	public static void main(String[] args) {
		Baralho baralho = new Baralho();

		System.out.println("ANTES:");
		baralho.mostrarBaralho();

		baralho.embaralhar();

		System.out.println("DEPOIS:");
		baralho.mostrarBaralho();
	
	}

}