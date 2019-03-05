/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.sun.org.apache.xerces.internal.util.DOMUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Yos Rio
 */
public class InvertedIndex {

    private ArrayList<Document> listOfDocument = new ArrayList<Document>();
    private ArrayList<Term> dictionary = new ArrayList<Term>();

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
        list = this.getUnsortedPostingList();
        Collections.sort(list);
        return list;
    }
    
    public ArrayList<Posting> getUnsortedPostingListWithTermNumber() {
        ArrayList<Posting> list = new ArrayList<Posting>();
        for (int i = 0; i < listOfDocument.size(); i++) {
            ArrayList<Posting> postingDoc = listOfDocument.get(i).getListofPosting();
            for (int j = 0; j < postingDoc.size(); j++) {
                Posting tempPosting = postingDoc.get(j);
                list.add(tempPosting);
            }
        }
        return list;
    }
    
    public ArrayList<Posting> getSortedPostingListWithTermNumber() {
        ArrayList<Posting> list = new ArrayList<Posting>();
        list = this.getUnsortedPostingListWithTermNumber();
        Collections.sort(list);
        return list;
    }
    
    public void makeDictionaryWithTermNumber() {
        ArrayList<Posting> list = this.getSortedPostingListWithTermNumber();

        for (int i = 0; i < list.size(); i++) {
            if (getDictionary().isEmpty()) {
                Term term = new Term(list.get(i).getTerm());
                term.getPostingList().add(list.get(i));
                getDictionary().add(term);
            } else {
                Term tempTerm = new Term(list.get(i).getTerm());
                int position = Collections.binarySearch(getDictionary(), tempTerm);
                if (position < 0) {
                    tempTerm.getPostingList().add(list.get(i));
                    getDictionary().add(tempTerm);
                } else {
                    getDictionary().get(position).getPostingList().add(list.get(i));
                    Collections.sort(getDictionary().get(position).getPostingList());
                }
                Collections.sort(getDictionary());
            }
        }
    }
    
    public int getDocFreq(String term){
        ArrayList<Term> result = getDictionary();
        int count = 0;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getTerm().equals(term)) {
                count = result.get(i).getNumberOfDocument();
            }
        }
        return count;
        
//        Term tempTerm = new Term(term);
//        int index = Collections.binarySearch(dictionary, tempTerm);
//        if (index > 0) {
//            ArrayList<Posting> tempPost = dictionary.get(index).getPostingList();
//            return tempPost.size();
//        }else{
//            return -1;
//        }
    }
    
    public double getInverseDocFreq(String term){
        double n = getDocFreq(term);
        double N = listOfDocument.size();
        System.out.println(n);
        System.out.println(N);
        return Math.log10(N/n);
//        Term tempTerm = new Term(term);
//        int index = Collections.binarySearch(dictionary, tempTerm);
//        if (index > 0) {
//            int N = listOfDocument.size();
//            int ni = getDocFreq(term);
//            return Math.log10(N/ni);
//        }else{
//            return 0.0;
//        }
    }
    
    public int getTermFreq(String term, int idDoc){
        Document doc = new Document();
        doc.setId(idDoc);
        int index = Collections.binarySearch(listOfDocument, doc);
        if (index >= 0) {
            ArrayList<Posting> tempPost = getListOfDocument().get(index).getListofPosting();
            Posting post = new Posting();
            post.setTerm(term);
            int postIndex = Collections.binarySearch(tempPost, post);
            if (postIndex > 0) {
                return tempPost.get(postIndex).getNumberOfTerm();
            }
            return 0;
        }
        return 0;
    }
    
    
    public ArrayList<Posting> search(String kunci) {
        String[] q = kunci.split(" ");
        ArrayList<Posting> result = new ArrayList<>();
        for (int i = 0; i < q.length; i++) {
            String string = q[i];
            if (i == 0) {
                result = searchOneWord(q[i]);
            } else {
                ArrayList<Posting> result2 = searchOneWord(q[i]);
                result = intersection(result, result2);
            }
        }
        return result;
    }

    public ArrayList<Posting> intersection(ArrayList<Posting> p1, ArrayList<Posting> p2) {
        if (p1 == null || p2 == null) {
            return new ArrayList<>();
        }
        
        ArrayList<Posting> posting = new ArrayList<>();
        int index_p1 = 0;
        int index_p2 = 0;
        
        Posting post1 = p1.get(index_p1);
        Posting post2 = p2.get(index_p2);

        while (true) {
            if (post1.getDocument().getId() == post2.getDocument().getId()) {
                try {
                    posting.add(post1);
                    index_p1++;
                    index_p2++;
                    post1 = p1.get(index_p1);
                    post2 = p2.get(index_p2);
                } catch (Exception e) {
                    break;
                }
            } else if (post1.getDocument().getId() < post2.getDocument().getId()) {
                try {
                    index_p1++;
                    post1 = p1.get(index_p1);
                } catch (Exception e) {
                    break;
                }
            } else {
                try {
                    index_p2++;
                    post2 = p2.get(index_p2);
                } catch (Exception e) {
                    break;
                }
            }
        }
        return posting;
    }

    public ArrayList<Posting> searchOneWord(String kunci) {
        Term tempTerm = new Term(kunci);
        if (getDictionary().isEmpty()) {
            return null;
        } else {
            int positionTerm = Collections.binarySearch(dictionary, tempTerm);
            if (positionTerm < 0) {
                return null;
            } else {
                return dictionary.get(positionTerm).getPostingList();
            }
        }
    }

    public void makeDictionary() {
        ArrayList<Posting> list = this.getSortedPostingList();

        for (int i = 0; i < list.size(); i++) {
            if (getDictionary().isEmpty()) {
                Term term = new Term(list.get(i).getTerm());
                term.getPostingList().add(list.get(i));
                getDictionary().add(term);
            } else {
                Term tempTerm = new Term(list.get(i).getTerm());
                int position = Collections.binarySearch(getDictionary(), tempTerm);
                if (position < 0) {
                    tempTerm.getPostingList().add(list.get(i));
                    getDictionary().add(tempTerm);
                } else {
                    getDictionary().get(position).getPostingList().add(list.get(i));
                    Collections.sort(getDictionary().get(position).getPostingList());
                }
                Collections.sort(getDictionary());
            }
        }
    }
    
    public ArrayList<Document> getListOfDocument() {
        return listOfDocument;
    }

    public void setListOfDocument(ArrayList<Document> listOfDocument) {
        this.listOfDocument = listOfDocument;
    }

    public ArrayList<Term> getDictionary() {
        return dictionary;
    }

    public void setDictionary(ArrayList<Term> dictionary) {
        this.dictionary = dictionary;
    }

}
