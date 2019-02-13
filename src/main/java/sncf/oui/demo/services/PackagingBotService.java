package sncf.oui.demo.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import sncf.oui.demo.exceptions.PackagingBotException;
import sncf.oui.demo.models.Box;

import java.util.*;

/**
 * Article packaging service.
 *
 * @author khocef
 */
@Service
@AllArgsConstructor
@Slf4j
public class PackagingBotService {

    public String packageArticles(String articlesString) throws PackagingBotException {

        verifyArticles(articlesString);

        List<Integer> articles = getArticles(articlesString);

        List<Box> boxes = boxArticles(articles);

        return getOutputString(boxes);
    }

    /**
     * Format the list of box to string of boxes separated with "/".
     *
     * @param boxes the list of boxes
     * @return a string of boxes
     */
    private String getOutputString(List<Box> boxes) {
        StringJoiner joiner = new StringJoiner("/");
        boxes.forEach(box -> joiner.add(box.toString()));
        return joiner.toString();
    }

    /**
     * Package a list of articles in boxes.
     *
     * @param articles the list of articles
     * @return the list of boxes
     */
    private List<Box> boxArticles(List<Integer> articles) {
        // initialize the first box with the first article
        // and romove the article from the list
        List<Box> boxes = initBoxList(articles.remove(0));

        // loop over the articles
        // find a box with enough space to add the article
        // if no box found create a new one and add the article to it
        for (Integer article : articles) {
            Box box = getNextArticle(boxes, article);
            if (null != box) {
                log.debug("fitting artcile {} into box {}", article, box);
                List<Integer> boxArticles = box.getArticles();
                boxArticles.add(article);
                box.setArticles(boxArticles);
            } else {
                log.debug("no existing box with enough space found, creating new box for artcile {}", article);
                boxes.add(createBox(article));
            }
        }
        return boxes;
    }

    /**
     * Finds a box to fit the article.
     *
     * @param boxes   the boxes
     * @param article the article to fit in a box
     * @return a box with enough space for the article, otherwise returns null
     */
    private Box getNextArticle(List<Box> boxes, Integer article) {
        return boxes.stream()
                .filter(box -> box.hasEnoughSpace(article))
                .max(Comparator.comparingInt(Box::getBoxSize))
                .orElse(null);
    }

    /**
     * Create a new list of boxes and add a first article.
     *
     * @param article the first article to add to the first box of the list
     * @return a new list of boxes
     */
    private List<Box> initBoxList(Integer article) {
        ArrayList<Box> boxList = new ArrayList<>();
        boxList.add(createBox(article));
        return boxList;
    }

    /**
     * Create a box with an article.
     *
     * @param article the article to add to the box
     * @return a new box containing an article
     */
    private Box createBox(Integer article) {
        Box b = new Box();
        b.addArticle(article);
        return b;
    }

    /**
     * Verify if the input is in the correct format.
     *
     * @param articles the list of articles string
     * @throws PackagingBotException exception if the wrong input format
     */
    private void verifyArticles(String articles) throws PackagingBotException {
        if (StringUtils.isBlank(articles)) {
            throw new PackagingBotException("No articles to package");
        } else if (articles.contains(String.valueOf(NumberUtils.INTEGER_ZERO))) {
            throw new PackagingBotException("Articles of size 0 are not allowed");
        } else if (!StringUtils.isNumeric(articles)) {
            throw new PackagingBotException("Error parsing articles");
        }
    }

    /**
     * Transform a string of number to a list of integers.
     *
     * @param articles the list of articles string
     * @return List of integers
     */
    private List<Integer> getArticles(String articles) {
        //
        String[] splitedArticles = articles.split(StringUtils.EMPTY);

        ArrayList<Integer> articleList = new ArrayList<>();

        Arrays.stream(splitedArticles).forEach(el -> articleList.add(Integer.valueOf(el)));

        return articleList;
    }
}
