
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class FileFinder {

        private String fileName;
        private String mfgCode;
        private File currentDirectory;
        private File startPath;
        private String[] files;
        private Boolean hasFiles;

        public FileFinder(String searchFile) {

                this.mfgCode = searchFile.substring(0, 3);
                this.fileName = searchFile.substring(4, searchFile.length() - 4);
        }

        public String getFileName() {
                return this.fileName;
        }

        public String getMfgCode() {
                return this.mfgCode;
        }

        public File getCurrentDirectory() {
                return this.currentDirectory;
        }

        public File getStartPath() {
                return this.startPath;
        }

        public void setHasFiles() {
                this.hasFiles = true;
        }

        public Boolean hasFiles() {
                return this.hasFiles;
        }

        public String[] getFiles() {
                return this.files;
        }

        public void setCurrentDirectory(File directory) {
                this.currentDirectory = directory;

        }

        public void setStartPath(File directory) {
                File[] directories = directory.listFiles(File::isDirectory);
                for (File dir : directories) {

                        if (dir.getName().toLowerCase().contains("(" + this.mfgCode + ")")
                                        || (dir.getName().compareToIgnoreCase(this.mfgCode) == 0)) {

                                this.startPath = dir;
                                return;
                        }
                        ;

                }
        }

        public Boolean hasSubDir() {
                File[] subs = this.currentDirectory.listFiles(File::isDirectory);
                if (subs.length > 1) {
                        return true;
                }
                return false;
        }

        public Boolean isZip() {
                if (this.currentDirectory.getName().toLowerCase().endsWith(".zip")) {
                        return true;
                }
                return false;
        }

        public Optional<File> searchZip() {

                this.zipSearch(this.currentDirectory.getAbsolutePath(), this.fileName);
                if (this.hasFiles) {

                        for (String file : this.files) {
                                if (file.toLowerCase().endsWith(this.fileName)) {
                                        return Optional.of(this.currentDirectory);
                                }
                        }
                }

                return Optional.empty();
        }

        public void zipSearch(String zipFolder, String fileName) {

                String shellCmd = "powershell \"script path\" -sourceFile \"'"
                                + zipFolder.replace(" ", "space").replace("'", "tick")
                                + "'\"";

                try {
                        Process shell = Runtime.getRuntime()
                                        .exec(shellCmd);
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(shell.getInputStream()));
                        BufferedReader stdError = new BufferedReader(new InputStreamReader(shell.getErrorStream()));
                        // Read the output from the command
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {

                                String[] files = s.split(",");

                                this.files = files;
                                this.setHasFiles();
                                return;
                        }
                        // Read any errors from the attempted command
                        while ((s = stdError.readLine()) != null) {
                                System.out.println(s);

                        }
                } catch (IOException e) {

                        e.printStackTrace();
                }

        }

        public Optional<File> searchSubDirectory(File directory) {
                if (directory == null) {
                        return Optional.empty();
                }

                this.currentDirectory = directory;

                File[] files = this.currentDirectory.listFiles();
                for (File sub : files) {
                        this.currentDirectory = sub;

                        if (this.currentDirectory.isFile()) {
                                String compareName = this.currentDirectory.getName().substring(0,
                                                this.currentDirectory.getName().length() - 4);
                                if ((compareName.compareToIgnoreCase(this.fileName) == 0)) {
                                        return Optional.of(this.currentDirectory);
                                }

                        }
                        if (this.currentDirectory.isDirectory()) {
                                Optional<File> target = this.searchSubDirectory(this.currentDirectory);
                                if (target.isPresent()) {
                                        return target;

                                }
                        }
                        if (this.isZip()) {

                                Optional<File> zipResults = this.searchZip();
                                if (zipResults.isPresent()) {
                                        return zipResults;
                                }
                        }

                }

                return Optional.empty();
        }
}
