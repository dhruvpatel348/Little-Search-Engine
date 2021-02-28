package lse;

import java.io.IOException;

public class Driver {

   public static void main(String[] args) throws IOException {
	   
      String docsFile   = "docs.txt";
      String noiseWords = "noisewords.txt";

      LittleSearchEngine searchEngine = new LittleSearchEngine();
      System.out.println(searchEngine.getKeyword("equi-distant"));
      searchEngine.makeIndex(docsFile, noiseWords);

      String kw1 = "orange";
      String kw2 = "weird";

      System.out.println(searchEngine.top5search(kw1, kw2));
   }
}
