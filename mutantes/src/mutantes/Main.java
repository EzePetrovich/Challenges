package mutantes;

import mutantes.services.MutantService;

public class Main {

    public static void main(String[] args) throws Exception {
        
        String[] dna1 = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"}; // Prueba 1
        
        String[] dna2 = {"ATGCGACA", "CAGTGCTG", "TTATGTGG", "AGAAGACG", "CCACCCTA", "TCAAGCTG", "CAGTGCCG", "AGAAGACC"}; // Prueba 2
        
        String[] dna3 = {"AAAAAA", "AAGGGA", "GCTGTA", "GGCTGA", "GAGCTG", "GAAGCT"}; // Prueba 3
        
        String[] dna4 = {"ATGACC", "TCACCT", "TAACTG", "ACCTGC", "ATTACA", "GGTCAC"}; // Prueba 4
        
        MutantService ms = new MutantService();
        
        ms.processDna(dna2);
        
    }
    
}
