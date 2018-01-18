package me.gosimple.nbvcxz;

public class URL
{
    String url;
    String title;
    String browser;
    public URL(String url, String title, String browser)
    {
        this.url = url;
        this.title = title;
        this.browser = browser;
    }

    public String getData()
    {
        return browser + " - " + title + " - " + url;
    }
}
