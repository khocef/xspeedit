package sncf.oui.demo.resources;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sncf.oui.demo.exceptions.PackagingBotException;
import sncf.oui.demo.services.PackagingBotService;

/**
 * Api for packaging articles in boxes.
 * Exemple
 * <p>
 * Articles      : 163841689525773
 * Robot actuel  : 163/8/41/6/8/9/52/5/7/73 => 10 cartons utilisés
 * Robot optimisé: 163/82/46/19/8/55/73/7   => 8  cartons utilisés
 * <p>
 *
 * @author khocef
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = "/package")
public class PackagingBotResource {

    /**
     * Packaging service.
     */
    private PackagingBotService packagingBotService;

    @GetMapping
    @ResponseBody
    public String packageArticles(@RequestParam("articles") final String articles) throws PackagingBotException {
        log.debug("Packaging Articles {}", articles);
        return packagingBotService.packageArticles(articles);
    }

}
