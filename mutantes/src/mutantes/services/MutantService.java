package mutantes.services;

import java.io.IOException;
import java.util.Objects;

public class MutantService {
    
    private final String NBL = "ATCG"; // Nitrogenous base letters
    private String dnaToString; // Toma el array y lo convierte en cadena para luego crear la matriz, si la muestra es valida.
    private Integer dim; // Si la muestra puede ser representada como matriz NxN, 'dim' toma un valor (línea 51).
    private Integer coincidences; // Cuenta las coincidencias de caracteres presentes en la matriz
    
    public MutantService() {
        dnaToString = "";
        coincidences = 0;
        dim = 0;
    }
    
    /*------------------ Lógica resolución del problema --------------------*/
    
    /*
        Toma la muestra (dna) y la valida (Que sus caracteres correspondan al de la base nitrogenada y
        que pueda ser representada en una matriz cuadrada (NxN))
    */ 
    
    private Boolean validateDna(String[] dna) {
        
        Boolean isOk = Boolean.TRUE;
        Integer inf, sup, dimDna = 0;
        
        for(String seqDna: dna) {
            
            inf = 0; 
            sup = 1;
            dimDna += seqDna.length();
            
            do {
                isOk = NBL.contains(seqDna.substring(inf, sup));
                dnaToString = dnaToString.concat(seqDna.substring(inf, sup));
                inf++;
                sup++;
            } while(sup <= seqDna.length() && isOk);
            
            if(!isOk) {break;}
            
        }
        
        if(isOk) {
            Double exactSqrt = Math.sqrt(dimDna);
            isOk = exactSqrt == Math.round(exactSqrt);
            if(isOk) {dim = (int) Math.round(exactSqrt);}
        }
        
        return isOk;
        
    }
    
    /*--------- RECORRE DIAGONALES PRINCIPALES ---------*/
    private void allDiagsPrin(String[][] matrix) {
        
        String ant = "", act = "";
        Integer i = 0, j = 0, equalsCont = 0;
	
        // RECORRIDO PRINCIPAL NATURAL
        
	for(i = 0; i < dim; i++) {
            for(j = 0; j < dim; j++) {
		if(Objects.equals(i, j)) {
                    act = matrix[i][j];
                    if(act.equals(ant)) {
                        equalsCont++;
                        if(equalsCont > 2) {
                            coincidences++;
                            equalsCont = 0;
                        }
                    }
                    else {equalsCont = 0;}
                    ant = act;
		}
            }
	}
	
	// RECORRIDO PRINCIPAL UPPER
	
	Integer cont = 1;
        
	do {
            ant = "";
            act = "";
            for(i = 0; i < dim - cont; i++) {
		for(j = cont; j < dim; j++) {
                    if(Objects.equals(i, (j - cont))) {
                        act = matrix[i][j];
                        if(act.equals(ant)) {
                            equalsCont++;
                            if(equalsCont > 2) {
                                coincidences++;
                                equalsCont = 0;
                            }
                        }
                        else {equalsCont = 0;}
                        ant = act;
                    }
		}
            }
            cont++;
	} while((int) (cont + (dim / 2)) <= (dim - 1));
        
	// RECORRIDO PRINCIPAL LOWER
        
        cont = 1;
        
	do {
            ant = "";
            act = "";
            for(i = cont; i < dim; i++) {	
		for(j = 0; j < dim - cont; j++) {
                    if(Objects.equals((i - cont), j)) {
			act = matrix[i][j];
                        if(act.equals(ant)) {
                            equalsCont++;
                            if(equalsCont > 2) {
                                coincidences++;
                                equalsCont = 0;
                            }
                        }
                        else {equalsCont = 0;}
                        ant = act;
                    }
		}
            }
            cont++;
	} while((int) (cont + (dim / 2)) <= (dim - 1));
        
    }
    
