package fr.univtours.polytech.bookshop.dao;

import java.util.List;

import fr.univtours.polytech.bookshop.model.BookBean;

public interface BookDAO {

    public BookBean getBook(Integer id);

    public List<BookBean> getBooks();

    public void createBook(BookBean bookBean);

    public void updateBook(BookBean bookBean);

    public void removeBook(Integer id);
}
