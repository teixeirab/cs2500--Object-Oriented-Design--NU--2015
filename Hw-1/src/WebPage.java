/**
 * Created by becogontijo on 4/6/2015.
 */
public class WebPage implements Publication {

  private String title;
  private String url;
  private String retrieved;

  /**
   *
   * @param title represents the title of an webpage
   * @param url represents the url of an webpage
   * @param retrieved represents the retrival date of an webpage
   */
  public WebPage(String title, String url, String retrieved) {
    this.title = title;
    this.url = url;
    this.retrieved = retrieved;
  }

  @Override
  public String citeApa() {
    return  title + ". Retrieved " + retrieved + ", from "
            + url + ".";
  }


  @Override
  public String citeMla() {
    return title + ". Web. " + retrieved + " <" + url + ">.";
  }
}
