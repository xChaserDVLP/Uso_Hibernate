package miEmpresa;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javassist.tools.rmi.ObjectNotFoundException;

public class HibernateEnterprise {

	private static SessionFactory sf;
	
	
	public HibernateEnterprise() {
		
		sf = new Configuration().configure().buildSessionFactory();
	}
	
	public void close() {
		sf.close();
	}
	
	public void addProduct(int id, String name, double price) {
		
		Session session = sf.openSession();
		Transaction tx = null;
		Productos p = new Productos();
		p.setNombre(name);
		p.setPrecio(price);
		p.setId(id);
		
		try {
			System.out.printf("Insertando Fila en BBDD: %s, %s, %s \n", id, name, price);
			tx = session.beginTransaction();
			session.save(p);
			tx.commit();
			
		} catch (Exception e) {
			if (tx!=null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
	}
	
	public void showProducts() {
		
		Session session = sf.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			List allProducts = session.createQuery("FROM Productos").list();
			Iterator it = allProducts.iterator();
			
			while(it.hasNext()) {
				Productos p = (Productos) it.next();
				System.out.print("Id: "+p.getId());
				System.out.print(" ,Name: "+p.getNombre());
				System.out.println(" ,Price: "+p.getPrecio());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
	}
	
	public Productos findProdudctById(int id) {
		
		Session session = sf.openSession();
		Transaction tx = null;
		Productos p = new Productos();
		
		try {
			System.out.println("Loading the object from the database");
			tx = session.beginTransaction();
			p = (Productos)session.load(Productos.class, id);
			tx.commit();
			System.out.println("The product with id = "+id+" is: "+p.getNombre());
			
		} catch (org.hibernate.ObjectNotFoundException e) {
			if(tx != null) {
				System.out.println(e);
				System.out.print("Product not found\n");
			}
		} catch (Exception e) {
			if(tx != null) {
				System.out.println(e);
				tx.rollback();
			}
		} finally {
			session.close();
		}
		return p;
	}
	
	public void deleteProductId(int id) {
		Productos p = new Productos();
		Session session = sf.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			p = (Productos)session.load(Productos.class, id);
			session.delete(p);
			System.out.printf("Object deleted FROM THE DATABASE: %s, %s, %s \n", p.getId(), p.getNombre(), p.getPrecio());
		} catch (org.hibernate.ObjectNotFoundException e) {
			if(tx != null) {
				System.out.print("Product not found with id: "+id+". Deleting cannot be completed");
			}
		} catch (Exception e) {
			if(tx != null) {
				System.out.println(e);
				tx.rollback();
			}
		} finally {
			session.close();
		}
	}
	
	public void updateProductById(int id, String newName, double newPrice) {
		
		Productos p = new Productos();
		Session session = sf.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			System.out.println("Updating a value");
			System.out.println("Before updating, we need to load the object");
			p = (Productos)session.load(Productos.class, id);
			
			p.setPrecio(newPrice);
			p.setNombre(newName);
			session.update(p);
			tx.commit();
			System.out.println("Object Update");
		} catch (org.hibernate.ObjectNotFoundException e) {
			
			if (tx != null) {
				System.out.println("Product not found with id: "+id+". Updating cannot be completed");
				tx.rollback();
			}
		} finally {
			session.close();
		}
		
	}
}
		
		
		
		

