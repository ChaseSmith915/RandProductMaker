import java.io.Serializable;

public class Product implements Serializable {
    private String IDNum;
    private String productName;
    private String productDesc;
    private double productCost;


    public Product(String IDNum, String productName, String productDesc, double productCost)
    {
        this.IDNum = IDNum;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productCost = productCost;
    }
    public String getIDNum() {
        return IDNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public double getProductCost() {
        return productCost;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }

    public String toCSVDataRecord()
    {
        return IDNum + ", " + productName + ", " + productDesc + ", " + productCost;
    }
}
