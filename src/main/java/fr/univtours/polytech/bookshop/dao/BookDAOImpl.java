package fr.univtours.polytech.bookshop.dao;

import java.util.List;

import fr.univtours.polytech.bookshop.model.BookBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class BookDAOImpl implements BookDAO {

    @PersistenceContext(unitName = "TpRestDB")
    EntityManager em;;

    @Override
    public BookBean getBook(Integer id) {
        return em.getReference(BookBean.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BookBean> getBooks() {
        Query requete = em.createNativeQuery("select * from BOOK", BookBean.class);
        return requete.getResultList();
    }

    @Override
    public void createBook(BookBean bookBean) {
        em.persist(bookBean);
    }

    @Override
    public void updateBook(BookBean bookBean) {
        em.persist(bookBean);
    }

    @Override
    public void removeBook(Integer id) {
        em.remove(this.getBook(id));
    }
}
