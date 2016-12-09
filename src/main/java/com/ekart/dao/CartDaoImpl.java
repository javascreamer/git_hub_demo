package com.ekart.dao;

import com.ekart.dao.CartDao;
import com.ekart.model.Cart;
import com.ekart.service.OrderService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;



@Repository
@Transactional
public class CartDaoImpl implements CartDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private OrderService orderService;
    public Cart getCartById (int cartId) {
    	System.out.println("getCart by id is called before sessionfactory");
        Session session = sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
       
       Cart c=(Cart)session.get(Cart.class, cartId);
       tx.commit();
       System.out.println("getCart by id is called after transaction");
       return c;
    }
    public void update(Cart cart) {
        int cartId = cart.getCartId();
        double grandTotal = orderService.getOrderGrandTotal(cartId);
        cart.setGrandTotal(grandTotal);
        Session session = sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        session.saveOrUpdate(cart);
        tx.commit();
    }
    public Cart validate(int cartId) throws IOException {
        Cart cart=getCartById(cartId);
        System.out.println("cart id:"+cart.getCartId());
        if(cart==null||cart.getCartItems().size()==0) {
            throw new IOException(cartId+"");
        }
        update(cart);
        return cart;
    }
 }
