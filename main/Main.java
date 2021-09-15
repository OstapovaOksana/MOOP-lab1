package main;

public class Main {

	public static void main(String[] args) throws Exception {
		
		example1();
		example2();
		example3();
		example4();
	}
	
	public static void example1() throws Exception {
        System.out.println("\nExample 1");
        NewsAgency services = new NewsAgency();
        services.addCategory(1, "Political");
        services.addNewsObject(1, "The meeting between Zelensky and Biden was postponed", "Segodnya", 1);
        services.addCategory(2, "Sport");
        services.addNewsObject(2, "Messi broke Pele's record for goals for the national team", "KOF", 2);
        services.addNewsObject(3, "Japan has refused to host the Club World Cup because of the coronavirus", "KOF", 2);
        services.saveToFile("example-1.xml");
    }
	
	 public static void example2() throws Exception {
        System.out.println("\nExample 2");
        NewsAgency services = new NewsAgency();
        services.loadFromFile("example-1.xml");
        services.deleteCategory(2);
        services.saveToFile("example-2.xml");
	 }

	 public static void example3() throws Exception {
	        System.out.println("\nExample 3");
	        NewsAgency services = new NewsAgency();
	        services.loadFromFile("example-1.xml");
	        try {
	            services.deleteCategory(3);
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        System.out.println(services.getNewsByCategory(2));	       
	        System.out.println(services.getNewsObject(2));
	        System.out.println(services.getNewsObject(3));
	        services.deleteNewsObject(3);
	        System.out.println(services.getNews());
	        services.saveToFile("example-3.xml");
	    }	 
	 
	 public static void example4() throws Exception {
	        System.out.println("\nExample 4");
	        NewsAgency services = new NewsAgency();

	        services.loadFromFile("example-1.xml");
	        services.getNewsObject(1).publishingHouse = "Changed Publishing House";
	        services.saveToFile("example-4.xml");
	    }

}
