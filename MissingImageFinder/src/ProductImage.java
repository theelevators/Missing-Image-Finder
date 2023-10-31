
public class ProductImage {
    private String brandCode;
    private String partNumber;
    private String fileName;
    private boolean flag;
    private String filePath;

    public ProductImage(String brandCode,  String partNumber,String fileName) {
        this.brandCode = brandCode.replace("ï»¿", "");
        this.partNumber = partNumber;
        this.fileName = fileName;
    }

    public String getBrandCode() {
        return this.brandCode;
    }

    public String getPartNumber() {
        return this.partNumber;
    }
    public String getFileName() {
        return this.fileName;
    }

    public void setFilePath(String path) {
        this.filePath = path;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public boolean isFlagged() {
        return this.flag;
    }

}
