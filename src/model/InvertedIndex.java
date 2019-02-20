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

    public void addNewDocument(Document document) {
        listOfDocument.add(document);
    }

    public ArrayList<Posting> getUnsortedPostingList() {
        ArrayList<Posting> list = new ArrayList<Posting>();

        for (int i = 0; i < listOfDocument.size(); i++) {
            String[] termResult = listOfDocument.get(i).getListofTerm();
            for (int j = 0; j < termResult.length; j++) {
                Posting tempPosting = new Posting(termResult[j], listOfDocument.get(i));
                list.add(tempPosting);
            }
        }
        return list;
    }

    public ArrayList<Posting> getSortedPostingList() {
        ArrayList<Posting> list = new ArrayList<Posting>();

        for (int i = 0; i < listOfDocument.size(); i++) {
            String[] termResult = listOfDocument.get(i).getListofTerm();
            for (int j = 0; j < termResult.length; j++) {
                Posting tempPosting = new Posting(termResult[j], listOfDocument.get(i));
                list.add(tempPosting);
            }
        }
        Collections.sort(list);
        return list;
    }

    public void makeDictionary() {
        ArrayList<Posting> postingList = this.getSortedPostingList();

        Term term = new Term();
        for (int i = 0; i < postingList.size(); i++) {

            term.setTerm(postingList.get(i).getTerm());

            if (i > 0) {
                if (postingList.get(i).getTerm()
                        .equalsIgnoreCase(postingList.get(i - 1).getTerm())) {
                    if (postingList.get(i).getDocument().getId() != postingList.get(i - 1).getDocument().getId()) {
                        Posting posting = postingList.get(i);
                        term.getPostingList().add(posting);
                    }
                } else {
                    Posting posting = postingList.get(i);
                    term.getPostingList().add(posting);
                }
            } else {
                Posting posting = postingList.get(i);
                term.getPostingList().add(posting);
            }
            if (i < postingList.size() - 1) {
                if (!postingList.get(i).getTerm()
                        .equalsIgnoreCase(postingList.get(i + 1).getTerm())) {
                    getListOfTerm().add(term);
                    term = new Term();
                }
            } else {
                getListOfTerm().add(term);
            }

        }
    }

    public ArrayList<Document> getListOfDocument() {
        return listOfDocument;
    }

    public void setListOfDocument(ArrayList<Document> listOfDocument) {
        this.listOfDocument = listOfDocument;
    }

    public ArrayList<Term> getListOfTerm() {
        return listOfTerm;
    }

    public void setListOfTerm(ArrayList<Term> listOfTerm) {
        this.listOfTerm = listOfTerm;
    }

    @Override
    public String toString() {
        String string = "";

        for (int i = 0; i < this.listOfTerm.size(); i++) {
            System.out.print(this.listOfTerm.get(i).getTerm() + " -> ");
            for (int j = 0; j < this.listOfTerm.get(i).getPostingList().size(); j++) {
                System.out.print(this.listOfTerm.get(i).getPostingList().get(j).getDocument().getId() + ", ");
            }
            System.out.println("");
        }

        return string;
    }
}