    /*--------- RECORRE DIAGONALES SECUNDARIAS ---------*/
    private void allDiagsSec(String[][] matrix) {
        
        String ant = "", act = "";
        Integer i = 0, j = 0, equalsCont = 0;
        
        // RECORRIDO SECUNDARIO NATURAL
        
        for(i = 0; i < dim; i++) {
            for(j = 0; j < dim; j++) {
		if(Objects.equals((i + j), (dim - 1))) {
                    act = matrix[i][j];
                    if(act.equals(ant)) {
                        equalsCont++;
                        if(equalsCont > 2) {
                            coincidences++;
                            equalsCont = 0;
                        }
                    }
                    else {equalsCont = 0;}
                    ant = act;
		}
            }
	}
        
        // RECORRIDO SECUNDARIO UPPER
	
	Integer cont = 1;
        
	do {
            ant = "";
            act = "";
            for(i = 0; i < dim - cont; i++) {
		for(j = 0; j < dim - cont; j++) {
                    if(Objects.equals((i + j), (dim - (cont + 1)))) {
                        act = matrix[i][j];
                        if(act.equals(ant)) {
                            equalsCont++;
                            if(equalsCont > 2) {
                                coincidences++;
                                equalsCont = 0;
                            }
                        }
                        else {equalsCont = 0;}
                        ant = act;
                    }
		}
            }
            cont++;
	} while((int) (cont + (dim / 2)) <= (dim - 1));
        
        // RECORRIDO SECUNDARIO LOWER
        
        cont = 1;
        
	do {
            ant = "";
            act = "";
            for(i = cont; i < dim; i++) {	
		for(j = cont; j < dim; j++) {
                    if(Objects.equals((i + j), ((dim - 1) + cont))) {
			act = matrix[i][j];
                        if(act.equals(ant)) {
                            equalsCont++;
                            if(equalsCont > 2) {
                                coincidences++;
                                equalsCont = 0;
                            }
                        }
                        else {equalsCont = 0;}
                        ant = act;
                    }
		}
            }
            cont++;
	} while((int) (cont + (dim / 2)) <= (dim - 1));
        
    }
    
    /*--------- RECORRE FILAS ---------*/
    
    private void allRows(String[][] matrix) {
        
        String ant = "", act = "";
        Integer equalsCont = 0;
        
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                act = matrix[i][j];
                if(act.equals(ant)) {
                    equalsCont++;
                    if(equalsCont > 2) {
                        coincidences++;
                        equalsCont = 0;
                    }
                }
                else {equalsCont = 0;}
                ant = act;
            }
            ant = "";
            act = "";
        }
    }
    
    /*--------- RECORRE COLUMNAS ---------*/
    
    private void allColumns(String[][] matrix) {
        
        String ant = "", act = "";
        Integer equalsCont = 0;
        
        for(int j = 0; j < dim; j++) {
            for(int i = 0; i < dim; i++) {
                act = matrix[i][j];
                if(act.equals(ant)) {
                    equalsCont++;
                    if(equalsCont > 2) {
                        coincidences++;
                        equalsCont = 0;
                    }
                }
                else {equalsCont = 0;}
                ant = act;
            }
            ant = "";
            act = "";
        }
        
    }
    
    /*--------- INDICA SI EL SUJETO ES MUTANTE O NO ---------*/
    private Boolean isMutant(String[] dna) throws Exception {
        
        if(!validateDna(dna)) {throw new Exception("ERROR: el adn no es valido.");}
        
        String[][] dnaMatrix = vectToMatrix(dna);
        
        allDiagsPrin(dnaMatrix);
        allDiagsSec(dnaMatrix);
        allRows(dnaMatrix);
        allColumns(dnaMatrix);
        
        return coincidences > 2;
    }
    
    /* 
        Toma la muestra (dna) y la procesa, indicando si el sujeto es mutante o no. Luego imprime
        por pantalla la muestra de adn con formato de matriz.
    */
    public void processDna(String[] dna) throws Exception {
        if(isMutant(dna)) {
            System.err.println("El individuo es un mutante.\n");
            System.out.println("\nSe encontraron " + coincidences + " coincidencias.");
        }
        else {System.out.println("El individuo es un ser humano.\n");}
        printMatrix(vectToMatrix(dna));
    }
    
    /*------------------ Herramientas y formato --------------------*/
    
    
    // Toma una matriz y la imprime por pantalla
    private void printMatrix(String[][] dnaMatrix) {
        pressIntro();
        System.out.println();
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                System.out.print(dnaMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    // Toma un vector, lo transforma en matriz y lo retorna
    private String[][] vectToMatrix(String[] vect) {
        String[][] matrix = new String[dim][dim];
        Integer inf = 0, sup = 1;
        for(int i = 0; i < dim; i++) {
                for(int j = 0; j < dim; j++) {
                    matrix[i][j] = dnaToString.substring(inf, sup);
                    inf++; 
                    sup++;
                }
            }
        return matrix;
    }
    
    // Espera que el usuario presione intro para seguir la ejecución del programa
    private static void pressIntro() {
        System.out.print("\nPresione intro para ver el adn...");
        try{System.in.read();}
        catch(IOException e) {}
    }
    
}
