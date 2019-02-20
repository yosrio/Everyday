/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author admin
 */
public class InvertedIndex {
    private ArrayList<Document> listOfDocument = new ArrayList<Document>();
    private ArrayList<Term> listOfTerm = new ArrayList<Term>();
    
    public InvertedIndex() {
        
    }
    
    public void addNewDocument(Document document){
        listOfDocument.add(document);
    }
    
    public ArrayList<Posting> getUnsortedPostingList(){
        ArrayList<Posting> list = new ArrayList<Posting>();
        
        for (int i = 0; i < listOfDocument.size(); i++) {
            String[] termResult = listOfDocument.get(i).getListofTerm();
            for (int j = 0; j < termResult.length; j++) {
                Posting tempPosting = new Posting(termResult[j],listOfDocument.get(i));
                   list.add(tempPosting);
            }
        }
        return list;
    }
    
    public ArrayList<Posting> getSortedPostingList(){
        ArrayList<Posting> list = new ArrayList<Posting>();
        
        for (int i = 0; i < listOfDocument.size(); i++) {
            String[] termResult = listOfDocument.get(i).getListofTerm();
            for (int j = 0; j < termResult.length; j++) {
                Posting tempPosting = new Posting(termResult[j],listOfDocument.get(i));
                   list.add(tempPosting);
            }
        }
        Collections.sort(list);
        return list;
    }
    
    public void makeDictionary(){
        
    }
}
