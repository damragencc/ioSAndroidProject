Feature: LinkedIn Mobil Uygulamasi Temel Fonksiyonlari

  Scenario: LinkedIn temel fonksiyonlarini test etme
    Given LinkedIn uygulamasi acik durumda
    When Ana sayfa butonuna tiklanir
    Then Ana sayfa buttonunun gorunur oldugu dogrulanir
    When Ag butonuna tiklanir
    Then Ag butonunun aktif oldugu dogrulanir
    When Is ilanlari butonuna tiklanir
    Then Is ilanlari butonunun gorunur oldugu dogrulanir
    When Bildirimler butonuna tiklanir
    Then Bildirimler butonunun gorunur oldugu dogrulanir
    When Arama butonuna tiklanir
    And "QA Engineer" kelimesi ile arama yapilir
    Then Arama sonuclarinin goruntulendigi dogrulanir