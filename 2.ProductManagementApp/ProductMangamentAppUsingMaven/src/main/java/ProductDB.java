import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class ProductDB {

    Connection conn = null;

    public ProductDB()
    {
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Product product)
    {
        String query = "insert into product(name, type, place, warranty) values(?,?,?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setString(2, product.getType());
            ps.setString(3, product.getPlace());
            ps.setInt(4, product.getWarranty());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();

        String query = "select name,type,place,warranty from product";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Product p = new Product();
                p.setName(rs.getString(1));
                p.setType(rs.getString(2));
                p.setPlace(rs.getString(3));
                p.setWarranty(rs.getInt(4));
                products.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    public Product getProduct(String pName)
    {
        Product p = new Product();
        String query = "select name,type,place,warranty from product where name=?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,pName);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                p.setName(rs.getString(1));
                p.setType(rs.getString(2));
                p.setPlace(rs.getString(3));
                p.setWarranty(rs.getInt(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    public List<Product> getProductsWithText(String text)
    {
        List<Product> products = new ArrayList<>();

        String query = "select name,type,place,warranty from product where name like ? or type like ? or place like ?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            String matchingText = "%" + text + "%";
            ps.setString(1,matchingText);
            ps.setString(2,matchingText);
            ps.setString(3,matchingText);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Product p = new Product();
                p.setName(rs.getString(1));
                p.setType(rs.getString(2));
                p.setPlace(rs.getString(3));
                p.setWarranty(rs.getInt(4));
                products.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    public List<Product> getProductsWithPlace(String text) {
        List<Product> products = new ArrayList<>();

        String query = "select name,type,place,warranty from product where place=?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,text);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Product p = new Product();
                p.setName(rs.getString(1));
                p.setType(rs.getString(2));
                p.setPlace(rs.getString(3));
                p.setWarranty(rs.getInt(4));
                products.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    public List<Product> getProductsWithOutOfWarranty() {
        List<Product> products = new ArrayList<>();

        String query = "select name,type,place,warranty from product where warranty<?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            int year = Year.now().getValue();
            ps.setInt(1,year);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Product p = new Product();
                p.setName(rs.getString(1));
                p.setType(rs.getString(2));
                p.setPlace(rs.getString(3));
                p.setWarranty(rs.getInt(4));
                products.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }
}
