package main;

import java.io.Serializable;
import java.text.ParseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class News implements Serializable{
	public int id;
	public String name;
	public String publishingHouse;
	public Category category;
	
	public News(int id, String name, String publishingHouse, Category category) {
		this.id = id;
		this.name = name;
		this.publishingHouse = publishingHouse;
		this.category = category;
	}
	
	public News(Element news, Category category) throws ParseException {
        this.id = Integer.parseInt(news.getAttribute("id"));
        this.name = news.getAttribute("name");
        this.publishingHouse = news.getAttribute("publishingHouse");
        this.category = category;
    }

    public Element serialize(Document doc) {
        Element news = doc.createElement("news");
        news.setAttribute("id", String.valueOf(this.id));
        news.setAttribute("name", this.name);
        news.setAttribute("publishingHouse", this.publishingHouse);
        return news;
    }
    
    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", publishingHouse='" + publishingHouse + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
