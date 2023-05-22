import java.util.List;

public class ProductManagement {
    public static void main(String[] args) {

        ProductService service = new ProductService();

        // Adding products
        service.addProduct(new Product("Type C", "Cable", "Black Drawer", 2024));
        service.addProduct(new Product("Mac Studio", "Computer", "White Table", 2025));
        service.addProduct(new Product("Focusrite Mixer", "Audio System", "White Table", 2025));
        service.addProduct(new Product("Asus Vivobook", "Laptop", "Brown Drawer", 2021));
        service.addProduct(new Product("Asus Rog", "Laptop", "Black Table", 2021));
        service.addProduct(new Product("Macbook pro", "Laptop", "Brown Drawer", 2022));
        service.addProduct(new Product("Wacom Pad", "Writing Pad", "Black Drawer", 2020));
        service.addProduct(new Product("Apple Keyboard", "Keyboard", "White Table", 2022));
        service.addProduct(new Product("Logitech Keyboard", "Keyboard", "Black Table", 2024));
        service.addProduct(new Product("Hdmi cable", "Cable", "Black Drawer", 2024));
        service.addProduct(new Product("Java Black Book", "Cable", "Shelf", 2024));
        service.addProduct(new Product("Logi Mouse", "Mouse", "Black Table", 2022));
        service.addProduct(new Product("Apple Mouse", "Mouse", "White Table", 2022));
        service.addProduct(new Product("Lenovo Mouse", "Mouse", "Black Drawer", 2022));
        service.addProduct(new Product("BlackBeast", "Computer", "White Table", 2020));

        //  Printing all the products

        System.out.println("All Products list :");
        List<Product> products = service.getAllProducts();
        printList(products);

        System.out.println("========================================================================================");

        //  Printing product based on product name

        System.out.println("Printing Product based on given Product name");
        Product product = service.getProductByName("Apple Mouse");
        System.out.println(product);

        System.out.println("========================================================================================");

        //  Printing products matching with given text

        System.out.println("Products matching with given text :");
        List<Product> products1 = service.getProductsByText("Black");
        printList(products1);

        System.out.println("========================================================================================");

        //  Printing products stored in given place

        System.out.println("Products stored in given place :");
        List<Product> products2 = service.getProductsByPlace("White Table");
        printList(products2);

        System.out.println("========================================================================================");

        //  Printing products which are out of warranty

        System.out.println("Products that are out of warranty :");
        List<Product> products3 = service.getProductsOutOfWarranty();
        printList(products3);
    }

    public static void printList(List<Product> products)
    {
        for (int i=0; i<products.size();i++)
        {
            System.out.println((i+1) + ". " + products.get(i));
        }
    }

}
