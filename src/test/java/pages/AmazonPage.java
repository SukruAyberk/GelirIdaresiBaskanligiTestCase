package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class AmazonPage {

    public AmazonPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "twotabsearchtextbox")
    public WebElement searchBox;

    @FindBy(id = "nav-search-submit-button")
    public WebElement searchButton;

    @FindBy(xpath = "//input[@id='sp-cc-accept']")
    public WebElement cookie;

    @FindBy(xpath = "//*[text()='Monster']")
    public WebElement brand;

    @FindBy(xpath = "//input[@id='low-price']")
    public WebElement minPrice;

    @FindBy(xpath = "//input[@id='high-price']")
    public WebElement maxPrice;

    @FindBy(xpath = "//input[@class='a-button-input']")
    public WebElement goButton;

    @FindBy(xpath = "(//img[@class='s-image'])[2]")
    public WebElement secondProduct;

    @FindBy(xpath = "(//tbody)[2]")
    public WebElement productInfo;
    ////span[@id='productTitle']

    @FindBy(xpath = "(//span[@class='a-price-whole'])[1]")
    public WebElement productPriceWhole;

    @FindBy(xpath = "(//span[@class='a-price-decimal'])[1]")
    public WebElement productPriceDecimal;

    @FindBy(xpath = "(//span[@class='a-price-fraction'])[1]")
    public WebElement productPriceFraction;

    @FindBy(xpath = "(//span[@class='a-price-symbol'])[1]")
    public WebElement productPriceSymbol;

    @FindBy(xpath = "//input[@id='add-to-cart-button']")
    public WebElement addToCartButton;

    @FindBy(xpath = "//a[@class='a-button-text']")
    public WebElement goToCartButton;

    @FindBy(xpath = "//span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap sc-product-price a-text-bold']")
    public WebElement cartPrice;

    @FindBy(xpath = "//select[@name='quantity']")
    public WebElement quantityDdm;

    @FindBy(xpath = "//span[@id='sc-subtotal-label-activecart']")
    public WebElement twoProductResultText;

    @FindBy(xpath = "//input[@data-action='delete']")
    public WebElement deleteButton;

    @FindBy(xpath = "//h1[@class='a-spacing-mini a-spacing-top-base']")
    public WebElement emptyCartResultText;

}
