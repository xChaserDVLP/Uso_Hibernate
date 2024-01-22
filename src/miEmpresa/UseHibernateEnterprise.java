package miEmpresa;

import java.util.logging.*;


public class UseHibernateEnterprise {

	public static void main(String[] args) {
		
		LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);
		
		HibernateEnterprise h = new HibernateEnterprise();
		//h.addProduct(6, "Fuente Alimentacion", 100);
		h.showProducts();
		//h.findProdudctById(10);
		//h.deleteProductId(25);
		h.updateProductById(4, "Motherboard", 300);
		h.updateProductById(5, "Graphic card", 1500);
		h.showProducts();
		h.close();
	}
}
