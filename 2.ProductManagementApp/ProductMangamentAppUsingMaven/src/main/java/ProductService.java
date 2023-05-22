import java.util.List;

public class ProductService {
    ProductDB db = new ProductDB();

//    Adding Products to List
    public void addProduct(Product product)
    {
        db.save(product);
    }


//    Fetching all the products from products list
    public List<Product> getAllProducts()
    {
        return db.getAll();
    }

//    Fetching product based on product name
    public Product getProductByName(String name)
    {
        return db.getProduct(name);
    }

//    Fetching all the matching products based on given text
    public List<Product> getProductsByText(String text)
    {
        return db.getProductsWithText(text);
    }

//    Fetching products based on place they are stored
    public List<Product> getProductsByPlace(String text)
    {
        return db.getProductsWithPlace(text);
    }

//    Fetching products which are currently out of Warranty
    public List<Product> getProductsOutOfWarranty()
    {
        return db.getProductsWithOutOfWarranty();
    }
}
