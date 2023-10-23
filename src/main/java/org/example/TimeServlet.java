package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")

public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] timezones = req.getParameterValues("timezone");
        int offset = 0;
        if (timezones != null) offset = Integer.parseInt(timezones[0]
                .replace("UTC", "")
                .replace("UT", "")
                .replace("GMT", "")
                .replace("+", "")
                .trim());
        String sOffset = "";
        if (offset < 0) sOffset = String.valueOf(offset);
        else if (offset > 0) sOffset = "+" + offset;
        OffsetDateTime utcDateTime = OffsetDateTime.now(ZoneOffset.ofHours(offset));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDate = utcDateTime.format(formatter) + " UTC" + sOffset;

        resp.getWriter().println("<html>");
        resp.getWriter().println("<head>");
        resp.getWriter().println("<title>UTC Time Servlet</title>");
        resp.getWriter().println("</head>");
        resp.getWriter().println("<body><h1>" + formattedDate + "</h1></body>");
        resp.getWriter().println("</html>");
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().close();
    }
}
