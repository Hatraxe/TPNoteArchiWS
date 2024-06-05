package fr.univtours.polytech.bookshop.business;

import java.util.List;

import fr.univtours.polytech.bookshop.dao.BookDAO;
import fr.univtours.polytech.bookshop.model.BookBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class BookBusinessImpl implements BookBusiness {

    @Inject
    private BookDAO bookDAO;

    @Override
    public BookBean getBook(Integer id) {
        return this.bookDAO.getBook(id);
    }

    @Override
    public List<BookBean> getBooks() {
        return this.bookDAO.getBooks();
    }

    @Override
    public void createBook(BookBean bookBean) {
        this.bookDAO.createBook(bookBean);
    }

    @Override
    public void updateBook(BookBean bookBean) {
        this.bookDAO.updateBook(bookBean);
    }

    @Override
    public void removeBook(Integer id) {
        this.bookDAO.removeBook(id);
    }
}
