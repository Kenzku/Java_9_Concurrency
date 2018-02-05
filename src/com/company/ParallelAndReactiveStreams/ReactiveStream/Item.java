package com.company.ParallelAndReactiveStreams.ReactiveStream;

/**
 * represent the content sent from publisher to subscriber
 */
public class Item {

  private String title;
  private String content;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
