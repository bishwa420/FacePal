/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.demo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author Tapos
 */
@WebServlet(name = "MyServletPack", urlPatterns = {"/MyServletPack"})

public class MyServletPack extends HttpServlet {
    
    
    
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
            
    public static final String basePath="F:\\project300Backup";
    
    public Conversion conversion;
    public static Object[] responsedObject;
    
     public int flag=0;
     private Object ob[];
     private String getImgstr;
    
     
     
     

     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
          
          
        
        
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        try {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet MyServletPack</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet MyServletPack at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        } finally {
//            out.close();
//        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("application/x-java-serialized-object");
                        // read a String-object from applet
			// instead of a String-object, you can transmit any object, which
			// is known to the servlet and to the applet
			InputStream in = request.getInputStream();
			ObjectInputStream inputFromApplet = new ObjectInputStream(in);
			//String echo="";
                        System.out.println("sdjhskdjfsdfsakdjfhskjdaf");
                        try {
                            
                          ob = (Object[]) inputFromApplet.readObject();
                          Analysis.check(ob);

                          
                          System.out.println("Successfull" );
                          
                          
                          
                           System.out.println("Successfull 1");
                          

			OutputStream outstr = response.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outstr);
			oos.writeObject(responsedObject);
			oos.flush();
			oos.close();
                            System.out.println("hello");
                        
                         } catch (ClassNotFoundException ex) {
                            Logger.getLogger(MyServletPack.class.getName()).log(Level.SEVERE, null, ex);
                        }

        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>

}
