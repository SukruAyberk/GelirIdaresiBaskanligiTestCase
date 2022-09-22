package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pages.AmazonPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;


import java.util.NoSuchElementException;

import java.util.Set;
import java.util.concurrent.TimeoutException;

public class AmazonStepDefinitions {

    AmazonPage amazon = new AmazonPage();

    Select select;

    String secilenUrunFiyati;

    @Given("Kullanici {string} anasayfasinda")
    public void kullanici_anasayfasinda(String url) {
        Driver.getDriver().get(ConfigReader.getProperty(url)); // singleton pattern
        ReusableMethods.cookieHandle(amazon.cookie); // cookieleri handle edebilmek için oluşturduğum method
    }

    @Then("Kullanici arama kutusuna {string} girer")
    public void kullaniciAramaKutusunaGirer(String word) {
        amazon.searchBox.sendKeys(word);
    }

    @And("Kullanici search butonuna tiklar")
    public void kullaniciSearchButonunaTiklar() {
        amazon.searchButton.click();
    }

    @And("Kullanici marka {string} secer")
    public void kullaniciMarkaSecer(String aranacakMarka) {
        WebElement brand = Driver.getDriver().findElement(By.xpath("//*[text()='" + aranacakMarka + "']"));
        ReusableMethods.waitForClickablility(brand, 10);
        brand.click();
    }

    @And("Kullanici fiyati minimum {string} ve maksimum {string} girer")
    public void kullaniciFiyatiMinimumVeMaksimumGirer(String min, String max) {
        ReusableMethods.waitForClickablility(amazon.minPrice, 10);
        amazon.minPrice.sendKeys(min);
        amazon.maxPrice.sendKeys(max);
        amazon.goButton.click();
    }

    @And("Kullanici gelen listeden ikinci urune tiklar")
    public void kullaniciGelenListedenIkinciUruneTiklar() {
        amazon.secondProduct.click();
    }

    @And("Kullanici secilen urun bilgisi ve tutar bilgisi txt doyasina yazdirir")
    public void kullaniciSecilenUrunBilgisiVeTutarBilgisiTxtDoyasinaYazdirir() {
        // ürün stokta kalmayacağı düşünülerek dinamik bir yapı oluşturmaya çalıştım
        secilenUrunFiyati = amazon.productPriceWhole.getText() + "," + amazon.productPriceFraction.getText() + " " + amazon.productPriceSymbol.getText();
        String filePath = "src/test/resources/testdata/UrunBilgisi.txt";
        String yazdirilacakBilgi = "Ürün Bilgi\n" + amazon.productInfo.getText() + "\nFiyat: " + secilenUrunFiyati;

        // bilgileri txt uzantılı dosyaya yazdırmak için oluşturduğum method
        ReusableMethods.writeToText(filePath, yazdirilacakBilgi);
    }

    @And("Kullanici urunu sepete ekler")
    public void kullaniciUrunuSepeteEkler() {
        amazon.addToCartButton.click();
    }

    @And("Kullanici sepet sayfasina gider")
    public void kullaniciSepetSayfasinaGider() {
        amazon.goToCartButton.click();
    }

    @Then("Kullanici secilen urun ile sepette yer alan urun fiyatinin dogrulamasini yapar")
    public void kullaniciSecilenUrunIleSepetteYerAlanUrunFiyatininDogrulamasiniYapar() {
        String sepettekiFiyat = amazon.cartPrice.getText();
        Assert.assertEquals(secilenUrunFiyati, sepettekiFiyat);
    }

    @Then("Kullanici miktari {string} secer sepette {string} oldugunu dogrular")
    public void kullaniciMiktariSecerSepetteOldugunuDogrular(String newQuantity, String expectedResult) {
        select = new Select(amazon.quantityDdm);
        select.selectByVisibleText(newQuantity);
        ReusableMethods.waitFor(1); // elementi göremediği için 1 saniye bekletildi
        String actualResult = amazon.twoProductResultText.getText();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }

    @Then("Kullanici urunu sepetten siler ve sepetin bos oldugunu kontrol eder")
    public void kullaniciUrunuSepettenSilerVeSepetinBosOldugunuKontrolEder() {
        amazon.deleteButton.click();
        Assert.assertTrue(amazon.emptyCartResultText.isDisplayed());
    }
}
