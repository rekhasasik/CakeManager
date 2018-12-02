package com.waracle.cakemgr;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/cakes")
public class CakeServlet extends HttpServlet {

    /**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 2353236050171076725L;
	
	private final String CONTENT_TYPE_JSON = "application/json";
	private final String CONTENT_TYPE_HTML = "text/html";

	@Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        System.out.println("init started");


        System.out.println("downloading cake json");
        
        Properties systemSettings = System.getProperties();          
        systemSettings.put("proxySet", "true");         
        systemSettings.put("http.proxyHost", "www-proxy.us.oracle.com");          
        systemSettings.put("http.proxyPort", "80");       
        systemSettings.put("https.proxyHost", "www-proxy.us.oracle.com");          
        systemSettings.put("https.proxyPort", "80");           
        
        try  {
        	ObjectMapper mapper = new ObjectMapper();
    		//List<Cake> myObjects = mapper.readValue(new URL("https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json"), new TypeReference<List<Cake>>(){});
    		List<CakeEntity> myObjects = mapper.readValue(new URL("https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json"), new TypeReference<List<CakeEntity>>(){});
    		
    		System.out.println("Number of Cakes: "+myObjects.size());
    		
    		//CakeStore cs = CakeStore.getInstance();
    		
    		for(CakeEntity cake : myObjects) {
    			//cs.addCake(cake);
    			addCake(cake);
    		}
    		

        } catch (Exception ex) {
        	if(ex instanceof ConstraintViolationException) {
        		//ignore
        	}
        	else {
        		throw new ServletException(ex);
        	}
        }

        System.out.println("init finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	System.out.println("Do Get");
    	try {
	    	if (isJson(req)) {
	    		processJsonRequest(req, resp);
	    	}
	    	
	    	else if (isBrowser(req)) { 
	             processBrowserRequest(req, resp);
	
	        } else {
	             resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported content-type " + req.getContentType());
	        }
    	}catch(Throwable e) {
    		resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    	}
        
        

    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	System.out.println("Do Post");
    	
    	String title = req.getParameter("title-input");
    	String desc = req.getParameter("desc-input");
    	String image = req.getParameter("image-input");
    	System.out.println("**"+desc);
    	CakeEntity cake = new CakeEntity();
    	cake.setDesc(desc);
    	cake.setTitle(title);
    	cake.setImage(image);
    	try {
    		addCake(cake);
    		resp.setStatus(200);
    	} catch(Throwable t) {
    		if(t instanceof  ConstraintViolationException) {
    			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Duplicate Cake "+cake.getTitle());
    		}
    		else {
    			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, t.getMessage());
    		}
    	}
    	
               
    	
    }
    
    
   
    private void addCake(CakeEntity object) {
       
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(object);
            System.out.println("adding cake entity: " + object);
            session.getTransaction().commit();

        } catch (ConstraintViolationException ex) { //Avoid Duplicates
            System.err.println(ex.getMessage() + ",\n Duplicate found for " + object.getTitle());
            throw ex;
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
        	System.out.println("Added");
            HibernateUtil.close(session);
        }
    }
    
    
    private List<CakeEntity> getCakeList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<CakeEntity> list = session.createCriteria(CakeEntity.class).list();
        HibernateUtil.close(session);
        return list;
    }
    
    
    /**
     * Returns true if the given request content type is null or content type is text/html 
     */
    private boolean isBrowser(HttpServletRequest req) {
        return (req.getContentType() == null || CONTENT_TYPE_HTML.equalsIgnoreCase(req.getContentType()));
    }

    /**
     * Returns true if the given request content type is application/json
     * @param req
     * @return boolean
     */
    private boolean isJson(HttpServletRequest req) {
        return CONTENT_TYPE_JSON.equalsIgnoreCase(req.getContentType());
    }
    
    
    /**
     * Create the json string and write it to the output stream
     * @param req
     * @param resp
     */
    private void processJsonRequest(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("processJsonRequest");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");           
        ObjectMapper mapper = new ObjectMapper();
		
        try {
        	String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getCakeList());
			resp.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				mapper.writeValue(resp.getWriter(), e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}  
        
    }
    
    /**
     * Pass the list of cake objects as request attributes
     * @param req
     * @param resp
     * @throws Throwable 
     */	
    private void processBrowserRequest(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
        System.out.println("processBrowserRequest");
        try {
        	List<CakeEntity> cakeList = getCakeList();
        	req.setAttribute("cakeList", cakeList);           
            req.getRequestDispatcher("/cakelisting.jsp").forward(req, resp);
            
        } catch (Throwable ex) {
            throw ex;
            
        }
    }
    
    /**
     * To prevent out-of-memory errors
     */
    @Override
    public void destroy() {
        super.destroy(); 
        try {
            HibernateUtil.shutdown();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
}


}
