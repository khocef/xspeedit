package sncf.oui.demo.services;

import org.junit.Test;
import sncf.oui.demo.exceptions.PackagingBotException;

import static org.junit.Assert.assertEquals;

public class PackagingBotServiceTest {

    private final PackagingBotService service = new PackagingBotService();

    @Test(expected = PackagingBotException.class)
    public void packageArticlesNoArticlesToPackageTest() throws PackagingBotException {
        service.packageArticles("");
    }

    @Test(expected = PackagingBotException.class)
    public void packageArticlesNoArticlesOfSizeZeroAllowedTest() throws PackagingBotException {
        service.packageArticles("847120487303");
    }

    @Test(expected = PackagingBotException.class)
    public void packageArticlesNotNumericTest() throws PackagingBotException {
        service.packageArticles("8473487353A");
    }

    @Test()
    public void packageArticles() throws PackagingBotException {
        String packageArticles = service.packageArticles("163841689525773");
        assertEquals("163/81/46/82/9/55/73/7", packageArticles);
    }


}