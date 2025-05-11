

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Access and use the HttpServletRequest object
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        // ... other processing logic

        // Example response
        response.getWriter().write("Request received");
    }
}