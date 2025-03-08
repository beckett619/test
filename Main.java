public class Main {
    public static void main(String[] args) {
        //String filePath = "/home/beckett/gitcode/test";
        String linuxPath = "/home/$USER/gitcode/test";
        String winPath = "/home/%USER%/gitcode/test";
        //sdasda
        System.out.println("initial linux path = "+linuxPath);
        String finalPath = getFinalPath(linuxPath);
        System.out.println("final linux path = "+finalPath);

        System.out.println("initial windows path = "+winPath);
        finalPath = getFinalPath(winPath);
        System.out.println("final windows path = "+winPath);

    }

    public static Boolean hasEnvVar(String fPath) { //Evaluate if string contains environmental variable
        if (fPath.contains("$") && (systemDistribution().equals("linux"))) {
            return true;
        } else if (fPath.contains("%") && (systemDistribution().equals("windows"))) {
            return true;
        }else {
            return false;
        }
    }

    public static String systemDistribution() { //Identifies system as linux or windows
        String oS = System.getProperty("os.name").toLowerCase();
        if (oS.contains("linux")) {
            return ("linux");
        }else if (oS.contains("win")) {
            return ("windows");
        }
        else {
            return ("unsupported");
        }
    }

    public static String processEnvVar(String fPath, String oS) { //Resolves environmental variables in path
        StringBuilder processedPath = new StringBuilder();
        String replace = ""; //Character to remove from environmental variable
        if (oS.equals("linux")){
            replace = "$";
        } else if (oS.equals("windows")) {
            replace = "%";
        }
        String[] splitPath = fPath.split("/");
        int index = 0;
        for (String dir : splitPath) { //evaluate each subdirectory in path for environmental variables
            if (index != 0){
                processedPath.append("/");
            }
            if (hasEnvVar(dir)){ //path directory contains environmental variable
                String stripped = dir.replace(replace, "");
                String envVar = System.getenv(stripped);
                if (envVar!= null){
                    processedPath.append(envVar);
                }else{
                    processedPath.append(dir);
                }
            }else{ //dir is not an environmental variable
                processedPath.append(dir);
            }
            index++;
        }
        return processedPath.toString();
    }

    public static String getFinalPath (String fPath){ //get final path (with or without environmental variables)
        if (hasEnvVar(fPath)){
            String oS = systemDistribution();
            fPath = processEnvVar(fPath, oS);
            return fPath;
        }else{
            return fPath;
        }

    }

}