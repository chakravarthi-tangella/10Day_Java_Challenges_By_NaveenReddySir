import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    List<Product> products = new ArrayList<>();

//    Adding Products to List
    public void addProduct(Product product)
    {
        products.add(product);
    }

//    Fetching all the products from products list
    public List<Product> getAllProducts()
    {
        return products;
    }

//    Fetching product based on product name
    public Product getProductByName(String name)
    {
        return products.stream().filter(product -> product.getName().equals(name)).findFirst().orElse(null);
    }

//    Fetching all the matching products based on given text
    public List<Product> getProductsByText(String text)
    {
        String str = text.toLowerCase();

        return products.stream().filter(product ->  product.getName().toLowerCase().contains(str) ||
                                                    product.getType().toLowerCase().contains(str) ||
                                                    product.getPlace().toLowerCase().contains(str))
                                                    .collect(Collectors.toList());
    }

//    Fetching products based on place they are stored
    public List<Product> getProductsByPlace(String text)
    {
        String str = text.toLowerCase();

        return products.stream().filter(product -> product.getPlace().toLowerCase().equals(str)).collect(Collectors.toList());
    }

//    Fetching products which are currently out of Warranty
    public List<Product> getProductsOutOfWarranty()
    {
        int year = Year.now().getValue();

        return products.stream().filter(product -> product.getWarranty() < year).collect(Collectors.toList());
    }
}
