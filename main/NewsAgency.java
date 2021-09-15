package main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class NewsAgency {
	private ArrayList<Category> categories;
	private ArrayList<News> news;
	
	private DocumentBuilderFactory documentBuilderFactory;
	
	public NewsAgency() throws SAXException {
        categories = new ArrayList<>();
        news = new ArrayList<>();

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File("NewsAgency-lab1.xsd"));
        
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(false);
        documentBuilderFactory.setSchema(schema);
    }
	
	public void saveToFile(String fileName) throws Exception {
        DocumentBuilder documentBuilder = null;
        Document doc = null;
        documentBuilder = documentBuilderFactory.newDocumentBuilder();

        doc = documentBuilder.newDocument();

        Element root = doc.createElement("NewsAgency");
        doc.appendChild(root);

        Map<Category, Element> categoryElMap = new HashMap<>();

        for (Category category : categories) {
            Element CategoryElement = category.serialize(doc);
            root.appendChild(CategoryElement);
            categoryElMap.put(category, CategoryElement);
        }

        for (News news : news) {
            Element newsElement = news.serialize(doc);
            categoryElMap.get(news.category).appendChild(newsElement);
        }

        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(new File(fileName));
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(domSource, fileResult);
    }
	
	public void loadFromFile(String fileName) throws Exception {
        categories.clear();
        news.clear();

        DocumentBuilder documentBuilder = null;
        documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document doc = null;
        doc = documentBuilder.parse(new File(fileName));

        Element root = doc.getDocumentElement();
        if (root.getTagName().equals("NewsAgency")) {
            NodeList listCategories = root.getElementsByTagName("category");

            for (int i = 0; i < listCategories.getLength(); i++) {
                Element categoryElement = (Element) listCategories.item(i);
                Category category = new Category(categoryElement);
                categories.add(category);

                NodeList listNews = categoryElement.getElementsByTagName("news");

                for (int j = 0; j < listNews.getLength(); j++) {
                    Element newsElement = (Element) listNews.item(j);
                    News newsObject = new News(newsElement, category);
                    news.add(newsObject);
                }
            }
        }
    }
	
	public Category getCategory(int id) {
        for (Category category : categories) {
            if (category.id == id) return category;
        }
        return null;
    }
	
	public void addCategory(int id, String name) throws Exception {
        if (getCategory(id) != null) throw new Exception("Category with id " + id + " already exists");
        categories.add(new Category(id, name));
    }
	
	public Category getCategoryIndex(int index) {
        return categories.get(index);
    }
	
	public ArrayList<Category> getCategories() {
	    return categories;
	}
	
	public int countCategories() {
	   return categories.size();
	}
	
	public void deleteCategory(int id) throws Exception {
        Category category = getCategory(id);
        if (category == null) throw new Exception("Cannot delete category with id " + id + " as it doesn't exist");
        news.removeIf(f -> f.category == category);
        categories.remove(category);
    }
	
	public News getNewsObject(int id) {
        for (News newsObject : news) {
            if (newsObject.id == id) return newsObject;
        }
        return null;
    }


    public void addNewsObject(int id, String name, String publishingHouse, int categoryId) throws Exception {
		if (getNewsObject(id) != null) throw new Exception("News with id " + id + " already exists");
		
		Category category = getCategory(categoryId);
		if (category == null)
		throw new Exception("Cannot add news with id " + id + " because category with id " + categoryId + " doesn't exist");
		
		News newsObject = new News(id, name, publishingHouse,
		  category);
		
		news.add(newsObject);
    }
    
    public News getNewsIndex(int index) {
        return news.get(index);
    }

    public ArrayList<News> getNews() {
        return news;
    }
    
    public ArrayList<News> getNewsByCategory(int categoryId) {
        Category category = getCategory(categoryId);
        ArrayList<News> result = new ArrayList<>();
        for (News newsObject : news) {
            if (newsObject.category == category) {
                result.add(newsObject);
            }
        }
        return result;
    }
    
    public int countNews() {
        return news.size();
    }

    public void deleteNewsObject(int id) throws Exception {
        News newsObject = getNewsObject(id);
        if (newsObject == null) throw new Exception("Cannot delete newsObject with id " + id + " as it doesn't exist");
        news.remove(newsObject);
    }
}
