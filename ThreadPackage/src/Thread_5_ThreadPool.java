package ThreadPackage.src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Thread_5_ThreadPool {
    /*
    Maximum No of threads to run in parallel = no of cores

    Thread Pool Executor:
    Creating a thread and reusing it again rather than recreating new threads every time.
    Once a thread is created it will sit in pool and tasks are distributed in a queue, free thread takes task from queue and executes it.
    If all threads are busy then the task will stay in queue until a new free thread takes it
    */

    public class HttpServerWithThreadPool {

        public static void main(String[] args) throws IOException {
            // Create the HTTP server
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            // Create a thread pool executor with a fixed pool size
            ExecutorService threadPool = Executors.newFixedThreadPool(10);

            // Assign the thread pool to the server
            server.setExecutor(threadPool);

            // Create a context to handle requests to "/"
            server.createContext("/", new RequestHandler());

            // Start the server
            System.out.println("Server is listening on port 8080...");
            server.start();
        }

        // Define the request handler
        static class RequestHandler implements HttpHandler {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "Hello, this is a response from the server!";

                // Set the response headers and status code
                exchange.sendResponseHeaders(200, response.getBytes().length);

                // Write the response body
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.flush();

                // Close the exchange
                outputStream.close();
            }
        }
    }

    /*
    Thread Assignment: The HttpServer internally uses the thread pool to assign requests to available threads. You do not have to manage this manually.
    Request Queueing: If the server receives more than 10 simultaneous requests, the extra requests will be queued until a thread becomes free.
    Scalability: The fixed thread pool size (10) can be adjusted based on your server's workload and resources.
    For high-concurrency scenarios, consider using a dynamically sized pool (Executors.newCachedThreadPool()) or tuning the fixed pool size.
    */
}

