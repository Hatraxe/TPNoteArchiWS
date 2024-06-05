package fr.univtours.polytech.bookshop.utils;

import fr.univtours.polytech.bookshop.business.BookBusiness;
import fr.univtours.polytech.bookshop.model.BookBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Singleton
@Startup
public class DataBaseInitializer {

    @Inject
    private BookBusiness bookBusiness;

    @PostConstruct
    public void init() {
        BookBean bean1 = insertBeanInDB("The Lord of the Rings", "J.R.R. Tolkien", 10.5F, "NZD");
        bookBusiness.createBook(bean1);

        BookBean bean2 = insertBeanInDB("La Nuit des temps", "René Barjavel", 5.5F, "CHF");
        bookBusiness.createBook(bean2);

        BookBean bean3 = insertBeanInDB("Death on the Nile", "Agatha Christie", 15F, "GBP");
        bookBusiness.createBook(bean3);

        BookBean bean4 = insertBeanInDB("L'anomalie", "Hervé Le Tellier", 12F, "EUR");
        bookBusiness.createBook(bean4);

        BookBean bean5 = insertBeanInDB("Blabla", "Bli Blu", null, "EUR");
        bookBusiness.createBook(bean5);

        BookBean bean6 = insertBeanInDB("Stupeur et Tremblements", "Amélie Nothomb", 7.2F, "EUR");
        bookBusiness.createBook(bean6);

        BookBean bean7 = insertBeanInDB("Métaphysique des tubes", "Amélie Nothomb", 7F, "EUR");
        bookBusiness.createBook(bean7);
    }

    /**
     * Permet d'insérer un enregistrement en BDD.
     */
    private BookBean insertBeanInDB(String title, String author, Float price, String currency) {
        BookBean bean = new BookBean();

        bean.setTitle(title);
        bean.setAuthor(author);
        bean.setPrice(price);
        bean.setCurrency(currency);

        return bean;
    }
}
