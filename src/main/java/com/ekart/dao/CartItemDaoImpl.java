package com.ekart.dao;

import com.ekart.dao.CartItemDao;
import com.ekart.model.Cart;
import com.ekart.model.CartItem;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public class CartItemDaoImpl implements CartItemDao{

    @Autowired
    private SessionFactory sessionFactory;
public void addCartItem(CartItem cartItem) {
    	System.out.println("Inside add cart item");
    	System.out.println(cartItem.getCartItemId());
        Session session = sessionFactory.getCurrentSession();
        try
        {
          Transaction tx=session.beginTransaction();	
        session.saveOrUpdate(cartItem);
        tx.commit();
        System.out.println("After session:"+cartItem.getCartItemId());
        }
        catch(Exception e)
        {
        	System.out.println("exception:"+e);
        }
        System.out.println("after save or update");
    }
    @Transactional
    public void removeCartItem (CartItem cartItem) {
        Session session = sessionFactory.getCurrentSession();
      Transaction tx=session.beginTransaction();
       
        System.out.println("In Remove Cart Item");
        System.out.println(cartItem.getCartItemId());
        try {
        	 session.delete(cartItem);
		} catch (Exception e) {
			System.out.println("Exception:"+e);
		}
       tx.commit();
 
        System.out.println("after remove cart item");
        session.flush();
    }

    public void removeAllCartItems(Cart cart) {
    	 System.out.println("In remove all Cart items");
         //System.out.println("cart item:"+cartItem);
        List<CartItem> cartItems = cart.getCartItems();
        System.out.println("cartItems:"+cartItems);
        for (CartItem item : cartItems) {
        	System.out.println("In for loop:"+item);
            removeCartItem(item);
            System.out.println("after deletion:"+item);
        }
    }

    public CartItem getCartItemByItemId (int itemId) {
    	System.out.println("Inside get cart item by item id");
        Session session = sessionFactory.getCurrentSession();
       Transaction tx= session.beginTransaction();
       System.out.println("item id got from the controller:"+itemId);
      Query query = session.createQuery("from CartItem where itemId="+itemId);
     System.out.println("After commit");
     CartItem cartItem=(CartItem) query.uniqueResult();
 System.out.println("cartItem:"+cartItem);
            System.out.println("cart item from database:"+cartItem);
        tx.commit();
        return cartItem;
    }
}
