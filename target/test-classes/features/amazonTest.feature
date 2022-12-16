@amazon
Feature: Gelir Idaresi Baskanligi Test Case

  Scenario: Kullanici amazon sayfasinda bilgisayar aratir, sepete ekler ve sepetten cikarir
    Given Kullanici "amazonUrl" anasayfasinda
    And Kullanici arama kutusuna "Bilgisayar" girer
    And Kullanici search butonuna tiklar
    And Kullanici marka "MONSTER" secer
    And Kullanici fiyati minimum "15000" ve maksimum "30000" girer
    And Kullanici gelen listeden ikinci urune tiklar
    And Kullanici secilen urun bilgisi ve tutar bilgisi txt doyasina yazdirir
    And Kullanici urunu sepete ekler
    And Kullanici sepet sayfasina gider
    Then Kullanici secilen urun ile sepette yer alan urun fiyatinin dogrulamasini yapar
    Then Kullanici miktari "2" secer sepette "2 ürün" oldugunu dogrular
    Then Kullanici urunu sepetten siler ve sepetin bos oldugunu kontrol eder