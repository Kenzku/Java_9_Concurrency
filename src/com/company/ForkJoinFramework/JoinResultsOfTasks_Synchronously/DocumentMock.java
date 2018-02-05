package com.company.ForkJoinFramework.JoinResultsOfTasks_Synchronously;

import java.util.Random;

/**
 * simulate documents
 */
public class DocumentMock {
  private String words[]={"the","hello","goodbye","packt","java","thread","pool","random","class","main"};

  public String[][] generateDocument(int numberOfLines, int numberOfWords, String word) {
    int counter = 0;
    String document[][] = new String[numberOfLines][numberOfWords];

    Random random = new Random();

    for (int i = 0; i < numberOfLines; i = i + 1) {
      for (int j = 0; j < numberOfWords; j = j + 1) {
        int index = random.nextInt(words.length);
        document[i][j] = words[index];
        if (document[i][j].equals(word)) {
          counter = counter + 1;
        }
      }
    }

    System.out.printf("DocumentMock: The word appears %d times in the doucment. \n", counter);
    return document;
  }
}
