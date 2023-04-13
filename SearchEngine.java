import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    ArrayList<String> queryList = new ArrayList<>();
    ArrayList<String> matchQuery = new ArrayList<>();
    String searches = "";

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return """
                This is a simple search engine!
                Type \"/search\" or \"/?add\" with a query \"?s=<item>\" to begin.
                Have fun!
                """;
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                queryList.add(parameters[1]);
                return String.format("Added %s to query list!", parameters[1]);
            } else {
                return "Invalid path!";
            }
        } else if (url.getPath().contains("/search")) {
            String[] searchParam = url.getQuery().split("=");
            String query = searchParam[1];

            if (searchParam[0].equals("s")) {
                for (int i = 0; i < queryList.size(); i++) {
                    if (queryList.get(i).contains(query)) {
                        matchQuery.add(queryList.get(i));
                    }
                }

                if (matchQuery.size() > 0) {
                    for (int j = 0; j < matchQuery.size(); j++) {
                        searches = searches + String.format("Search result: %s\n", matchQuery.get(j));
                    }
                    return searches;
                } else {
                    return "No valid search results!";
                }
            }
        }
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
