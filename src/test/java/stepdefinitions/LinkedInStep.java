package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.LinkedInPage;

public class LinkedInStep {
    private LinkedInPage pageLinkedIn = new LinkedInPage();

    private static String currentScenarioName;

    public LinkedInStep() {

    }

    @Before
    public void setUp(Scenario scenario) {
        currentScenarioName = scenario.getName();
    }


    @When("Ana sayfa butonuna tiklanir")
    public void anaSayfaButonunaTiklanir() {
        pageLinkedIn.cliquerAnaSayfa();
    }

    @Then("Ana sayfa buttonunun gorunur oldugu dogrulanir")
    public void anaSayfaButtonununGorunurOlduguDogrulanir() {
        //
    }

    @When("Ag butonuna tiklanir")
    public void agButonunaTiklanir() {
        pageLinkedIn.cliquerAg();
    }

    @Then("Ag butonunun aktif oldugu dogrulanir")
    public void agButonununAktifOlduguDogrulanir() {
        //
    }

    @When("Is ilanlari butonuna tiklanir")
    public void isIlanlariButonunaTiklanir() {
        pageLinkedIn.cliquerIsIlanlari();
    }

    @Then("Is ilanlari butonunun gorunur oldugu dogrulanir")
    public void isIlanlariButonununGorunurOlduguDogrulanir() {
        //
    }

    @When("Bildirimler butonuna tiklanir")
    public void bildirimlerButonunaTiklanir() {
        pageLinkedIn.cliquerBildirimler();
    }

    @Then("Bildirimler butonunun gorunur oldugu dogrulanir")
    public void bildirimlerButonununGorunurOlduguDogrulanir() {
        //
    }

    @When("Arama butonuna tiklanir")
    public void aramaButonunaTiklanir() throws InterruptedException {
        // pageLinkedIn.aramaYap();
    }

    @And("{string} kelimesi ile arama yapilir")
    public void kelimesiIleAramaYapilir(String aramaKelimesi) throws InterruptedException {
        pageLinkedIn.aramaYap(aramaKelimesi);
    }

    @Then("Arama sonuclarinin goruntulendigi dogrulanir")
    public void aramaSonuclarininGoruntulendigiDogrulanir() throws InterruptedException {
        //pageLinkedIn.aramaYap("");
    }



}