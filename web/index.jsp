<%@ page import="utility.CookieChecker" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%
    if (request.getParameter("error") != null)
        response.sendRedirect("/signin?error=" + request.getParameter("error"));
    else
        response.sendRedirect("/signin");
%>