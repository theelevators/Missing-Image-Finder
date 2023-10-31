import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner; // Import the Scanner class to read text files

public class ImageFinder {

    private String[] searchPaths;
    private ProductImage[] products;
    private String output;
    private String finalReportPath;

    public ImageFinder(String[] searchPaths) {
        this.searchPaths = searchPaths;
        return;
    }

    public void setFinalReportPath(String path) {
        this.finalReportPath = path;
        return;
    }

    public void setProducts(String file) {
        List<ProductImage> products = new ArrayList<ProductImage>();
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split("\\|", 0);
                ProductImage product = new ProductImage(dataList[0], dataList[1], dataList[2]);
                products.add(product);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        this.products = products.toArray(new ProductImage[0]);
    }

    public void findImages() {

        HashMap<String, String> filePaths = new HashMap<String, String>();
        HashMap<String, String[]> knownFiles = new HashMap<String, String[]>();

        for (ProductImage product : this.products) {
            FileFinder finder = new FileFinder(product.getFileName());
            String[] files = null;
            if ((files = knownFiles.get(product.getBrandCode())) != null) {

                for (String file : files) {

                    if (file.toLowerCase().endsWith(finder.getFileName())) {

                        product.setFlag(true);
                        product.setFilePath(filePaths.get(product.getBrandCode()));
                        break;
                    }
                }
                if (product.isFlagged()) {
                    continue;
                }

            }
            for (String path : this.searchPaths) {

                File folder = new File(path);
                finder.setStartPath(folder);
    
                Optional<File> target = finder.searchSubDirectory(finder.getStartPath());
                if (target.isPresent()) {

                    filePaths.put(product.getBrandCode(), target.get().getAbsolutePath());
                    knownFiles.put(product.getBrandCode(), finder.getFiles());
                    product.setFlag(true);
                    product.setFilePath(target.get().getAbsolutePath());
                    break;
                }
            }

        }

    }

    public void prepareOutput() {
        String output = new String("BrandCode|PartNumber|FileName|ImageFilePath\r\n");
        for (ProductImage product : this.products) {

            if (product.isFlagged()) {
                output = output + product.getBrandCode() + "|" + product.getPartNumber() + "|" + product.getFileName()
                        + "|" + product.getFilePath() + "\r\n";
            } else {

                output = output + product.getBrandCode() + "|" + product.getPartNumber() + "|" + product.getFileName()
                        + "|" + "No Image Found." + "\r\n";

            }

        }
        this.output = output;
    }

    public void createOutReport() {
        try {
            FileWriter myWriter = new FileWriter(
                    this.finalReportPath);
            myWriter.write(this.output);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void printImgPaths() {

        for (ProductImage product : this.products) {

            if (product.isFlagged()) {
                System.out.println(product.getFilePath() + "\n");
            } else {
                System.out.println("No Image found for: " + product.getFileName() + "\n");
            }

        }

    }

}
