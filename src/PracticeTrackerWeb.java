import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PracticeTrackerWeb {
    private static List<String> problems = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Handle homepage
        server.createContext("/", exchange -> {
            String response = """
                <html>
                <head><title>Practice Tracker - Hanumesh K</title></head>
                <body style='font-family:sans-serif;'>
                    <h2>Programming Practice Tracker</h2>
                    <form method='POST' action='/add'>
                        <label>Title:</label><br>
                        <input name='title' required><br>
                        <label>Topic:</label><br>
                        <input name='topic' required><br>
                        <label>Difficulty:</label><br>
                        <input name='difficulty' required><br>
                        <label>Code Snippet:</label><br>
                        <textarea name='code' rows='4' cols='50'></textarea><br><br>
                        <button type='submit'>Add Problem</button>
                    </form>
                    <hr>
                    <a href='/view'>View All Problems</a>
                </body></html>
            """;
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });

        // Handle adding problem
        server.createContext("/add", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                String formData = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                problems.add(formData.replace("&", "\n").replace("=", ": "));
                saveToFile();
                String response = "<html><body><h3>✅ Problem Added!</h3><a href='/'>Back</a></body></html>";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                exchange.getResponseBody().write(response.getBytes());
            }
            exchange.close();
        });

        // Handle viewing problems
        server.createContext("/view", exchange -> {
            StringBuilder html = new StringBuilder("<html><body><h2>All Problems</h2>");
            if (problems.isEmpty()) html.append("No problems added yet.");
            else {
                for (String p : problems) {
                    html.append("<pre>").append(p).append("</pre><hr>");
                }
            }
            html.append("<a href='/'>Back</a></body></html>");
            byte[] resp = html.toString().getBytes();
            exchange.sendResponseHeaders(200, resp.length);
            exchange.getResponseBody().write(resp);
            exchange.close();
        });

        server.start();
        System.out.println("🚀 Server running at http://localhost:8080");
    }

    private static void saveToFile() {
        try (FileWriter writer = new FileWriter("problems.txt")) {
            for (String p : problems) writer.write(p + "\n\n");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
