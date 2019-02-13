package sncf.oui.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a box with list of articles.
 *
 * @author khocef
 */
@Data
@NoArgsConstructor
public class Box {

    /**
     * Maximum capacity of
     */
    public static final int MAX_SIZE = 10;

    /**
     * List of articles.
     */
    private List<Integer> articles = new ArrayList<>();

    /**
     * Add an artcile to the box.
     *
     * @param article the article to add
     */
    public void addArticle(Integer article) {
        articles.add(article);
    }

    /**
     * Verify if the box has enough space to add articles.
     *
     * @param article to article to add
     * @return true if article can be added, false otherwise
     */
    public boolean hasEnoughSpace(Integer article) {
        return getBoxSize() + article <= MAX_SIZE;
    }

    /**
     * Return the box size by summing up articles.
     *
     * @return the sum of the articles
     */
    public int getBoxSize() {
        return articles.stream().mapToInt(value -> value).sum();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        articles.forEach(builder::append);
        return builder.toString();
    }
}
