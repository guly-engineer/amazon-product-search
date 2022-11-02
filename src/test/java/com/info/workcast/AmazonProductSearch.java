package com.info.workcast;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;



import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class AmazonProductSearch {
    WebDriver driver;

    private static WebDriver newDriver() {

       // WebDriverManager.chromedriver().setup();
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver_106/chromedriver.exe");
        } else if (osName.contains("linux")) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver_106/chromedriver_linux");
        } else {
            throw new RuntimeException("OS not supported");
        }
        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getProperty("headless")) == true) {
            options.addArguments("--headless");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-debugging-port=9222");
        options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");

//        String osName = System.getProperty("os.name").toLowerCase();
//
//        if (osName.contains("win")) {
//            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver_106/chromedriver.exe");
//        } else if (osName.contains("linux")) {
//            System.setProperty("webdriver.chrome.driver", "/chromedriver_linux");
//        } else {
//            throw new RuntimeException("OS not supported");
//        }
//        ChromeOptions options = new ChromeOptions();
//        if (Boolean.parseBoolean(System.getProperty("headless")) == true) {
//            options.addArguments("--headless");
//        }
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--no-sandbox");
        return new ChromeDriver();

    }

    @After
    public void finishTest() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Given("I am on the {string} page")
    public void i_am_on_the_amazon_co_uk_landing_page(String url) {
        driver = newDriver();
        driver.navigate().to(url);
        driver.manage().window().maximize();
        WebElement acceptCookie=driver.findElement(By.id("a-autoid-0"));
        acceptCookie.submit();

    }

    @When("I search for {string}")
    public void i_search_for(String item) {
        WebElement searchElement = driver.findElement(By.id("twotabsearchtextbox"));
        searchElement.sendKeys(item);
        searchElement.submit();
    }

    @Then("I am able to scroll through all search results")
    public void i_am_able_to_scroll_through_all_search_results() {
        new Actions(driver)
                .sendKeys(Keys.END)
                .build()
                .perform();

        new Actions(driver)
                .sendKeys(Keys.HOME)
                .build()
                .perform();

    }

    @Then("I see that each item has following attributes:$")
    public void i_see_the_relevant(List<String> attributes) {
        for (WebElement productItem : productItems()){
            for (String attribute : attributes) {
                switch (attribute) {
                    case "thumbnail":
                        validateThumbnail(productItem);
                    case "title":
                        validateTitle(productItem);
                    case "rating":
                        validateRating(productItem);
                    case "price":
                        validatePrice(productItem);
                }
            }
        }
    }

    private List<WebElement> productItems() {
        return driver.findElements(By.xpath("//div[@data-component-type = 's-search-result']"));
    }

    private void validateThumbnail(WebElement productItem) {
        List<WebElement> images = productItem.findElements(By.tagName("img"));
        assertThat(images).hasSize(1);
    }

    private void validateTitle(WebElement productItem) {
        List<WebElement> h2 = productItem.findElements(By.cssSelector("h2[class*=a-size-mini]"));
        assertThat(h2).hasSize(1);
        assertThat(h2.get(0).getText()).isNotBlank();
    }

    private void validateRating(WebElement productItem) {
        List<WebElement> ratingIcons = productItem.findElements(By.cssSelector("i[class*=icon-star-small]"));
        assertThat(ratingIcons).hasSize(1);
    }

    private void validatePrice(WebElement productItem) {
        List<WebElement> priceTags = productItem.findElements(By.cssSelector(".a-price[data-a-color='base']"));
        assertThat(priceTags).hasSize(1);
        assertThat(priceTags.get(0).getText()).isNotBlank();
    }

    @Then("I click on the thumbnail of the {int}. item")
    public void i_click_on_the_thumbnail_a_particular_product(int itemIndex) {
        productItems()
                .get(itemIndex-1)
                .findElement(By.tagName("img"))
                .click();
    }

    @Then("I am able to view more about the item")
    public void i_am_able_to_view_more_about_the_item() {
        boolean moreAboutItemIsDisplayed= driver.findElement(By.id("featurebullets_feature_div")).isDisplayed();
        assertThat(moreAboutItemIsDisplayed).isTrue();
    }
}

