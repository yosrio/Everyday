/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.io.IOException;
import model.Document;

/**
 *
 * @author yosrio
 */
public class tesFileDokumen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Document doc = new Document();
        File file = new File("F:\\smstr 6\\PemrolehanInformasi\\DocumentRetrieval\\dokumen\\dokumen1.txt");
        
        doc.readFile(1, file);
        System.out.println("Isi Dokumen:");
        System.out.println(doc.getContent());
    }

}
