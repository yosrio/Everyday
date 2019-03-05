/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.List;
import model.Document;
import model.InvertedIndex;
import model.Posting;
import model.Term;

/**
 *
 * @author admin
 */
public class testTFIDF5 {

    public static void main(String[] args) {
//        Document doc1 = new Document(1, "Shipment of gold damaged in a fire");
//        Document doc2 = new Document(2, "delivery of silver arrived in a silver truck");
//        Document doc3 = new Document(3, "shipment of gold arrived in a truck");
        Document doc1 = new Document(1,"Saya pergi memancing");
        Document doc2 = new Document(2,"Pemancingan itu sangat ramai");
        Document doc3 = new Document(3,"Saya dapat ikan di pemancingan itu");
        Document doc4 = new Document(4,"Memancing itu hobi adik saya");

        InvertedIndex index = new InvertedIndex();
        index.addNewDocument(doc1);
        index.addNewDocument(doc2);
        index.addNewDocument(doc3);
        index.addNewDocument(doc4);

        index.makeDictionaryWithTermNumber();
        ArrayList<Term> result = index.getDictionary();

        for (int i = 0; i < result.size(); i++) {
            System.out.println("====================================");
            System.out.println("Term = " + result.get(i).getTerm() + ", numberofDocument = "
                    + result.get(i).getNumberOfDocument());
            System.out.println("------------------------------------");
            ArrayList<Posting> tempPosting = result.get(i).getPostingList();
            for (int j = 0; j < tempPosting.size(); j++) {
                System.out.println("id_doc = " + tempPosting.get(j).getDocument().getId()
                        + ", numberofTerm = " + tempPosting.get(j).getNumberOfTerm());
            }
        }
        System.out.println("====================================");
        // number of document
        String tempString = "saya";
        int result2 = index.getDocFreq(tempString);
        System.out.println("Number of Doc with " + tempString + " is " + result2);
        double result3 = index.getInverseDocFreq(tempString);
        System.out.println("IDF of " + tempString + " is " + result3);
        
//        // number of document
//        String tempString = "damaged";
//        int result2 = index.getDocFreq(tempString);
//        System.out.println("Number of Doc with " + tempString + " is " + result2);
//        double result3 = index.getInverseDocFreq(tempString);
//        System.out.println("IDF of " + tempString + " is " + result3);
    }
}
