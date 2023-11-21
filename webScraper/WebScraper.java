package webScraper;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebScraper {
	private WebDriver chrome;
	private static final String TESTUDO_URL ="https://app.testudo.umd.edu/soc/";
	private ChromeOptions options = new ChromeOptions();
	private LinkedList<Course> courses;
	private LinkedList<String> deptLinks;
	public WebScraper(String browserPath) {
		
		options.addArguments("headless");
		chrome = new ChromeDriver(options);
		System.out.println("EDG");
		courses = new LinkedList<Course>();
		deptLinks = retrieveDeptLinks();
		for(String url: deptLinks) {
			chrome.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
			chrome.get(url);
			List<WebElement> courses = chrome.findElements(By.xpath(".//div[@class=\"course\"]"));
			for(WebElement element: courses) {
				addCourse(element);
			}
		}
	}
	private void addCourse(WebElement element) {
		WebDriver temp = new ChromeDriver();
		String courseID = "";
		String courseName = "";
		String description = "no description";
		LinkedList<String> genEds = new LinkedList<String>();
		LinkedList<Course.Section> sections = new LinkedList<Course.Section>();
		String credits = element.findElement(By.xpath(".//span[@class=\"course-min-credits\"]")).getText();
		courseID = element.findElement(By.xpath(".//div[@class=\"course-id\"]")).getText();
		courseName = element.findElement(By.xpath(".//span[@class=\"course-title\"]")).getText();
		
		try {
			List<WebElement> genEdsList = element.findElements(By.xpath(".//div[@class=\"course\"]//span[@class=\"course-subcategory\"]//a"));
			for(WebElement i: genEdsList) {
				genEds.add(i.getText());
			}
		}catch(NoSuchElementException e) {
			System.out.println(e.getMessage());
			System.out.println(element.getText());
		}
		
		try {	
			List<WebElement> descriptions= element.findElements(By.xpath(".//div[@class=\"approved-course-text\"]"));
			description = descriptions.get(descriptions.size()-1).getText();
		}catch(NoSuchElementException e) {
			System.out.println(e.getMessage());
			System.out.println(element.getText());
		}
		try {
			String url = element.findElement(By.xpath(".//a[@class=\"toggle-sections-link\"]")).getAttribute("href");
			retrieveSections(url);
			
		}catch(NoSuchElementException e) {
			
		}
		System.out.println(courseID+" "+courseName + " "+ credits +" "+ description);
	}
	private LinkedList<Course.Section> retrieveSections(String url){
		boolean virtual=false;
		String prof="TBA";
		String meetingTimes ="";
		String[] startTimes;
		String[] endTimes;
		String[] locations;
		String sectionNum="";
		WebDriver temp = new ChromeDriver(options);
		LinkedList<Course.Section> output = new LinkedList<Course.Section>();
		temp.get(url);
		List<WebElement> sectionData = temp.findElements(By.xpath(".//div[@class=\"sections-container\"]//div"));
		for(WebElement i: sectionData) {
			if(i.getAttribute("class").equals("section delivery-online")) {
				virtual = true;
			}
			
			sectionNum = i.findElement(By.xpath("//span[@class=\"section-id\"]")).getText();
			if(!virtual) {
				List<WebElement> containers = i.findElements(By.xpath(".//div[@class=\"class-days-container\"]"));
			}
		}
		return null ;
	}
	private LinkedList<String> retrieveDeptLinks(){
		chrome.get(TESTUDO_URL);
		List<WebElement> depts = chrome.findElements(By.xpath("//a[@class='clearfix']"));
		LinkedList<String> output = new LinkedList<String>();
		for(WebElement element: depts) {
			output.add(element.getAttribute("href"));
		}
		
		return output;
		
	}
	public LinkedList<String> getDeptLinks(){
		return deptLinks;
	}
	
	public void exit() {
		chrome.quit();
	}
	
	
}

