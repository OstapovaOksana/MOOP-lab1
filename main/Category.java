package main;

import java.io.Serializable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Category implements Serializable {
	public int id;
	public String name;
	
	public Category(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Category(Element category) {
        this.id = Integer.parseInt(category.getAttribute("id"));
        this.name = category.getAttribute("name");
    }
	
	public Element serialize(Document doc) {
        Element category = doc.createElement("category");
        category.setAttribute("id", String.valueOf(this.id));
        category.setAttribute("name", this.name);
        return category;
    }
	
	 @Override
	    public String toString() {
	        return "Category{" +
	                "id=" + id +
	                ", name='" + name + '\'' +
	                '}';
	    }
}
