package com.p202.fiserv;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SingleTransfer {
	private WebDriver driver;
	private static final long WAIT_TIMEOUT = 20;
	private SoftAssert softAssert = new SoftAssert();
	@BeforeClass
	public void beforeClass() {
    	System.setProperty("webdriver.chrome.driver", "c:\\bin\\chromedriver\\chromedriver.exe");
    	driver = new ChromeDriver();
	}
    @BeforeMethod
    public void beforeMethod() {

    	driver.get("https://fiserv-architect-dev.azurewebsites.net/");
    }
    
    @AfterMethod
    public void afterMethod() {
    	System.out.println("After Method");
    	//driver.close();
    }
    
    @Test
    public void test() {
    	WebElement username = driver.findElement(By.id("username"));
    	username.clear();
    	username.sendKeys("architect");
    	WebElement password = driver.findElement(By.id("password"));
    	password.clear();
    	password.sendKeys("architect");
    	
        WebElement loginBtn = driver.findElement(By.xpath("//button[@type='button']//*[contains(text(),'Login')]"));
        loginBtn.click();
    	
    	new WebDriverWait(driver, WAIT_TIMEOUT).until(ExpectedConditions.stalenessOf(loginBtn));
    	new WebDriverWait(driver, WAIT_TIMEOUT).until(ExpectedConditions.urlToBe("https://fiserv-architect-dev.azurewebsites.net/transfers"));
    	//WebDriverWait wait = new WebDriverWait(driver, 30);
    	//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='button']")));
    	//driver.findElement(By.xpath("//button[@type='button']")).click();
        
    	softAssert.assertEquals(driver.getCurrentUrl(), "https://fiserv-architect-dev.azurewebsites.net/transfers");

    	new WebDriverWait(driver, WAIT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label/span[contains(text(), 'From')]")));
    	
    	softAssert.assertTrue(driver.findElement(By.xpath("//label/span[contains(text(), 'From')]")).isDisplayed());
    	
    	//choose 1st option in from dropdown box
    	driver.findElement(By.xpath("//form/div[1]/div/div")).click();
    	String text = driver.findElement(By.xpath("//button[@data-index='1']")).getText();
    	driver.findElement(By.xpath("//button[@data-index='1']")).click();
    	String textDisplayed = driver.findElement(By.xpath("//form/div[1]/div/div")).getText();	
    	softAssert.assertTrue(text.contains(textDisplayed));
    	
    	driver.findElement(By.xpath("//form/div[2]/div/div")).click();
    	//assure first selection is disabled in to dropdown box
    	String focusable = driver.findElement(By.xpath("//button[@data-index='1']")).getAttribute("data-is-focusable");
    	softAssert.assertEquals(focusable, "false");
    	//select the 2nd selection as the "to account"
    	driver.findElement(By.xpath("//button[@data-index='2']")).click();
       	
    	//
    	Actions actions = new Actions(driver);
    	actions.moveToElement(driver.findElement(By.xpath("//form/div[3]/div/div")));
    	actions.click();
    	actions.sendKeys("188");
    	actions.build().perform();
    	
    	//Scheduling - immediate
    	driver.findElement(By.xpath("//form/div[4]/div/div")).click();
    	//driver.findElement(By.xpath("//button[@data-index='0']")).click();
    	driver.findElement(By.xpath("//button//*[contains(text(), 'Immediate')]")).click();
    	
    	//click continue button
    	driver.findElement(By.xpath("//button[@type='submit']")).click();
    	//List<WebElement> buttons = driver.findElements(By.xpath("//*[@class='header__menu']/button"));
    	//for(WebElement btn: buttons){
    	//	btn.click();
    	//}
    	
    	System.out.println("Hello World");
    }
    
}
