package lse;

import java.io.*;
import java.util.*;

    /**
     * This class builds an index of keywords. Each keyword maps to a set of pages in
     * which it occurs, with frequency of occurrence in each page.
     *
     */
    public class LittleSearchEngine {
        
        /**
         * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
         * an array list of all occurrences of the keyword in documents. The array list is maintained in 
         * DESCENDING order of frequencies.
         */
        HashMap<String,ArrayList<Occurrence>> keywordsIndex;
        
        /**
         * The hash set of all noise words.
         */
        HashSet<String> noiseWords;
        
        /**
         * Creates the keyWordsIndex and noiseWords hash tables.
         */
        public LittleSearchEngine() {
            keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
            noiseWords = new HashSet<String>(100,2.0f);
        }
        
        /**
         * Scans a document, and loads all keywords found into a hash table of keyword occurrences
         * in the document. Uses the getKeyWord method to separate keywords from other words.
         * 
         * @param docFile Name of the document file to be scanned and loaded
         * @return Hash table of keywords in the given document, each associated with an Occurrence object
         * @throws FileNotFoundException If the document file is not found on disk
         */
        
        public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
        throws FileNotFoundException {
            
            HashMap<String,Occurrence> hMap = new HashMap<String,Occurrence> (1000,2.0f);
            
            Scanner dFile = new Scanner(new File(docFile));
            
            while(dFile.hasNext())
            {
                String dWord = dFile.next();
                
                if(dWord!=null)
                {
                    
                if(getKeyword(dWord) == null)
                    continue;
                else
                {
                    dWord = getKeyword(dWord);
                    
                    if(!(hMap.containsKey(dWord)))
                    {
                        Occurrence oc = new Occurrence(docFile,1);
                        hMap.put(dWord, oc);
                    }
                    else
                    {
                        Occurrence oc = hMap.get(dWord);
                        oc.frequency++;
                        hMap.put(dWord, oc);
                    }
                }
                }
            }
            dFile.close();
            return hMap;
            /** COMPLETE THIS METHOD **/
            
            // following line is a placeholder to make the program compile
            // you should modify it as needed when you write your code
            
        }    
        
        /**
         * Merges the keywords for a single document into the master keywordsIndex
         * hash table. For each keyword, its Occurrence in the current document
         * must be inserted in the correct place (according to descending order of
         * frequency) in the same keyword's Occurrence list in the master hash table. 
         * This is done by calling the insertLastOccurrence method.
         * 
         * @param kws Keywords hash table for a document
         */
         public void mergeKeywords(HashMap<String,Occurrence> kws) {
             
            for(String kWord:kws.keySet())
            {
                ArrayList<Occurrence> oc = keywordsIndex.get(kWord);
                Occurrence occur = kws.get(kWord);
                
                if(oc!=null)
                {
                    oc.add(occur);
                    insertLastOccurrence(oc);
                    keywordsIndex.put(kWord, oc);
                }
                else
                {
                    oc = new ArrayList<Occurrence>();
                    oc.add(occur);
                    keywordsIndex.put(kWord, oc);
                
                }
            }
            /** COMPLETE THIS METHOD **/
            
        }
         
        /**
         * Given a word, returns it as a keyword if it passes the keyword test,
         * otherwise returns null. A keyword is any word that, after being stripped of any
         * trailing punctuation(s), consists only of alphabetic letters, and is not
         * a noise word. All words are treated in a case-INsensitive manner.
         * 
         * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
         * NO OTHER CHARACTER SHOULD COUNT AS PUNCTUATION
         * 
         * If a word has multiple trailing punctuation characters, they must all be stripped
         * So "word!!" will become "word", and "word?!?!" will also become "word"
         * 
         * See assignment description for examples
         * 
         * @param word Candidate word
         * @return Keyword (word without trailing punctuation, LOWER CASE)
         */
        public String getKeyword(String word) {
            
            
            if(word.length() == 0)
                return null;
            
            for(int i = word.length()-1;i>=0;i--)
            {
                if(word.charAt(i) == '.' || word.charAt(i) == ',' || word.charAt(i) == '?' || word.charAt(i) == ':' || word.charAt(i) == ';' || word.charAt(i) == '!')
                {
                    word = word.substring(0,i);
                    if(word.length() == 0) return null;
                    continue;
                }
                break;
            }
            word = word.toLowerCase();
            
            for(int i = 0;i<word.length();i++)
            {
                if(!Character.isLetter(word.charAt(i)))
                {
                    return null;
                }
            }
            if(noiseWords.contains(word))
            {
                return null;
            }
            
            /** COMPLETE THIS METHOD **/
            // following line is a placeholder to make the program compile
            // you should modify it as needed when you write your code
            return word;
        }

        /**
         * Inserts the last occurrence in the parameter list in the correct position in the
         * list, based on ordering occurrences on descending frequencies. The elements
         * 0..n-2 in the list are already in the correct order. Insertion is done by
         * first finding the correct spot using binary search, then inserting at that spot.
         * 
         * @param occs List of Occurrences
         * @return Sequence of mid point indexes in the input list checked by the binary search process,
         *         null if the size of the input list is 1. This returned array list is only used to test
         *         your code - it is not used elsewhere in the program.
         */
        public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
            /** COMPLETE THIS METHOD **/
            
            if(occs.size()==1)
                return null;
            
            ArrayList<Integer> mPoints = new ArrayList<Integer>();
            
            int min = 0;
            int max = occs.size()-1;
            int mid = 0;
            
            Occurrence oc = occs.get(occs.size()-1);
            
            while(max>=min)
            {
                mid = (min + max)/2;
                mPoints.add(mid);
                
                if(occs.get(mid).frequency < oc.frequency)
                {
                    max = mid-1;
                }
                else if(occs.get(mid).frequency > oc.frequency)
                {
                    min = mid + 1;
                }
                else if(occs.get(mid).frequency == oc.frequency)
                {
                    break;
                }
            }
            
            if(occs.get(mid).frequency >= oc.frequency)
            {
                mid = mid+1;
            }
          occs.add(mid,oc);
            
            // following line is a placeholder to make the program compile
            // you should modify it as needed when you write your code
            return mPoints;
        }
    
        /**
         * This method indexes all keywords found in all the input documents. When this
         * method is done, the keywordsIndex hash table will be filled with all keywords,
         * each of which is associated with an array list of Occurrence objects, arranged
         * in decreasing frequencies of occurrence.
         * 
         * @param docsFile Name of file that has a list of all the document file names, one name per line
         * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
         * @throws FileNotFoundException If there is a problem locating any of the input files on disk
         */
        public void makeIndex(String docsFile, String noiseWordsFile) 
        throws FileNotFoundException {
            // load noise words to hash table
            Scanner sc = new Scanner(new File(noiseWordsFile));
            while (sc.hasNext()) {
                String word = sc.next();
                noiseWords.add(word);
            }
            
            // index all keywords
            sc = new Scanner(new File(docsFile));
            while (sc.hasNext()) {
                String docFile = sc.next();
                HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
                mergeKeywords(kws);
            }
            sc.close();
        }
        
        /**
         * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
         * document. Result set is arranged in descending order of document frequencies. 
         * 
         * Note that a matching document will only appear once in the result. 
         * 
         * Ties in frequency values are broken in favor of the first keyword. 
         * That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2 also with the same 
         * frequency f1, then doc1 will take precedence over doc2 in the result. 
         * 
         * The result set is limited to 5 entries. If there are no matches at all, result is null.
         * 
         * See assignment description for examples
         * 
         * @param kw1 First keyword
         * @param kw1 Second keyword
         * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
         *         frequencies. The result size is limited to 5 documents. If there are no matches, 
         *         returns null or empty array list.
         */
        public ArrayList<String> top5search(String kw1, String kw2) {
            /** COMPLETE THIS METHOD **/
            
            ArrayList<String> finalList = new ArrayList<String>();
            kw1 = kw1.toLowerCase();
            kw2 = kw2.toLowerCase();
            
            ArrayList<Occurrence> dList1 = keywordsIndex.get(kw1);
            ArrayList<Occurrence> dList2 = keywordsIndex.get(kw2);
            
            int m = 0;
            int n = 0;
            int dMax = 0;
            
            
            if(dList1 == null && dList2 == null)
                return null;
            
            if(dList1 == null)
            {
                while(n < dList2.size())
                {
                    if(dMax == 5) 
                        break;
                    
                    finalList.add(dList2.get(n).document);
                    n++;
                    dMax++;
                }
                return finalList;
            }
            if(dList2 == null)
            {
                while(m < dList1.size())
                {
                    if(dMax == 5) 
                        break;
                    
                    finalList.add(dList1.get(m).document);
                    m++;
                    dMax++;
                }
                return finalList;
            }
            
            
            while(m < dList1.size() && n < dList2.size())
            {
                if(dMax == 5)
                    break;
                
                if(dList1.get(m).frequency >= dList2.get(n).frequency)
                {
                    if(!finalList.contains(dList1.get(m).document))
                    {
                        finalList.add(dList1.get(m).document);
                        dMax++;
                    }
                    m++;
                }
                else if(dList1.get(m).frequency < dList2.get(n).frequency)
                {
                    if(!finalList.contains(dList2.get(n).document))
                    {
                        finalList.add(dList2.get(n).document);
                        dMax++;
                    }
                    n++;
                }
            }
            
            while(m < dList1.size() || n < dList2.size())
            {
                if( dMax == 5)
                    break;
                
                if(m == dList1.size())
                {
                    if(!finalList.contains(dList2.get(n).document))
                    {
                        finalList.add(dList2.get(n).document);
                        dMax++;
                    }
                    n++;
                }
                else
                {
                    if(!finalList.contains(dList1.get(m).document))
                    {
                        finalList.add(dList1.get(m).document);
                        dMax++;
                    }
                    m++;
                }
                
            }
            
            // following line is a placeholder to make the program compile
            // you should modify it as needed when you write your code
            return finalList;
        }
    }
	