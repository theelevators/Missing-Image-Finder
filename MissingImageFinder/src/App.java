import java.time.LocalDate;


public class App {

        public static void main(String[] args) {

                if (args.length < 2) {
                       

                        throw new RuntimeException("No valid input was given. Try again.");
                }
                String reportPath = args[0];
                String outputPath = args[1] + "\\" + LocalDate.now() + "_Missing_Image_Report.txt";

                String[] paths = {
                                "paths"
                };

                ImageFinder imageFinder = new ImageFinder(paths);

                imageFinder.setProducts(reportPath);
                imageFinder.setFinalReportPath(outputPath);

                imageFinder.findImages();

                imageFinder.prepareOutput();
                imageFinder.createOutReport();

        }

}
