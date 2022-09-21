package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pages.AmazonPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class AmazonStepDefinitions {

    AmazonPage amazon = new AmazonPage();

    Select select;

    String secilenUrunFiyati;

    @Given("Kullanici {string} anasayfasinda")
    public void kullanici_anasayfasinda(String url) {
        Driver.getDriver().get(ConfigReader.getProperty(url));
        ReusableMethods.waitForClickablility(amazon.cookie, 15);
        ReusableMethods.waitFor(1);
        amazon.cookie.click();
        ReusableMethods.waitFor(1); // waitFor() methodu adımların net görülebilmesi için eklendi

        /*
        Set<Cookie> cookieSet = Driver.getDriver().manage().getCookies();
        System.out.println(cookieSet);
        System.out.println(cookieSet.size());
        if (cookieSet.size() != 0){

        }

         */
    }

    @Then("Kullanici arama kutusuna {string} girer")
    public void kullaniciAramaKutusunaGirer(String word) {
        amazon.searchBox.sendKeys(word);
        ReusableMethods.waitFor(1);
    }

    @And("Kullanici search butonuna tiklar")
    public void kullaniciSearchButonunaTiklar() {
        amazon.searchButton.click();
        ReusableMethods.waitFor(1);
    }

    @And("Kullanici marka {string} secer")
    public void kullaniciMarkaSecer(String aranacakMarka) {
        WebElement brand = Driver.getDriver().findElement(By.xpath("//*[text()='" + aranacakMarka + "']"));
        ReusableMethods.waitForClickablility(brand, 10);
        brand.click();
        ReusableMethods.waitFor(1);
    }

    @And("Kullanici fiyati minimum {string} ve maksimum {string} girer")
    public void kullaniciFiyatiMinimumVeMaksimumGirer(String min, String max) {
        ReusableMethods.waitForClickablility(amazon.minPrice, 10);
        amazon.minPrice.sendKeys(min);
        ReusableMethods.waitFor(1);
        amazon.maxPrice.sendKeys(max);
        ReusableMethods.waitFor(1);
        amazon.goButton.click();
        ReusableMethods.waitFor(1);
    }

    @And("Kullanici gelen listeden ikinci urune tiklar")
    public void kullaniciGelenListedenIkinciUruneTiklar() {
        amazon.secondProduct.click();
        ReusableMethods.waitFor(1);
    }

    @And("Kullanici secilen urun bilgisi ve tutar bilgisi txt doyasina yazdirir")
    public void kullaniciSecilenUrunBilgisiVeTutarBilgisiTxtDoyasinaYazdirir() {
        System.out.println(amazon.productInfo.getText());
        secilenUrunFiyati = amazon.productPriceWhole.getText() + "," + amazon.productPriceFraction.getText() + " " + amazon.productPriceSymbol.getText();
        System.out.println("secilen urun fiyati: " + secilenUrunFiyati);

        String filePath = "src/test/resources/testdata/UrunBilgisi.txt";
        String yazdirilacakBilgi = "Ürün Bilgi\n" + amazon.productInfo.getText() + "\nFiyat: " + secilenUrunFiyati;
        //System.out.println(yazdirilacakBilgi);
        ReusableMethods.writeToText(filePath, yazdirilacakBilgi);
    }

    @And("Kullanici urunu sepete ekler")
    public void kullaniciUrunuSepeteEkler() {
        amazon.addToCartButton.click();
        ReusableMethods.waitFor(1);
    }

    @And("Kullanici sepet sayfasina gider")
    public void kullaniciSepetSayfasinaGider() {
        amazon.goToCartButton.click();
        ReusableMethods.waitFor(1);
    }

    @Then("Kullanici secilen urun ile sepette yer alan urun fiyatinin dogrulamasini yapar")
    public void kullaniciSecilenUrunIleSepetteYerAlanUrunFiyatininDogrulamasiniYapar() {
        System.out.println("secilen urun fiyati: " + secilenUrunFiyati);
        String sepettekiFiyat = amazon.cartPrice.getText();
        System.out.println("sepetteki fiyat: " + sepettekiFiyat);
        Assert.assertEquals(secilenUrunFiyati, sepettekiFiyat);
    }

    @Then("Kullanici miktari {string} secer sepette {string} oldugunu dogrular")
    public void kullaniciMiktariSecerSepetteOldugunuDogrular(String newQuantity, String expectedResult) {
        select = new Select(amazon.quantityDdm);
        select.selectByVisibleText(newQuantity);
        //Driver.getDriver().navigate().refresh();
        ReusableMethods.waitFor(1);
        String actualResult = amazon.twoProductResultText.getText();
        System.out.println(actualResult);
        Assert.assertTrue(actualResult.contains(expectedResult));
    }

    @Then("Kullanici urunu sepetten siler ve sepetin bos oldugunu kontrol eder")
    public void kullaniciUrunuSepettenSilerVeSepetinBosOldugunuKontrolEder() {
        amazon.deleteButton.click();
        String expectedResult = "Amazon sepetiniz boş.";
        String actualResult = amazon.emptyCartResultText.getText();
        System.out.println(actualResult);
        Assert.assertEquals(expectedResult, actualResult);
    }
}
