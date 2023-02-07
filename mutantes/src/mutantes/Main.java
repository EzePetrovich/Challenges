package mutantes;

import mutantes.exceptions.InvalidDNAException;
import mutantes.services.MutantService;

public class Main {

    public static void main(String[] args) {
        
        String[] dna1 = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"}; // Prueba 1
        
        String[] dna2 = {"ATGCGACA", "CAGTGCTG", "TTATGTGG", "AGAAGACG", "CCACCCTC", "TCAAGCTG", "CAGTGCCG", "AGAAGACC"}; // Prueba 2
        
        String[] dna3 = {"AAAAAA", "AAGGGA", "GCTGTA", "GGCTGA", "GAGCTG", "GAAGCT"}; // Prueba 3
        
        String[] dna4 = {"ATGACC", "TCACCT", "TAACTG", "ACCTGC", "ATTACA", "GGTCAC"}; // Prueba 4
        
        MutantService ms = new MutantService();
        
        try {
            ms.processDna(dna2);
        } catch (InvalidDNAException ex) {
            System.err.println("ERROR: muestra de adn invalida.");
            System.out.println(ex.getMessage());
        }
        
    }
    
}
