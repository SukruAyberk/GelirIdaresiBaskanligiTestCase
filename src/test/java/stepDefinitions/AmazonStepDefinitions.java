package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pages.AmazonPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;


import java.util.NoSuchElementException;

import java.util.concurrent.TimeoutException;

public class AmazonStepDefinitions {

    AmazonPage amazon = new AmazonPage();

    Select select;

    String secilenUrunFiyati;

    @Given("Kullanici {string} anasayfasinda")
    public void kullanici_anasayfasinda(String url) {
        Driver.getDriver().get(ConfigReader.getProperty(url));
        //cookie list al listeyi sorgula if içinde -_-
        try {
            amazon.cookie.click();
        } catch (NoSuchElementException e) {
            System.out.println("Cookie çıkmadı");
        }


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
    }

    @And("Kullanici sepet sayfasina gider")
    public void kullaniciSepetSayfasinaGider() {
        amazon.goToCartButton.click();
    }

    @Then("Kullanici secilen urun ile sepette yer alan urun fiyatinin dogrulamasini yapar")
    public void kullaniciSecilenUrunIleSepetteYerAlanUrunFiyatininDogrulamasiniYapar() {
        String sepettekiFiyat = amazon.cartPrice.getText();
        System.out.println("sepetteki fiyat: " + sepettekiFiyat);
        Assert.assertEquals(secilenUrunFiyati, sepettekiFiyat);
    }

    @Then("Kullanici miktari {string} secer sepette {string} oldugunu dogrular")
    public void kullaniciMiktariSecerSepetteOldugunuDogrular(String newQuantity, String expectedResult) {
        select = new Select(amazon.quantityDdm);
        select.selectByVisibleText(newQuantity);
        ReusableMethods.waitFor(1); // elementi göremediği için 1 saniye bekletildi
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
